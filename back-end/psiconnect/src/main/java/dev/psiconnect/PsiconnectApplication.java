package dev.psiconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.psiconnect", "repositories", "controllers", "entities"})
@EnableJpaRepositories(basePackages = "repositories")
public class PsiconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsiconnectApplication.class, args);
	}

}
