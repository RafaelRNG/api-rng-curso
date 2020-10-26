package com.rng.apirng.resources.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidationError extends StandardError {

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<FieldMessage>();

    public ValidationError(Integer status, String msg, Date date){
        super(status, msg, date);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String nomeDoCampo, String msg){
        errors.add(new FieldMessage(nomeDoCampo, msg));
    }
}
