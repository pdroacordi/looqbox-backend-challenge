package io.acordi.looqboxbackendchallenge.dataprovider;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.dataprovider.client.mapper.NamedPokemonResponseMapper;
import io.acordi.looqboxbackendchallenge.dataprovider.client.mapper.NamedPokemonResponseMapperImpl;
import io.acordi.looqboxbackendchallenge.dataprovider.client.response.NamedPokemonResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NamedPokemonResponseMapperTest {

    private NamedPokemonResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NamedPokemonResponseMapperImpl();
    }

    @Test
    void shouldMapValidResultsToPokemonList(){
        List<NamedPokemonResponse.Result> results = List.of(
                new NamedPokemonResponse.Result("Pikachu", "https://pokeapi.co/api/v2/pokemon/25"),
                new NamedPokemonResponse.Result("Charmander", "https://pokeapi.co/api/v2/pokemon/4")
        );

        List<Pokemon> pokemonList = mapper.toPokemonList(results);

        Assertions.assertEquals(2, pokemonList.size());
        Assertions.assertEquals("Pikachu", pokemonList.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListForEmptyResults() {
        List<NamedPokemonResponse.Result> results = List.of();

        List<Pokemon> pokemonList = mapper.toPokemonList(results);

        Assertions.assertTrue(pokemonList.isEmpty());
    }

    @Test
    void shouldReturnNullForNullResults() {
        List<Pokemon> pokemonList = mapper.toPokemonList(null);
        Assertions.assertNull(pokemonList);
    }
}
