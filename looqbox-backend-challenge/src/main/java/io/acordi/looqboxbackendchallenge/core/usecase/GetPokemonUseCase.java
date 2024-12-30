package io.acordi.looqboxbackendchallenge.core.usecase;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;

import java.util.List;

public interface GetPokemonUseCase {

    List<Pokemon> getPokemon(String name, String sortType);
}
