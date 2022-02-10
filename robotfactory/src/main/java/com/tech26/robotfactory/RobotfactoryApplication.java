package com.tech26.robotfactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages="com.tech26.robotfactory")
public class RobotfactoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(RobotfactoryApplication.class, args);
	}
}
