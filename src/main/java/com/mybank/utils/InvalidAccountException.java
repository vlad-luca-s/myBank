package com.mybank.utils;

public class InvalidAccountException extends RuntimeException {

    public InvalidAccountException(String account) {
        super("Invalid account: " + account);
    }
}
