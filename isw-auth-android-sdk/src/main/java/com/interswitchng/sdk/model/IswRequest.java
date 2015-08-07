package com.interswitchng.sdk.model;

/**
 * Created by crownus on 8/6/15.
 */
public abstract class IswRequest {
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
