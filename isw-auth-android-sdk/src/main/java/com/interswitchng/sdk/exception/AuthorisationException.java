package com.interswitchng.sdk.exception;

/**
 * Created by crownus on 8/6/15.
 */
public class AuthorisationException extends IswException {
    public AuthorisationException(String message, String requestId) {
        super(message, requestId);
    }
}
