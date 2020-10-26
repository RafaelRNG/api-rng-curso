package com.rng.apirng.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeDoCampo;
    private String msg;

    public FieldMessage(){}

    public FieldMessage(String nomeDoCampo, String msg) {
        this.nomeDoCampo = nomeDoCampo;
        this.msg = msg;
    }

    public String getNomeDoCampo() {
        return nomeDoCampo;
    }

    public void setNomeDoCampo(String nomeDoCampo) {
        this.nomeDoCampo = nomeDoCampo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
