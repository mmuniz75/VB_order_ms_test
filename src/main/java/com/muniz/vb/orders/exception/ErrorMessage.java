
package com.muniz.vb.orders.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(Include.NON_EMPTY)
public class ErrorMessage {

  private String message;

  private List<String> errors = new ArrayList<>();

  public ErrorMessage(final String msg) {
    this.message = msg;
  }

  public void addError(final String error) {
    errors.add(error);
  }

}
