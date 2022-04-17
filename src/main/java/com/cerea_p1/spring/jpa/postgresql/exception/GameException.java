package com.cerea_p1.spring.jpa.postgresql.exception;

public class GameException extends RuntimeException {

    public GameException(String msg) {
        super(msg);
    }
}