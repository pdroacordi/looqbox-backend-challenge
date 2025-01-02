package io.acordi.looqboxbackendchallenge.dataprovider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.acordi.looqboxbackendchallenge.dataprovider.client.FetchAllPokemonClient;
import io.acordi.looqboxbackendchallenge.dataprovider.client.response.NamedPokemonResponse;
import io.acordi.looqboxbackendchallenge.dataprovider.exception.ApiUnavailableException;
import io.acordi.looqboxbackendchallenge.dataprovider.exception.JsonParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FetchAllPokemonClientTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    private FetchAllPokemonClient fetchAllPokemonClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fetchAllPokemonClient = new FetchAllPokemonClient(httpClient, objectMapper);
    }

    @Test
    void shouldFetchPokemonSuccessfully() throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        String jsonResponse = "{\"results\": [{\"name\": \"pikachu\", \"url\": \"https://pokeapi.co/api/v2/pokemon/25\"}]}";
        NamedPokemonResponse expectedNamedPokemonResponse = new NamedPokemonResponse();
        expectedNamedPokemonResponse.setResults(List.of(new NamedPokemonResponse.Result("pikachu", "https://pokeapi.co/api/v2/pokemon/25")));
        expectedNamedPokemonResponse.setCount(1);

        var baseUrlField = FetchAllPokemonClient.class.getDeclaredField("BASE_URL");
        baseUrlField.setAccessible(true);
        baseUrlField.set(fetchAllPokemonClient, "https://pokeapi.co/api/v2");

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(httpClient.sendAsync(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        when(objectMapper.readValue(jsonResponse, NamedPokemonResponse.class)).thenReturn(expectedNamedPokemonResponse);

        NamedPokemonResponse response = fetchAllPokemonClient.fetchAllPokemon().join();

        Assertions.assertEquals(expectedNamedPokemonResponse.getResults().size(), response.getResults().size());
        Assertions.assertEquals(expectedNamedPokemonResponse.getResults().get(0).getName(), response.getResults().get(0).getName());
    }

    @Test
    void shouldThrowExceptionForNon200StatusCode() throws NoSuchFieldException, IllegalAccessException {
        var baseUrlField = FetchAllPokemonClient.class.getDeclaredField("BASE_URL");
        baseUrlField.setAccessible(true);
        baseUrlField.set(fetchAllPokemonClient, "https://pokeapi.co/api/v2");

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(500);
        when(httpClient.sendAsync(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        CompletionException exception = Assertions.assertThrows(CompletionException.class,
                () -> fetchAllPokemonClient.fetchAllPokemon().join());

        Assertions.assertInstanceOf(ApiUnavailableException.class, exception.getCause());
        Assertions.assertEquals("The PokéAPI is currently not available: 500", exception.getCause().getMessage());
    }

    @Test
    void shouldThrowJsonParseExceptionForInvalidJson() throws Exception {
        String invalidJson = "invalid-json";

        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(invalidJson);
        when(httpClient.sendAsync(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));
        when(objectMapper.readValue(invalidJson, NamedPokemonResponse.class))
                .thenThrow(new JsonProcessingException("Invalid JSON") {});

        var baseUrlField = FetchAllPokemonClient.class.getDeclaredField("BASE_URL");
        baseUrlField.setAccessible(true);
        baseUrlField.set(fetchAllPokemonClient, "https://pokeapi.co/api/v2");

        CompletionException exception = Assertions.assertThrows(CompletionException.class,
                () -> fetchAllPokemonClient.fetchAllPokemon().join());

        Assertions.assertInstanceOf(JsonParseException.class, exception.getCause());
        Assertions.assertEquals("Failed to parse JSON response from API", exception.getCause().getMessage());
    }

    @Test
    void shouldHandleEmptyResponseBody() throws NoSuchFieldException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(null);
        when(httpClient.sendAsync(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        var baseUrlField = FetchAllPokemonClient.class.getDeclaredField("BASE_URL");
        baseUrlField.setAccessible(true);
        baseUrlField.set(fetchAllPokemonClient, "https://pokeapi.co/api/v2");

        CompletionException exception = Assertions.assertThrows(CompletionException.class,
                () -> fetchAllPokemonClient.fetchAllPokemon().join());

        Assertions.assertInstanceOf(JsonParseException.class, exception.getCause());
        Assertions.assertEquals("API returned an empty or null response.", exception.getCause().getMessage());
    }
}
