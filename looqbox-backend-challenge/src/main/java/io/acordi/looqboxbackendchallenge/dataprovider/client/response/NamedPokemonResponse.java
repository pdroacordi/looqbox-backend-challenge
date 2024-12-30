package io.acordi.looqboxbackendchallenge.dataprovider.client.response;

import java.util.List;

public class NamedPokemonResponse {

    private int count;

    private String next;

    private String previous;

    private List<Result> results;

    public NamedPokemonResponse() {
    }

    public NamedPokemonResponse(int count, String next, String previous, List<Result> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {

        private String name;

        private String url;

        public Result() {
        }

        public Result(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
