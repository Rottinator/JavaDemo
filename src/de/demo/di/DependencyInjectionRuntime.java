package de.demo.di;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"rawtypes"})
public class DependencyInjectionRuntime {
	
	static class ExportInformation {
		
		public ExportInformation(Class clazz, CreationPolicy creationPolicy) {
			this.clazz = clazz;
			this.creationPolicy = creationPolicy;
		}
		
		private Class clazz;
		
		public Class getClazz() {
			return clazz;
		}
		
		
		private CreationPolicy creationPolicy;
		
		public CreationPolicy getCreationPolicy() {
			return creationPolicy;
		}
	}
	
	private static Map<String, ExportInformation> exportInformations = new ConcurrentHashMap<String, ExportInformation>();
	
	//private static Map<String, Object> instances = new ConcurrentHashMap<String, Object>();
	
	public static void initialize() throws IOException {
		ClassCollector classCollector = new ClassCollector();
		
		classCollector.collect("de.demo");
		
		Collection<Class<?>> annotatedClasses = classCollector.getClassesAnnotatedWith(Export.class);
		
		for(Class<?> clazz : annotatedClasses) {
			Export annotation = clazz.getAnnotation(Export.class);
			
			ExportInformation exportInformation = new ExportInformation(clazz, annotation.creationPolicy());
			
			exportInformations.put(annotation.clazz().getName(), exportInformation);
		}
	}
	
	public static void compose(Object object) {
		Class clazz = object.getClass();
		
		for(Field field : clazz.getDeclaredFields()) {
			if(field.isAnnotationPresent(Import.class)) {
				if(!exportInformations.containsKey(field.getClass().getName())) {
					throw new DependencyInjectionException("No Export found for Import-Type: " + field.getClass().getName());
				}
				
				ExportInformation exportInformation = exportInformations.get(field.getClass().getName());
				
				try {
					Object instance = exportInformation.clazz.newInstance();
					
					compose(instance);
					
					field.set(object, instance);
				} catch (Exception e) {
					throw new DependencyInjectionException("Error creating instance for class: " + exportInformation.clazz.getName(), e);
				}
			}
		}
	}
}
