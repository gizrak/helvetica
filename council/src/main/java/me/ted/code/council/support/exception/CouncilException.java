package me.ted.code.council.support.exception;

import me.ted.code.council.support.response.ResponseCode;

public class CouncilException extends RuntimeException {

    private static final long serialVersionUID = 8336445966940447734L;

    public CouncilException(ResponseCode responseCode, Throwable cause) {
        super(createExceptionMessage(responseCode, null, cause), cause);
    }

    public CouncilException(ResponseCode responseCode, String message, Throwable cause) {
        super(createExceptionMessage(responseCode, message, cause), cause);
    }

    private static String createExceptionMessage(ResponseCode responseCode, String message, Throwable cause) {
        if (message != null) {
            return message;
        }
        if (cause != null) {
            return cause.getMessage();
        }
        return responseCode.getMessage();
    }
}
