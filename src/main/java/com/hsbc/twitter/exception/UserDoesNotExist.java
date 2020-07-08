package com.hsbc.twitter.exception;

public class UserDoesNotExist extends RuntimeException {

    public UserDoesNotExist(String message) {
        super(message);
    }
}
