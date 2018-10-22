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

public class FormConstants {

  public static final String FRAGMENT_KNOT_PREFIX = "form";
  static final String FORM_DEFAULT_IDENTIFIER = "_default_";
  public static final String FORM_NO_REDIRECT_SIGNAL = "_self";

  static final String FRAGMENT_KNOT_PATTERN = "form(-)?";
  static final String FORM_SIGNAL_ATTR_PREFIX = "data-knotx-forms-on-";
  static final String FORM_ADAPTER_ATTR = "data-knotx-forms-adapter";
  static final String FORM_ADAPTER_PARAMS = "data-knotx-forms-adapter-params";

  static final String FORM_ATTRIBUTES_PATTERN = "data-knotx-.*";

  private FormConstants() {
    // hidden
  }

}
