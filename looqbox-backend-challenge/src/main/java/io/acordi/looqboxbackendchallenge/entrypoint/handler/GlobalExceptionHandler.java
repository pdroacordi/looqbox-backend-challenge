package io.acordi.looqboxbackendchallenge.entrypoint.handler;

import io.acordi.looqboxbackendchallenge.dataprovider.exception.ApiUnavailableException;
import io.acordi.looqboxbackendchallenge.dataprovider.exception.FetchPokemonException;
import io.acordi.looqboxbackendchallenge.dataprovider.exception.JsonParseException;
import io.acordi.looqboxbackendchallenge.entrypoint.handler.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException e) {
        var response = new ExceptionResponse.Builder()
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ExceptionResponse> handleException(JsonParseException e) {
        var response = new ExceptionResponse.Builder()
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY); // BAD_GATEWAY (502) indicates that the service
                                                                       // received an invalid response from an upstream server
    }

    @ExceptionHandler(ApiUnavailableException.class)
    public ResponseEntity<ExceptionResponse> handleException(ApiUnavailableException e) {
        var response = new ExceptionResponse.Builder()
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE); // SERVICE_UNAVAILABLE (503) Indicates that the
                                                                               // external API is temporarily unavailable.
    }

    @ExceptionHandler(FetchPokemonException.class)
    public ResponseEntity<ExceptionResponse> handleException(FetchPokemonException e) {
        var response = new ExceptionResponse.Builder()
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
