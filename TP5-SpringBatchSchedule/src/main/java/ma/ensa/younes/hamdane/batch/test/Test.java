package ma.ensa.younes.hamdane.batch.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ma.ensa.younes.hamdane.batch.configuration.AppConfig;

public class Test {
	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	 
		
	}
	
}
