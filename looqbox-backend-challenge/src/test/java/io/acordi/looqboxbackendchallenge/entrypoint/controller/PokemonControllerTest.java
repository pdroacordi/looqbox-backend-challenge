package io.acordi.looqboxbackendchallenge.entrypoint.controller;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.mapper.PokemonResponseMapper;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonHighlightResponse;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

public class PokemonControllerTest {

    @Mock
    private GetPokemonUseCase getPokemonUseCase;

    @Mock
    private PokemonResponseMapper pokemonResponseMapper;

    private PokemonController pokemonController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pokemonController = new PokemonController(pokemonResponseMapper, getPokemonUseCase);
    }

    @Test
    void shouldReturnPokemonResponse() {
        List<Pokemon> mockPokemonList = List.of(new Pokemon("Pikachu"), new Pokemon("Charmander"));
        PokemonResponse mockResponse = new PokemonResponse(List.of("Pikachu"));

        when(getPokemonUseCase.getPokemon("pik", "ALPHABETICAL")).thenReturn(mockPokemonList);
        when(pokemonResponseMapper.toPokemonResponse(mockPokemonList)).thenReturn(mockResponse);

        ResponseEntity<PokemonResponse> response = pokemonController.getPokemon("pik", "ALPHABETICAL");

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(mockResponse, response.getBody());
    }

    @Test
    void shouldReturnPokemonHighlightResponse() {
        List<Pokemon> mockPokemonList = List.of(new Pokemon("Pikachu"), new Pokemon("Charmander"));
        PokemonHighlightResponse mockHighlightResponse = new PokemonHighlightResponse();
        mockHighlightResponse.setResult( List.of(
                new PokemonHighlightResponse.Result("Pikachu", "pik")
        ) );

        when(getPokemonUseCase.getPokemon("pik", "ALPHABETICAL")).thenReturn(mockPokemonList);
        when(pokemonResponseMapper.toPokemonHighlightResponse(mockPokemonList, "pik")).thenReturn(mockHighlightResponse);

        ResponseEntity<PokemonHighlightResponse> response = pokemonController.getPokemonHighlight("pik", "ALPHABETICAL");

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(mockHighlightResponse, response.getBody());
    }
}
