package io.acordi.looqboxbackendchallenge.dataprovider;

import io.acordi.looqboxbackendchallenge.core.dataprovider.Cache;
import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.dataprovider.client.FetchAllPokemonClient;
import io.acordi.looqboxbackendchallenge.dataprovider.client.mapper.NamedPokemonResponseMapper;
import io.acordi.looqboxbackendchallenge.dataprovider.client.response.NamedPokemonResponse;
import io.acordi.looqboxbackendchallenge.dataprovider.exception.FetchPokemonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;

public class FetchAllPokemonImplementationTest {

    @Mock
    private FetchAllPokemonClient fetchAllPokemonClient;

    @Mock
    private NamedPokemonResponseMapper namedPokemonResponseMapper;

    @Mock
    private Cache<String, List<Pokemon>> cache;

    private FetchAllPokemonImplementation fetchAllPokemonImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fetchAllPokemonImplementation = new FetchAllPokemonImplementation(fetchAllPokemonClient, namedPokemonResponseMapper, cache);
    }

    @Test
    void shouldReturnCachedPokemonData() {
        List<Pokemon> cachedPokemonList = List.of(new Pokemon("Pikachu"), new Pokemon("Charmander"));
        when(cache.get("all_pokemons")).thenReturn(Optional.of(cachedPokemonList));

        List<Pokemon> result = fetchAllPokemonImplementation.fetch();

        Assertions.assertEquals(cachedPokemonList, result);
        verify(cache, times(1)).get("all_pokemons");
        verifyNoInteractions(fetchAllPokemonClient, namedPokemonResponseMapper);
    }

    @Test
    void shouldFetchPokemonFromClientAndCacheItWhenNotCached() {
        NamedPokemonResponse mockResponse = mock(NamedPokemonResponse.class);
        List<Pokemon> pokemonList = List.of(new Pokemon("Pikachu"), new Pokemon("Charmander"));

        when(cache.get("all_pokemons")).thenReturn(Optional.empty());
        when(fetchAllPokemonClient.fetchAllPokemon()).thenReturn(CompletableFuture.completedFuture(mockResponse));
        when(namedPokemonResponseMapper.toPokemonList(mockResponse.getResults())).thenReturn(pokemonList);

        List<Pokemon> result = fetchAllPokemonImplementation.fetch();

        Assertions.assertEquals(pokemonList, result);
        verify(cache, times(1)).get("all_pokemons");
        verify(fetchAllPokemonClient, times(1)).fetchAllPokemon();
        verify(namedPokemonResponseMapper, times(1)).toPokemonList(mockResponse.getResults());
        verify(cache, times(1)).put("all_pokemons", pokemonList);
    }

    @Test
    void shouldThrowExceptionOnInterruptedException() throws InterruptedException, ExecutionException {
        when(cache.get("all_pokemons")).thenReturn(Optional.empty());

        @SuppressWarnings("unchecked")
        CompletableFuture<NamedPokemonResponse> mockFuture = mock(CompletableFuture.class);
        when(fetchAllPokemonClient.fetchAllPokemon()).thenReturn(mockFuture);

        when(mockFuture.get()).thenThrow(new InterruptedException("Interrupted"));

        FetchPokemonException exception = Assertions.assertThrows(FetchPokemonException.class, fetchAllPokemonImplementation::fetch);
        Assertions.assertEquals("The operation was interrupted while fetching Pokémon data.", exception.getMessage());
        verify(cache, times(1)).get("all_pokemons");
    }

    @Test
    void shouldThrowExceptionOnExecutionException() throws InterruptedException, ExecutionException {
        when(cache.get("all_pokemons")).thenReturn(Optional.empty());

        @SuppressWarnings("unchecked")
        CompletableFuture<NamedPokemonResponse> mockFuture = mock(CompletableFuture.class);
        when(fetchAllPokemonClient.fetchAllPokemon()).thenReturn(mockFuture);

        when(mockFuture.get()).thenThrow(new ExecutionException("Executed", null));

        FetchPokemonException exception = Assertions.assertThrows(FetchPokemonException.class, fetchAllPokemonImplementation::fetch);
        Assertions.assertEquals("Failed to fetch Pokémon data from the external API.", exception.getMessage());
        verify(cache, times(1)).get("all_pokemons");
    }

}
