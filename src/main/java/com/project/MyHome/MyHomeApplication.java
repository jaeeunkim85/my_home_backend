package com.project.MyHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MyHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyHomeApplication.class, args);
	}

}
