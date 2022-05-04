package com.cerea_p1.spring.jpa.postgresql.exception;

public class ConnectGameException extends RuntimeException {

    public ConnectGameException(String msg) {
        super(msg);
    }
}