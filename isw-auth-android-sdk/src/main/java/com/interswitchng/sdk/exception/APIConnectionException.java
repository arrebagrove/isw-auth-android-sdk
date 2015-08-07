package com.interswitchng.sdk.exception;

/**
 * Created by crownus on 8/6/15.
 */
public class APIConnectionException extends IswException {
    public APIConnectionException(String message) {
        super(message, null);
    }

    public APIConnectionException(String message, Throwable e) {
        super(message, null, e);
    }
}
