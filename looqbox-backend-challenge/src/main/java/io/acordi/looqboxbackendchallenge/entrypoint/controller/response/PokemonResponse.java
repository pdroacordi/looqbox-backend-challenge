package io.acordi.looqboxbackendchallenge.entrypoint.controller.response;

import java.util.List;

public class PokemonResponse {

    List<String> result;

    public PokemonResponse() {
    }

    public PokemonResponse(List<String> result) {
        this.result = result;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
