package com.rng.apirng.services.exception;

public class FileException extends RuntimeException {

    public FileException(){}

    public FileException(String msg) {
        super(msg);
    }
}