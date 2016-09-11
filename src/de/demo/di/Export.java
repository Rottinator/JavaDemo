package de.demo.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Export {
	
	@SuppressWarnings("rawtypes")
	Class clazz();
	
	CreationPolicy creationPolicy() default CreationPolicy.Default;
	
}
