package com.eftomi.kata.service.exception;

public class NegativeSecondsException extends RuntimeException {
    public NegativeSecondsException(String message) {
        super(message);
    }
}
