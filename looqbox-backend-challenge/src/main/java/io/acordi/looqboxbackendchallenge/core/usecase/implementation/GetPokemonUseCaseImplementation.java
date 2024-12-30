package io.acordi.looqboxbackendchallenge.core.usecase.implementation;

import io.acordi.looqboxbackendchallenge.core.dataprovider.FetchAllPokemon;
import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.domain.SortType;
import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.core.util.sorting.SortingStrategy;
import io.acordi.looqboxbackendchallenge.core.util.sorting.factory.SortingFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetPokemonUseCaseImplementation implements GetPokemonUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetPokemonUseCaseImplementation.class);
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
        //Sorting
        sortType = parseEnumOrString(sortType);
        SortingStrategy<Pokemon> sortingStrategy = SortingFactory.getSortingStrategy(sortType);
        allPokemon = sortingStrategy.sort( allPokemon );

        return allPokemon;
    }

    private String parseEnumOrString(String value) {
        if (value == null) {
            return "Not Provided";
        }

        try {
            return SortType.valueOf(value.toUpperCase()).name();
        } catch (IllegalArgumentException e) {
        }

        try {
            int ordinal = Integer.parseInt(value);
            SortType[] values = SortType.values();
            if (ordinal >= 0 && ordinal < values.length) {
                return values[ordinal].name();
            }
        } catch (NumberFormatException e) {
        }

        // If neither match, return the value as is
        return value;
    }

}
