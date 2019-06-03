package com.github.throwable.yajflow;

public class ProcessDefinitionException extends RuntimeException {
    public ProcessDefinitionException() {
        super();
    }

    public ProcessDefinitionException(String message) {
        super(message);
    }

    public ProcessDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessDefinitionException(Throwable cause) {
        super(cause);
    }
}
