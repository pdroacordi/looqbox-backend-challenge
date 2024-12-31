package io.acordi.looqboxbackendchallenge.dataprovider;

import io.acordi.looqboxbackendchallenge.core.dataprovider.Cache;
import io.acordi.looqboxbackendchallenge.core.dataprovider.FetchAllPokemon;
import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.dataprovider.client.FetchAllPokemonClient;
import io.acordi.looqboxbackendchallenge.dataprovider.client.mapper.NamedPokemonResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchAllPokemonImplementation implements FetchAllPokemon {

    private static final Logger log = LoggerFactory.getLogger(FetchAllPokemonImplementation.class);
    private final FetchAllPokemonClient fetchAllPokemonClient;
    private final NamedPokemonResponseMapper namedPokemonResponseMapper;
    private final Cache<String, List<Pokemon>> cache;

    private static final String CACHE_KEY = "all_pokemons";

    public FetchAllPokemonImplementation(FetchAllPokemonClient fetchAllPokemonClient,
                                         NamedPokemonResponseMapper namedPokemonResponseMapper,
                                         Cache<String, List<Pokemon>> cache
    ) {
        this.fetchAllPokemonClient = fetchAllPokemonClient;
        this.namedPokemonResponseMapper = namedPokemonResponseMapper;
        this.cache = cache;
    }

    @Override
    public List<Pokemon> fetch() {
        try{
            var cachedPokemon = cache.get(CACHE_KEY);
            if (cachedPokemon.isPresent()) {
                log.info("Returning cached Pokémon data");
                return cachedPokemon.get();
            }

            var pokemonResponse = fetchAllPokemonClient.fetchAllPokemon().get();
            var pokemonList = namedPokemonResponseMapper.toPokemonList(pokemonResponse.getResults());

            cache.put(CACHE_KEY, pokemonList);
            log.atInfo().log("Fetched all Pokemon");

            return pokemonList;
        }catch (Exception e) {
            log.atError().log(e.getMessage());
        }
        return null;
    }
}
