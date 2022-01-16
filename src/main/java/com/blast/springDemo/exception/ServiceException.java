package com.blast.springDemo.exception;

/**
 * 业务异常 也就是500
 * 属于运行时异常（runtime exception）
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -9006104640618533135L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable t) {
		super(t.getMessage());
		super.initCause(t);
	}

	public ServiceException(String message, Throwable t) {
		super(message);
		super.initCause(t);
	}

	public ServiceException() {}

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
