package io.tenants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SharedDatabaseDiscriminatorColumnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharedDatabaseDiscriminatorColumnApplication.class, args);
	}

}
