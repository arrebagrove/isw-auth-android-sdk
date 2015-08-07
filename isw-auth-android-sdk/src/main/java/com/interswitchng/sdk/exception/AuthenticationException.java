package com.interswitchng.sdk.exception;

/**
 * Created by crownus on 8/6/15.
 */
public class AuthenticationException extends IswException {
    public AuthenticationException(String message, String requestId) {
        super(message, requestId);
    }
}
