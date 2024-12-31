package io.acordi.looqboxbackendchallenge.dataprovider;

import io.acordi.looqboxbackendchallenge.core.dataprovider.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private final ScheduledExecutorService scheduler;

    public CacheImplementation(long ttlMillis) {
        this.ttlMillis = ttlMillis;
        this.cache = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        startCleanupTask();
        log.info("Cache implementation created");
    }

    @Override
    public void put(K key, V value) {
        int maxSize = 1000;
        if (cache.size() >= maxSize) {
            evictOldest();
        }
        cache.put(key, new CacheEntry<>(value, System.currentTimeMillis()));
    }

    @Override
    public Optional<V> get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry != null && (System.currentTimeMillis() - entry.timestamp < ttlMillis)) {
            return Optional.of(entry.value);
        } else {
            cache.remove(key); // Expire entry
            return Optional.empty();
        }
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    private void evictOldest() {
        K oldestKey = null;
        long oldestTimestamp = Long.MAX_VALUE;

        for (Map.Entry<K, CacheEntry<V>> entry : cache.entrySet()) {
            if (entry.getValue().timestamp < oldestTimestamp) {
                oldestTimestamp = entry.getValue().timestamp;
                oldestKey = entry.getKey();
            }
        }

        if (oldestKey != null) {
            cache.remove(oldestKey);
            log.info("Evicted oldest cache entry with key: {}", oldestKey);
        }
    }

    private void startCleanupTask() {
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            cache.entrySet().removeIf(entry -> (now - entry.getValue().timestamp) >= ttlMillis);
            log.debug("Periodic cleanup completed.");
        }, ttlMillis, ttlMillis, TimeUnit.MILLISECONDS);
    }

}

