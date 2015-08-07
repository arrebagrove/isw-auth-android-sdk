package com.interswitchng.sdk.model;

import java.util.List;

/**
 * Created by crownus on 8/5/15.
 */
public class IswResponse {
    private List<IswError> errors;
    private IswError error;

    public List<IswError> getErrors() {
        return errors;
    }

    public void setErrors(List<IswError> errors) {
        this.errors = errors;
    }

    public IswError getError() {
        return error;
    }

    public void setError(IswError error) {
        this.error = error;
    }
}
