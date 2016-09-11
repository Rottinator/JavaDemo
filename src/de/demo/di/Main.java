package de.demo.di;

import java.io.IOException;

public class Main {
	public static void main (String[] args) throws IOException {
		System.out.println("Start...");
		
		DependencyInjectionRuntime.initialize();
		
		System.in.read();
		/*
		Class clazz = DemoConsumer.class;
		
		DemoConsumer demoConsumer = new DemoConsumer();
		
		DependencyInjectionRuntime.compose(demoConsumer);
		
		System.out.println(clazz.getSimpleName());
		*/

	}
}
