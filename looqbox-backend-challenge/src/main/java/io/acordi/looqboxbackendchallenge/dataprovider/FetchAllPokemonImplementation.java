    package io.acordi.looqboxbackendchallenge.dataprovider;

    import io.acordi.looqboxbackendchallenge.core.dataprovider.Cache;
    import io.acordi.looqboxbackendchallenge.core.dataprovider.FetchAllPokemon;
    import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
    import io.acordi.looqboxbackendchallenge.dataprovider.client.FetchAllPokemonClient;
    import io.acordi.looqboxbackendchallenge.dataprovider.client.mapper.NamedPokemonResponseMapper;
    import io.acordi.looqboxbackendchallenge.dataprovider.exception.FetchPokemonException;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Component;

    import java.util.List;
    import java.util.concurrent.ExecutionException;

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
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt status
                throw new FetchPokemonException("The operation was interrupted while fetching Pokémon data.");
            } catch (ExecutionException e) {
                log.error("Failed to fetch Pokémon data due to execution error: {}", e.getMessage(), e);
                throw new FetchPokemonException("Failed to fetch Pokémon data from the external API.");
            }
        }
    }
