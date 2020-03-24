
package com.muniz.vb.orders.exception;

import lombok.Getter;

@Getter
public abstract class APIException extends RuntimeException {

  private final ErrorMessage error;

  public APIException(final Throwable cause) {
    super(cause);
    this.error = new ErrorMessage(cause.getMessage());
  }

  public APIException(final ErrorMessage error) {
    this.error = error;
  }

  public APIException(final String error) {
    this.error = new ErrorMessage(error);
  }

}
