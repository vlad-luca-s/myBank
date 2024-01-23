package com.mybank.utils;

public class InvalidAmountException extends RuntimeException{

    public InvalidAmountException() {
        super("Not enough money!");
    }
}
