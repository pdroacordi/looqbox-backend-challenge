package io.acordi.looqboxbackendchallenge.dataprovider;

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

    public FetchAllPokemonImplementation(FetchAllPokemonClient fetchAllPokemonClient, NamedPokemonResponseMapper namedPokemonResponseMapper) {
        this.fetchAllPokemonClient = fetchAllPokemonClient;
        this.namedPokemonResponseMapper = namedPokemonResponseMapper;
    }

    @Override
    public List<Pokemon> fetch() {
        try{
            var pokemonResponse = fetchAllPokemonClient.fetchAllPokemon().get();
            log.atInfo().log("Fetched all Pokemon");
            return namedPokemonResponseMapper.toPokemonList(pokemonResponse.getResults());
        }catch (Exception e) {
            log.atError().log(e.getMessage());
        }
        return null;
    }
}
