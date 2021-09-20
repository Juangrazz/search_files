package com.juangracia.utils.searchFiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class App {
	
	public static void main(String[] args) {
		try (final ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args)) {
	        applicationContext.getBean(RunnerSearchFile.class).main(args);
	    }	
	}

}
