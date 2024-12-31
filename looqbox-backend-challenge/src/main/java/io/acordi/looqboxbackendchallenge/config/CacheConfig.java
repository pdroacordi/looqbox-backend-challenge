package io.acordi.looqboxbackendchallenge.config;

import io.acordi.looqboxbackendchallenge.core.dataprovider.Cache;
import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.dataprovider.CacheImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, List<Pokemon>> pokemonCache() {
        return new CacheImplementation<>(10 * 60 * 1000); // 10 minutes is what the cache will last.
    }

}
