package com.blast.springDemo.exception;

public class ParamsException extends RuntimeException{

    private static final long serialVersionUID = -9006104640618533135L;

    public ParamsException(String message) {
        super(message);
    }

    public ParamsException(Throwable t) {
        super(t.getMessage());
        super.initCause(t);
    }

    public ParamsException(String message, Throwable t) {
        super(message);
        super.initCause(t);
    }

    public ParamsException() {}

    public static ServiceException build(){
        return new ServiceException();
    }

    public static ServiceException build(String message){
        return new ServiceException(message);
    }

    public static ServiceException build(Throwable t){
        return new ServiceException(t);
    }

    public static ServiceException build(String message, Throwable t){
        return new ServiceException(message,t);
    }
}
