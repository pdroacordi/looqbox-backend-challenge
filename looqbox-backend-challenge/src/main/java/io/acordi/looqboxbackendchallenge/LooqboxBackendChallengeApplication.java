package io.acordi.looqboxbackendchallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
            title = "LooqBox Challenge",
            version = "1.0.0",
            description = """
                This API is designed to solve the Looqbox challenge. It provides endpoints for:
                - Fetching and filtering Pokémon data.
                - Highlighting specific Pokémon based on user criteria.
                The API integrates with the external PokéAPI to retrieve Pokémon data, 
                ensuring that the information is always up-to-date.
            """,
            contact = @Contact( name = "Pedro Acordi", email = "pedroacordi2@gmail.com" )
        ),
        tags = @Tag(name = "Pokémon Operations", description = "Endpoints for fetching and processing Pokémon data.")
    )
public class LooqboxBackendChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LooqboxBackendChallengeApplication.class, args);
    }

}
