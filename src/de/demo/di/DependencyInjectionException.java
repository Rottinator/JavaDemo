package de.demo.di;

public class DependencyInjectionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DependencyInjectionException(String message) {
		super(message);
	}	
	
	public DependencyInjectionException(String message, Throwable ex) {
		super(message, ex);
	}
}
