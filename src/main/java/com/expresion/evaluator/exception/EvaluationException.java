package com.expresion.evaluator.exception;

public class EvaluationException extends RuntimeException {
    public EvaluationException(String message) {
        super(message);
    }

    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
