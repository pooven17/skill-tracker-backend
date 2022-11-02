package com.cts.skilltrkr.exception;

public class NotValidUpdateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotValidUpdateException(String msg) {
        super(msg);
    }
}
