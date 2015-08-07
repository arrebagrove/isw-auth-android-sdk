package com.interswitchng.sdk.exception;

/**
 * Created by crownus on 8/6/15.
 */
public class APIException extends IswException {
    public APIException(String message, String requestId, Throwable e) {
        super(message, requestId, e);
    }
}
