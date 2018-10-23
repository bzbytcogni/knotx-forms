/*
 * Copyright (C) 2018 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.forms.test.integration;

import static io.knotx.junit5.util.RequestUtil.subscribeToResult_shouldSucceed;

import io.knotx.dataobjects.ClientRequest;
import io.knotx.dataobjects.ClientResponse;
import io.knotx.dataobjects.Fragment;
import io.knotx.dataobjects.KnotContext;
import io.knotx.junit5.KnotxApplyConfiguration;
import io.knotx.junit5.KnotxExtension;
import io.knotx.reactivex.proxy.KnotProxy;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.Vertx;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Ignore
@ExtendWith(KnotxExtension.class)
public class FormsIntegrationTest {

  private final static String CORE_MODULE_EB_ADDRESS = "knotx.knot.forms";
  private final static String FORM_NAME = "someId456";

  @Test
  @KnotxApplyConfiguration("formsStack.conf")
  public void callForms_validKnotContextResult(
      VertxTestContext vertxTestContext, Vertx vertx)
      throws IOException, URISyntaxException {
    KnotContext message = payloadMessage("fragment_form_self_in.txt");

    rxProcessWithAssertions(vertxTestContext, vertx, message, this::assertValidFormsContext);
  }

  @Test
  @KnotxApplyConfiguration("formsStack_POST.conf")
  public void callPostForms_validKnotContextResult(
      VertxTestContext vertxTestContext, Vertx vertx)
      throws IOException, URISyntaxException {
    KnotContext message = payloadMessage("fragment_form_self_in.txt");
    message.getClientRequest().setMethod(HttpMethod.POST);

    rxProcessWithAssertions(vertxTestContext, vertx, message, this::assertValidFormsContext);
  }

  @Test
  @KnotxApplyConfiguration("formsStack_GET.conf")
  public void callGetForms_validResult(
      VertxTestContext vertxTestContext, Vertx vertx)
      throws IOException, URISyntaxException {
    KnotContext message = payloadMessage("fragment_form_self_in.txt");
    message.getClientRequest().setMethod(HttpMethod.GET);

    rxProcessWithAssertions(vertxTestContext, vertx, message,
        knotContext -> {
          Assertions.assertEquals(
              getFragmentFromResources("fragment_form_self_out.txt").replaceAll("\\s", ""),
              knotContext.getFragments().iterator().next().content().replaceAll("\\s", ""));
        });
  }

  private void rxProcessWithAssertions(VertxTestContext context, Vertx vertx, KnotContext payload,
      Consumer<KnotContext> onSuccess) {
    KnotProxy service = KnotProxy.createProxy(vertx, CORE_MODULE_EB_ADDRESS);
    Single<KnotContext> knotContextSingle = service.rxProcess(payload);

    subscribeToResult_shouldSucceed(context, knotContextSingle, onSuccess);
  }

  private String getFragmentFromResources(String fragmentPath)
      throws IOException, URISyntaxException {
    return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
        .getResource(fragmentPath).toURI())));
  }

  private KnotContext payloadMessage(String fragmentPath) throws IOException, URISyntaxException {
    ClientRequest clientRequest = new ClientRequest()
        .setFormAttributes(MultiMap.caseInsensitiveMultiMap()
            .add("snippet-identifier", FORM_NAME));

    ClientResponse clientResponse = new ClientResponse().setStatusCode(200);

    String fragmentContent = getFragmentFromResources(fragmentPath);

    List<Fragment> fragments = Collections.singletonList(
        Fragment.snippet(Collections.singletonList("form-" + FORM_NAME), fragmentContent));

    return new KnotContext()
        .setClientRequest(clientRequest)
        .setClientResponse(clientResponse)
        .setFragments(fragments);
  }

  private void assertValidFormsContext(KnotContext knotContext) {
    JsonObject context = knotContext.getFragments().iterator().next().context();

    Assertions.assertTrue(context.containsKey("forms"));
    Assertions.assertTrue(context.getJsonObject("forms").containsKey("_result"));
    Assertions.assertTrue(
        context.getJsonObject("forms").getJsonObject("_result").containsKey("mock"));
  }

}
