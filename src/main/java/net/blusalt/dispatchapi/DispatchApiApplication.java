package net.blusalt.dispatchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DispatchApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispatchApiApplication.class, args);
	}

}
