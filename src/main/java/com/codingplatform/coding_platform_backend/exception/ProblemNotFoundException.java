package com.codingplatform.coding_platform_backend.exception;

public class ProblemNotFoundException extends RuntimeException{
    public ProblemNotFoundException(String message) {
        super(message);
    }
}
