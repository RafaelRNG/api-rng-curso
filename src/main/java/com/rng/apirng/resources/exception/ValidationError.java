package com.rng.apirng.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<FieldMessage>();

    
    
    public ValidationError(Long timestamp, Integer status, String error, String msg, String path) {
		super(timestamp, status, error, msg, path);
	}

	public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String nomeDoCampo, String msg){
        errors.add(new FieldMessage(nomeDoCampo, msg));
    }
}
