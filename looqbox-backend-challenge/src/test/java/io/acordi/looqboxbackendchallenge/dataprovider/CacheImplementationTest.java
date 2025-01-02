package io.acordi.looqboxbackendchallenge.dataprovider;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CacheImplementationTest {

    private CacheImplementation<String, List<Pokemon>> cache;

    @BeforeEach
    void setUp() {
        cache = new CacheImplementation<>(100);
    }

    @Test
    void shouldStoreAndRetrieveValue(){
        List<Pokemon> pokemon = new ArrayList<Pokemon>(Arrays.asList( new Pokemon("Charmander"),
                new Pokemon("Squirtle"), new Pokemon("Bulbasaur")));
        cache.put("all_pokemon", pokemon);

        Optional<List<Pokemon>> retrievedPokemon = cache.get("all_pokemon");

        Assertions.assertTrue(retrievedPokemon.isPresent());
        Assertions.assertEquals("Charmander", retrievedPokemon.get().get(0).getName());
    }

    @Test
    void shouldExpireValueAfterTTL() throws InterruptedException {
        List<Pokemon> pokemon = new ArrayList<Pokemon>(Arrays.asList( new Pokemon("Charmander"),
                new Pokemon("Squirtle"), new Pokemon("Bulbasaur")));
        cache.put("all_pokemon", pokemon);

        Thread.sleep(150);

        Optional<List<Pokemon>> retrieved = cache.get("all_pokemon");
        Assertions.assertFalse(retrieved.isPresent());
    }

    @Test
    void shouldRemoveValueManually() {
        List<Pokemon> pokemon = new ArrayList<Pokemon>(Arrays.asList( new Pokemon("Charmander"),
                new Pokemon("Squirtle"), new Pokemon("Bulbasaur")));
        cache.put("all_pokemon", pokemon);
        cache.remove("all_pokemon");

        Optional<List<Pokemon>> retrieved = cache.get("all_pokemon");
        Assertions.assertFalse(retrieved.isPresent());
    }

    @Test
    void shouldEvictOldestEntryWhenMaxSizeReached() {
        CacheImplementation<String, String> limitedCache = new CacheImplementation<>(100);
        limitedCache.put("key1", "value1");
        limitedCache.put("key2", "value2");

        for (int i = 3; i <= 1002; i++) {
            limitedCache.put("key" + i, "value" + i);
        }

        Optional<String> retrieved = limitedCache.get("key1");
        Assertions.assertFalse(retrieved.isPresent());
    }

    @Test
    void shouldPerformPeriodicCleanup() throws InterruptedException {
        List<Pokemon> pokemon = new ArrayList<Pokemon>(Arrays.asList( new Pokemon("Charmander"),
                new Pokemon("Squirtle"), new Pokemon("Bulbasaur")));
        cache.put("all_pokemon", pokemon);

        // Wait for cleanup to run
        Thread.sleep(150);

        Optional<List<Pokemon>> retrieved = cache.get("all_pokemon");
        Assertions.assertFalse(retrieved.isPresent());
    }
}
