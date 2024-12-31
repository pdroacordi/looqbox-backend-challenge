package io.acordi.looqboxbackendchallenge.dataprovider;

import io.acordi.looqboxbackendchallenge.core.dataprovider.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CacheImplementation<K, V> implements Cache<K, V> {

    private static final Logger log = LoggerFactory.getLogger(CacheImplementation.class);

    private static class CacheEntry<V> {
        V value;
        long timestamp;

        CacheEntry(V value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    private final Map<K, CacheEntry<V>> cache;
    private final long ttlMillis;

    public CacheImplementation(long ttlMillis) {
        this.ttlMillis = ttlMillis;
        int maxSize = 1000;
        this.cache = new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                return size() > maxSize;
            }
        };

        log.info("Cache implementation created");
    }

    @Override
    public void put(K key, V value) {
        synchronized (cache) {
            cache.put(key, new CacheEntry<>(value, System.currentTimeMillis()));
        }
    }

    @Override
    public Optional<V> get(K key) {
        synchronized (cache) {
            CacheEntry<V> entry = cache.get(key);
            if (entry != null && (System.currentTimeMillis() - entry.timestamp < ttlMillis)) {
                return Optional.of(entry.value);
            } else {
                cache.remove(key); // Expire entry
                return Optional.empty();
            }
        }
    }

    @Override
    public void remove(K key) {
        synchronized (cache) {
            cache.remove(key);
        }
    }
}
