package io.github.dathin.dathinsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class DathinSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DathinSessionApplication.class, args);
	}

}
