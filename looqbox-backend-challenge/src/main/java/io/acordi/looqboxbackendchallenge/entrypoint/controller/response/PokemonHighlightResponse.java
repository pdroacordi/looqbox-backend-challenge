package io.acordi.looqboxbackendchallenge.entrypoint.controller.response;

import java.util.List;

public class PokemonHighlightResponse {

    List<Result> result;

    public PokemonHighlightResponse() {
    }

    public PokemonHighlightResponse(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        private String name;
        private String highlight;

        public Result() {
        }

        public Result(String name, String highlight) {
            this.name = name;
            this.highlight = highlight;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHighlight() {
            return highlight;
        }

        public void setHighlight(String highlight) {
            this.highlight = highlight;
        }
    }

}
