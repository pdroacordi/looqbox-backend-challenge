package io.acordi.looqboxbackendchallenge.config;

import io.acordi.looqboxbackendchallenge.core.dataprovider.FetchAllPokemon;
import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.core.usecase.implementation.GetPokemonUseCaseImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetPokemonConfig {

    @Bean
    public GetPokemonUseCase getPokemonUseCase(FetchAllPokemon fetchAllPokemon) {
        return new GetPokemonUseCaseImplementation(fetchAllPokemon);
    }
}
