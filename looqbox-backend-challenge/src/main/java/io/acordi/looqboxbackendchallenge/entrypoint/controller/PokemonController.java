package io.acordi.looqboxbackendchallenge.entrypoint.controller;

import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.mapper.PokemonResponseMapper;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonHighlightResponse;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonResponseMapper pokemonResponseMapper;
    private final GetPokemonUseCase getPokemonUseCase;

    public PokemonController(PokemonResponseMapper pokemonResponseMapper, GetPokemonUseCase getPokemonUseCase) {
        this.pokemonResponseMapper = pokemonResponseMapper;
        this.getPokemonUseCase = getPokemonUseCase;
    }

    @GetMapping
    public ResponseEntity<PokemonResponse> getPokemon(
            @RequestHeader(value = "query", required = false) String query,
            @RequestHeader(value = "sort", required = false) String sort
            ) {
        PokemonResponse response = pokemonResponseMapper.toPokemonResponse( getPokemonUseCase.getPokemon(query, sort) );
        return ResponseEntity.ok( response );
    }

    @GetMapping("/highlight")
    public ResponseEntity<PokemonHighlightResponse> getPokemonHighlight(
            @RequestHeader(value = "query", required = false) String query,
            @RequestHeader(value = "sort", required = false) String sort
    ) {
        PokemonHighlightResponse response = pokemonResponseMapper.toPokemonHighlightResponse( getPokemonUseCase.getPokemon(query, sort), query );
        return ResponseEntity.ok( response );
    }

}
