package dev.mauhux.apps.cinema;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppCinemaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppCinemaApiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Cinema API")
						.version("1.0.0")
						.description("API for managing cinema")
						.termsOfService("http://www.cinema.io/terms"));
	}
}
