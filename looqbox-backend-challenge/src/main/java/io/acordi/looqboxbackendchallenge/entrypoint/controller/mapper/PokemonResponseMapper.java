package io.acordi.looqboxbackendchallenge.entrypoint.controller.mapper;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonHighlightResponse;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PokemonResponseMapper {

    default PokemonResponse toPokemonResponse(List<Pokemon> pokemonList) {
        PokemonResponse response = new PokemonResponse();
        response.setResult(
                pokemonList.stream()
                        .map(Pokemon::getName)
                        .collect(Collectors.toList())
        );
        return response;
    }

    default PokemonHighlightResponse toPokemonHighlightResponse(List<Pokemon> pokemonList, String query){
        PokemonHighlightResponse response = new PokemonHighlightResponse();
        response.setResult(
                pokemonList.stream()
                        .map(pokemon -> new PokemonHighlightResponse.Result(
                                pokemon.getName(),
                                highlight(pokemon.getName(), query)
                        ))
                        .collect(Collectors.toList())
        );
        return response;
    }

    private String highlight(String name, String query) {
        if (query == null || query.isEmpty()) {
            return null;
        }
        return name.replaceAll("(?i)(" + query + ")", "<pre>$1</pre>");
    }
}
