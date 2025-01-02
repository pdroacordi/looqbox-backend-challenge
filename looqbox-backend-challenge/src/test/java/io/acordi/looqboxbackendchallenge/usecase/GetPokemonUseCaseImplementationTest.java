package io.acordi.looqboxbackendchallenge.usecase;

import io.acordi.looqboxbackendchallenge.core.dataprovider.FetchAllPokemon;
import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.core.usecase.implementation.GetPokemonUseCaseImplementation;
import io.acordi.looqboxbackendchallenge.core.util.sorting.SortingStrategy;
import io.acordi.looqboxbackendchallenge.core.util.sorting.factory.SortingFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class GetPokemonUseCaseImplementationTest {

    @Mock
    private FetchAllPokemon fetchAllPokemon;

    private GetPokemonUseCase getPokemonUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getPokemonUseCase = new GetPokemonUseCaseImplementation(fetchAllPokemon);
    }


    @Test
    void shouldFilterPokemon(){
        List<Pokemon> mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon("Pikachu"));
        mockPokemonList.add(new Pokemon("Charmander"));
        mockPokemonList.add(new Pokemon("Squirtle"));

        when(fetchAllPokemon.fetch()).thenReturn(mockPokemonList);

        List<Pokemon> result = getPokemonUseCase.getPokemon("pik", null);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Pikachu", result.get(0).getName());
        Mockito.verify(fetchAllPokemon, times(1)).fetch();
    }

    @Test
    void shouldSortPokemonAlphabetically(){
        List<Pokemon> mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon("Pikachu"));
        mockPokemonList.add(new Pokemon("Charmander"));
        mockPokemonList.add(new Pokemon("Squirtle"));

        @SuppressWarnings("unchecked")
        SortingStrategy<Pokemon> mockSortingStrategy = Mockito.mock(SortingStrategy.class);

        when(fetchAllPokemon.fetch()).thenReturn(mockPokemonList);

        try (MockedStatic<SortingFactory> mockedFactory = Mockito.mockStatic(SortingFactory.class)) {
            mockedFactory.when(() -> SortingFactory.getSortingStrategy("ALPHABETICAL"))
                    .thenReturn(mockSortingStrategy);

            when(mockSortingStrategy.sort(mockPokemonList)).thenReturn(
                    List.of(new Pokemon("Charmander"), new Pokemon("Pikachu"), new Pokemon("Squirtle"))
            );

            List<Pokemon> result = getPokemonUseCase.getPokemon(null, "ALPHABETICAL");

            Assertions.assertEquals(3, result.size());
            Assertions.assertEquals("Charmander", result.get(0).getName());
            Assertions.assertEquals("Pikachu", result.get(1).getName());
            Assertions.assertEquals("Squirtle", result.get(2).getName());
            Mockito.verify(fetchAllPokemon, times(1)).fetch();
            Mockito.verify(mockSortingStrategy, times(1)).sort(mockPokemonList);
        }
    }

    @Test
    void shouldSortPokemonByLength(){
        List<Pokemon> mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon("Pikachu"));
        mockPokemonList.add(new Pokemon("Charmander"));
        mockPokemonList.add(new Pokemon("Squirtle"));

        @SuppressWarnings("unchecked")
        SortingStrategy<Pokemon> mockSortingStrategy = Mockito.mock(SortingStrategy.class);

        when(fetchAllPokemon.fetch()).thenReturn(mockPokemonList);

        try (MockedStatic<SortingFactory> mockedFactory = Mockito.mockStatic(SortingFactory.class)) {
            mockedFactory.when(() -> SortingFactory.getSortingStrategy("LENGTH"))
                    .thenReturn(mockSortingStrategy);

            when(mockSortingStrategy.sort(mockPokemonList)).thenReturn(
                    List.of(new Pokemon("Pikachu"), new Pokemon("Squirtle"), new Pokemon("Charmander"))
            );

            List<Pokemon> result = getPokemonUseCase.getPokemon(null, "LENGTH");

            Assertions.assertEquals(3, result.size());
            Assertions.assertEquals("Pikachu", result.get(0).getName());
            Assertions.assertEquals("Squirtle", result.get(1).getName());
            Assertions.assertEquals("Charmander", result.get(2).getName());
            Mockito.verify(fetchAllPokemon, times(1)).fetch();
            Mockito.verify(mockSortingStrategy, times(1)).sort(mockPokemonList);
        }
    }
}
