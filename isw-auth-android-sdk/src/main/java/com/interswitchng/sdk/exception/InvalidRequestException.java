package com.interswitchng.sdk.exception;

/**
 * Created by crownus on 8/6/15.
 */
public class InvalidRequestException extends IswException {
    private final String params;

    public InvalidRequestException(String message, String params, String requestId, Throwable e) {
        super(message, requestId, e);
        this.params = params;
    }

    public String getParams() {
        return params;
    }
}
