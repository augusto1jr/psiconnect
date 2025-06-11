package dev.psiconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe principal da aplicação PsiConnect.
 *
 * Responsável por iniciar a aplicação Spring Boot e configurar o contexto,
 * incluindo o escaneamento dos repositórios JPA no pacote especificado.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "dev.psiconnect.repositories")
public class PsiconnectApplication {

	/**
	 * Método principal que inicializa a aplicação Spring Boot.
	 *
	 * @param args argumentos de linha de comando (opcional)
	 */
	public static void main(String[] args) {
		SpringApplication.run(PsiconnectApplication.class, args);
	}
}
