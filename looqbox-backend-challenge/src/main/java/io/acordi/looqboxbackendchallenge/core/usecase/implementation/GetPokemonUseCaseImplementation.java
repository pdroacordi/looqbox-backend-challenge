package io.acordi.looqboxbackendchallenge.core.usecase.implementation;

import io.acordi.looqboxbackendchallenge.core.dataprovider.FetchAllPokemon;
import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.core.util.sorting.SortingStrategy;
import io.acordi.looqboxbackendchallenge.core.util.sorting.factory.SortingFactory;

import java.util.List;

public class GetPokemonUseCaseImplementation implements GetPokemonUseCase {

    private final FetchAllPokemon fetchAllPokemon;


    public GetPokemonUseCaseImplementation(FetchAllPokemon fetchAllPokemon) {
        this.fetchAllPokemon = fetchAllPokemon;
    }

    @Override
    public List<Pokemon> getPokemon(String name, String sortType) {
        List<Pokemon> allPokemon = fetchAllPokemon.fetch();

        //Filtering the results using the user query
        if( name != null && !name.isEmpty() ) {

        }


        return allPokemon;
    }

}
