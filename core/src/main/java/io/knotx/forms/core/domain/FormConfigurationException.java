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

public class FormConfigurationException extends RuntimeException {
  private final boolean fallbackDefined;

  public FormConfigurationException(String message, Throwable cause, boolean fallbackDetected) {
    super(message, cause);
    this.fallbackDefined = fallbackDetected;
  }

  public FormConfigurationException(String message, boolean fallbackDetected) {
    super(message);
    this.fallbackDefined = fallbackDetected;
  }

  public boolean isFallbackDefined() {
    return fallbackDefined;
  }
}
