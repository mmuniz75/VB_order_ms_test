package com.muniz.vb.orders.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String BAD_REQUEST_MSG = "Invalid request";


  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  protected void processException(final Exception ex) {
    logE(ex);
  }

  @ExceptionHandler(APIException.class)
  protected ResponseEntity<ErrorMessage> processAPIException(final APIException ex) {
    final ResponseStatus status = ex.getClass().getDeclaredAnnotation(ResponseStatus.class);
    logE(ex);
    return new ResponseEntity<>(ex.getError(),
            Objects.nonNull(status) ? status.code() : HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
    logE(ex);
    final ErrorMessage errorMessage = new ErrorMessage(BAD_REQUEST_MSG);
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errorMessage.addError(error.getField() + ": " + error.getDefaultMessage());
    }
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }


  private static void logE(final Exception e) {

    log.info("e={},m={}", e.getClass().getSimpleName(), e.getMessage());
  }

}
