package dev.psiconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "dev.psiconnect.repositories")
public class PsiconnectApplication {
	public static void main(String[] args) {
		SpringApplication.run(PsiconnectApplication.class, args);
	}
}
