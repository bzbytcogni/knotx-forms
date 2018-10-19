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
package io.knotx.forms.adapter.mock;


import io.knotx.dataobjects.ClientResponse;
import io.knotx.forms.api.FormsAdapterRequest;
import io.knotx.forms.api.FormsAdapterResponse;
import io.knotx.forms.api.reactivex.AbstractFormsAdapterProxy;
import io.reactivex.Single;
import io.vertx.core.buffer.Buffer;

public class FormsMockAdapterProxy extends AbstractFormsAdapterProxy {

  private static final String MOCK_RESPONSE = "{\"mock\": true}";

  @Override
  protected Single<FormsAdapterResponse> processRequest(FormsAdapterRequest request) {
    return Single.just(
        new FormsAdapterResponse()
            .setSignal("success")
            .setResponse(
                new ClientResponse().setStatusCode(200).setBody(Buffer.buffer(MOCK_RESPONSE)))
    );
  }
}
