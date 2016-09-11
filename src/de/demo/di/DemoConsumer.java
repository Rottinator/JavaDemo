package de.demo.di;

public class DemoConsumer {

	@Import
	@Export(clazz = DemoConsumer.class)
	private IDemoService demoService;
	
}
