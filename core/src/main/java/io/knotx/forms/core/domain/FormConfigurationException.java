package io.knotx.forms.core.domain;

import io.knotx.dataobjects.Fragment;

public class FormConfigurationException extends RuntimeException {
  private boolean fallbackDetected;

  public FormConfigurationException(String message, Throwable cause, boolean fallbackDetected) {
    super(message, cause);
    this.fallbackDetected = fallbackDetected;
  }

  public FormConfigurationException(String message, boolean fallbackDetected) {
    super(message);
    this.fallbackDetected = fallbackDetected;
  }

  public boolean isFallbackDetected() {
    return fallbackDetected;
  }
}
