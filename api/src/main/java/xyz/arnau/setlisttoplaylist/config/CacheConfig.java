package xyz.arnau.setlisttoplaylist.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

import static java.time.Duration.*;
import static java.util.Arrays.asList;

@Configuration
public class CacheConfig {

    public static final String ARTISTS = "artists";
    public static final String SONGS = "songs";
    public static final String SETLISTS = "setlists";
    public static final String COVER_IMAGE = "coverImage";
    public static final String TOP_ARTISTS = "topArtists";

    @Primary
    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(asList(
                createCache(TOP_ARTISTS, ofDays(1), 10),
                createCache(ARTISTS, ofHours(1), 500),
                createCache(SONGS, ofMinutes(30), 2000),
                createCache(SETLISTS, ofMinutes(15), 200),
                createCache(COVER_IMAGE, ofMinutes(5), 100)
        ));
        return cacheManager;
    }

    private static CaffeineCache createCache(String name, Duration duration, long maxItems) {
        return new CaffeineCache(name,
                Caffeine.newBuilder()
                        .expireAfterWrite(duration)
                        .maximumSize(maxItems)
                        //.recordStats()
                        .build());
    }
}
