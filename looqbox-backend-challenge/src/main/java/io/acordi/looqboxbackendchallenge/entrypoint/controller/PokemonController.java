package io.acordi.looqboxbackendchallenge.entrypoint.controller;

import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.mapper.PokemonResponseMapper;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<PokemonResponse> getPokemon() {
        PokemonResponse response = pokemonResponseMapper.toPokemonResponse( getPokemonUseCase.getPokemon(null, null) );
        return ResponseEntity.ok( response );
    }

}
