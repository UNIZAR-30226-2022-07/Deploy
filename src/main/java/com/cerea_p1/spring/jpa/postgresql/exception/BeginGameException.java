package com.cerea_p1.spring.jpa.postgresql.exception;

public class BeginGameException extends RuntimeException {

    public BeginGameException(String msg) {
        super(msg);
    }
}