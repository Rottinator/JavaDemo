package de.demo.di;

@Export(clazz = IDemoService.class)
public class DemoService implements IDemoService {

	@Override
	public void doStuff() {
		System.out.println("Hallo");
	}

}
