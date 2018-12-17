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
package io.knotx.forms.core.domain;

import io.knotx.dataobjects.KnotContext;
import io.knotx.forms.core.FormsKnotOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.BooleanUtils;

public final class FormsFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(FormsFactory.class);

  private FormsFactory() {
    // util class
  }

  public static List<FormEntity> create(KnotContext context, FormsKnotOptions options) {
    List<FormEntity> forms = context.getFragments().stream()
        .filter(f -> f.knots().stream().anyMatch(id -> id.startsWith(
            FormConstants.FRAGMENT_KNOT_PREFIX)))
        .map(f -> FormEntity.from(f, options))
        .collect(Collectors.toList());
    if (areNotUnique(forms)) {
      LOGGER.error("Form identifiers are not unique [{}]", forms.stream().map(FormEntity::identifier).toArray());
      Set<String> duplicates = findDuplicateIds(forms);
      boolean fallbackDetected = markDuplicatesAndVerifyFallback(forms, duplicates);
      throw new FormConfigurationException("Form identifiers are not unique!", fallbackDetected);
    }
    return forms;
  }

  private static boolean areNotUnique(List<FormEntity> forms) {
    return forms.size() != forms.stream().map(FormEntity::identifier).collect(Collectors.toSet()).size();
  }

  private static Set<String> findDuplicateIds(Collection<FormEntity> collection) {
    Set<String> uniques = new HashSet<String>();
    return collection.stream()
        .map(FormEntity::identifier)
        .filter(e -> !uniques.add(e))
        .collect(Collectors.toSet());
  }

  private static boolean markDuplicatesAndVerifyFallback(List<FormEntity> forms, Set<String> duplicateIds) {
    return forms.stream()
        .filter(f -> duplicateIds.contains(f.identifier()))
        .map(FormsFactory::markAsDuplicateAndVerifyFallback)
        .allMatch(BooleanUtils::isTrue);
  }

  private static boolean markAsDuplicateAndVerifyFallback(FormEntity form) {
    String knotId = form.fragment().knots().stream()
        .filter(knot -> knot.startsWith(FormConstants.FRAGMENT_KNOT_PREFIX))
        .findFirst()
        .get();

    form.fragment().failure(knotId, new IllegalStateException(String.format("Duplicate form ID %s", form.identifier())));
    return form.fragment().fallback().isPresent();
  }

}
