package com.popinjay.loginsys.controller.errors;

public class UsernameUniqueException extends RuntimeException {
  public UsernameUniqueException(String message) {
    super(message);
  }
}
