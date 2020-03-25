
package com.muniz.vb.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class PreConditionException extends APIException {

    public PreConditionException(final Throwable cause) {

        super(cause);
    }

    public PreConditionException(final ErrorMessage error) {

        super(error);
    }

    public PreConditionException(final String error) {

        super(error);
    }

}
