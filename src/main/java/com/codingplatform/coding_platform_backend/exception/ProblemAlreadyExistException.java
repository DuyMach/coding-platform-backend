package com.codingplatform.coding_platform_backend.exception;

public class ProblemAlreadyExistException extends RuntimeException{
    public ProblemAlreadyExistException(String message) {
        super(message);
    }
}
