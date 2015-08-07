package com.interswitchng.sdk.exception;

/**
 * Created by crownus on 8/6/15.
 */
public abstract class IswException extends Exception {
    private String requestId;

    public IswException(String message, String requestId) {
        super(message, null);
        this.requestId = requestId;
    }

    public IswException(String message, String requestId, Throwable e) {
        super(message, e);
        this.requestId = requestId;
    }

    public String toString() {
        String reqIdStr = "";
        if (requestId != null) {
            reqIdStr = "; requestId: " + requestId;
        }
        return super.toString() + reqIdStr;
    }
}
