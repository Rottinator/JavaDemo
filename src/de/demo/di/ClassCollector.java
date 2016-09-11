package de.demo.di;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class ClassCollector {

	private Collection<Class<?>> classes;
	
	public void collect(String packageName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		URL url = classLoader.getResource(packageName.replaceAll(".", "/"));
		
		if(url == null) {
			throw new ClassCollectorException("No Resource found for package \"" + packageName + "\"");
		}
		
		String filePath = url.getFile();
		
		File classFolder = new File(filePath);
		
		if(!classFolder.exists()) {
			throw new ClassCollectorException("Path: \"" + filePath + "\" not found");
		}
		
		classes = getClasses(classFolder, packageName);
	}
	
	public Collection<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotationClass) {
		Collection<Class<?>> result = new ArrayList<Class<?>>();
		
		for(Class<?> clazz : classes) {
			if(clazz.getAnnotation(annotationClass) != null) {
				result.add(clazz);
			}
		}
		
		return result;
	}
	
	private Collection<Class<?>> getClasses(File rootFolder, String rootPackage) {
		Collection<Class<?>> resultList = new ArrayList<Class<?>>();
		
		File[] files = rootFolder.listFiles();
		
		String currentPackage = rootPackage;
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			
			if(file.isFile() && file.getName().endsWith(".class")) {
				String fullClassName = rootPackage + "." + file.getName().replace(".class", "");
								
				try {
					Class<?> clazz = Class.forName(fullClassName);
					
					resultList.add(clazz);
				} catch (ClassNotFoundException e) {
					
				}
			}
			else {
				Collection<Class<?>> subFolderClasses = getClasses(file, currentPackage + "." + file.getName());
				
				resultList.addAll(subFolderClasses);
			}
		}
		
		return resultList;
	}
}
