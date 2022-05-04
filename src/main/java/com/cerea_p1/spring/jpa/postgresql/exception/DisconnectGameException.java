package com.cerea_p1.spring.jpa.postgresql.exception;

public class DisconnectGameException extends RuntimeException {

    public DisconnectGameException(String msg) {
        super(msg);
    }
}