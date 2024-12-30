package io.acordi.looqboxbackendchallenge.entrypoint.controller.mapper;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

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
}
