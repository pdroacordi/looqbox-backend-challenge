package io.acordi.looqboxbackendchallenge.entrypoint.controller;

import io.acordi.looqboxbackendchallenge.core.usecase.GetPokemonUseCase;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.mapper.PokemonResponseMapper;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonHighlightResponse;
import io.acordi.looqboxbackendchallenge.entrypoint.controller.response.PokemonResponse;
import io.acordi.looqboxbackendchallenge.entrypoint.handler.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/pokemons", produces = {"application/json"})
@Tag(name="Pokémon Operations")
public class PokemonController {

    private final PokemonResponseMapper pokemonResponseMapper;
    private final GetPokemonUseCase getPokemonUseCase;

    public PokemonController(PokemonResponseMapper pokemonResponseMapper, GetPokemonUseCase getPokemonUseCase) {
        this.pokemonResponseMapper = pokemonResponseMapper;
        this.getPokemonUseCase = getPokemonUseCase;
    }

    @Operation(
            summary = "Fetch Pokémon",
            description = "Fetches a list of Pokémon from the PokéAPI. The results can be optionally filtered using the 'query' header and sorted using the 'sort' header.",
            method = "GET",
            parameters = {
                    @Parameter(name = "query", description = "Filter Pokémon by name or partial match (optional). Partial or full Pokémon name. Case insensitive.",
                            required = false, in = ParameterIn.HEADER),
                    @Parameter(name = "sort", description = "Sort Pokémon by specific criteria (e.g., alphabetical, by name length). Alphabetical sort by default.",
                            required = false, in = ParameterIn.HEADER, schema = @Schema(allowableValues = {"ALPHABETICAL", "LENGTH", "0", "1"}, defaultValue = "ALPHABETICAL") )
            }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully returned the Pokémon.", content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PokemonResponse.class),
                        examples = @ExampleObject(
                                value = "{\"result\":[\"Pikachu\",\"Charmander\"]}"
                        )
                )
        }),
        @ApiResponse(responseCode = "502", description = "Indicates that the API received an invalid response from an upstream server.", content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class),
                        examples = @ExampleObject(
                                value = "{ \"message\": \"Failed to parse JSON response from API\", \"timestamp\": \"2025-01-02T10:15:30\", \"status\": 502}"
                        )
                )
        }),
        @ApiResponse(responseCode = "503", description = "Indicates that the external API is temporarily unavailable.", content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class),
                        examples = @ExampleObject(
                                value = "{ \"message\": \"The PokéAPI is currently not available: 500\", \"timestamp\": \"2025-01-02T10:15:30\", \"status\": 503}"
                        )
                )
        }),
    })
    @GetMapping()
    public ResponseEntity<PokemonResponse> getPokemon(
            @RequestHeader(value = "query", required = false) String query,
            @RequestHeader(value = "sort", required = false) String sort
            ) {
        PokemonResponse response = pokemonResponseMapper.toPokemonResponse( getPokemonUseCase.getPokemon(query, sort) );
        return ResponseEntity.ok( response );
    }


    @Operation(
            summary = "Fetch Pokémon with highlight",
            description = "Fetches a list of Pokémon from the PokéAPI. The results can be optionally filtered and highlighted using the 'query' header and sorted using the 'sort' header.",
            method = "GET",
            parameters = {
                    @Parameter(name = "query", description = "Filter Pokémon by name or partial match (optional). Partial or full Pokémon name. Case insensitive.",
                            required = false, in = ParameterIn.HEADER),
                    @Parameter(name = "sort", description = "Sort Pokémon by specific criteria (e.g., alphabetical, by name length). Alphabetical sort by default.",
                            required = false, in = ParameterIn.HEADER, schema = @Schema(allowableValues = {"ALPHABETICAL", "LENGTH", "0", "1"}, defaultValue = "ALPHABETICAL") )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the Pokémon.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PokemonHighlightResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Example with query parameter filled.",
                                            value = "{\"result\":[{\"name\": \"Pikachu\", \"highlight\": \"pi\"},{\"name\": \"Pidgeot\", \"highlight\": \"pi\"}]}"
                                    ),
                                    @ExampleObject(
                                            name = "Example without query parameter filled.",
                                            value = "{\"result\":[{\"name\": \"Pikachu\", \"highlight\": null},{\"name\": \"Pidgeot\", \"highlight\": null}]}"
                                    )
                            }
                    )
            }),
            @ApiResponse(responseCode = "502", description = "Indicates that the API received an invalid response from an upstream server.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"message\": \"Failed to parse JSON response from API\", \"timestamp\": \"2025-01-02T10:15:30\", \"status\": 502}"
                            )
                    )
            }),
            @ApiResponse(responseCode = "503", description = "Indicates that the external API is temporarily unavailable.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"message\": \"The PokéAPI is currently not available: 500\", \"timestamp\": \"2025-01-02T10:15:30\", \"status\": 503}"
                            )
                    )
            }),
    })
    @GetMapping("/highlight")
    public ResponseEntity<PokemonHighlightResponse> getPokemonHighlight(
            @RequestHeader(value = "query", required = false) String query,
            @RequestHeader(value = "sort", required = false) String sort
    ) {
        PokemonHighlightResponse response = pokemonResponseMapper.toPokemonHighlightResponse( getPokemonUseCase.getPokemon(query, sort), query );
        return ResponseEntity.ok( response );
    }

}
