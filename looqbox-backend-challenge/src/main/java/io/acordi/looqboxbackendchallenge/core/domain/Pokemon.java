package io.acordi.looqboxbackendchallenge.core.domain;

public class Pokemon {

    private String name;


    public Pokemon() {
    }

    public Pokemon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
