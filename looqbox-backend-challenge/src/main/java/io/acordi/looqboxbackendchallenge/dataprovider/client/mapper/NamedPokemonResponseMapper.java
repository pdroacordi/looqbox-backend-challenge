package io.acordi.looqboxbackendchallenge.dataprovider.client.mapper;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.dataprovider.client.response.NamedPokemonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NamedPokemonResponseMapper {


    @Mapping(target = "", source = "url", ignore = true)
    @Mapping(target = "name", source = "results.name")
    List<Pokemon> toPokemonList(List<NamedPokemonResponse.Result> results);

}
