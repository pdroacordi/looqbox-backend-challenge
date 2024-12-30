package io.acordi.looqboxbackendchallenge.dataprovider.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.acordi.looqboxbackendchallenge.dataprovider.client.response.NamedPokemonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class FetchAllPokemonClient {

    private static final Logger log = LoggerFactory.getLogger(FetchAllPokemonClient.class);

    private final int LIMIT = 1302; // Max Pokémon per API request. As this API will not reach prod,
                                    // there's no problem doing like "high limit, low requests", we're not
                                    // trying to save any resources here.

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${acordi.pokeapi.url}")
    private String BASE_URL;

    public FetchAllPokemonClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    //Here, I decided to use asynchronous fetching because of the possibility of fetching low limit at a time.
    //If the limit is defined to 20, fetching 1302 pokémon would require 66 requests. Doing that synchronously,
    //In a sequential way, would slow down the performance.
    public CompletableFuture<NamedPokemonResponse> fetchAllPokemon() {
        List<NamedPokemonResponse.Result> allResults = new ArrayList<>();
        String url = BASE_URL + "/pokemon?limit=" + LIMIT;

        return fetchAllPagesAsync(url, allResults)
                .thenApply(results -> {
                    NamedPokemonResponse aggregatedResponse = new NamedPokemonResponse();
                    aggregatedResponse.setCount(results.size());
                    aggregatedResponse.setResults(results);
                    return aggregatedResponse;
                });
    }

    private CompletableFuture<List<NamedPokemonResponse.Result>> fetchAllPagesAsync(
            String url, List<NamedPokemonResponse.Result> results) {
        if(url == null) {
            return CompletableFuture.completedFuture(results);
        }
        log.info("Fetching all pokemon pages from {}", url);
        return fetchPageAsync(url)
                .thenCompose(response -> {
                    results.addAll(response.getResults());
                    return fetchAllPagesAsync(response.getNext(), results);
                });
    }

    private CompletableFuture<NamedPokemonResponse> fetchPageAsync(String url){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseResponse);
    }

    private NamedPokemonResponse parseResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, NamedPokemonResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Pokémon API response", e);
        }
    }
}
