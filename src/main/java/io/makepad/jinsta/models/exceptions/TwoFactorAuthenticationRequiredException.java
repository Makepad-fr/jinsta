package io.makepad.jinsta.models.exceptions;

public class TwoFactorAuthenticationRequiredException extends Exception {
  public TwoFactorAuthenticationRequiredException(String message) {
    super(message);
  }
}
