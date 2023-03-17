package net.benfro.concalc.ruleapi.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.benfro.concalc.model.StandardHolidays;
import net.benfro.concalc.ruleapi.TollFreeDateLookup;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * A component that caches toll-free days if needed. Dependent on custom implementation of TollFreeDateCacheLoadingRule.
 */
@Component
public class TollFreeDateCache {

    private final LoadingCache<Integer, List<LocalDateTime>> cache;

    private final TollFreeDateCacheLoadingRule tollFreeDateCacheLoadingRule;

    public TollFreeDateCache(TollFreeDateCacheLoadingRule tollFreeDateCacheLoadingRule) {
        this.tollFreeDateCacheLoadingRule = tollFreeDateCacheLoadingRule;
        CacheLoader<Integer, List<LocalDateTime>> loader = new CacheLoader<>() {
            @Override
            public List<LocalDateTime> load(Integer year) {
                return tollFreeDateCacheLoadingRule.loadDatesToCacheRule(year);
            }
        };
        cache = CacheBuilder.newBuilder().maximumSize(10).build(loader);
    }

    public Optional<List<LocalDateTime>> get(int year) throws ExecutionException {
        return Optional.ofNullable(cache.get(year));
    }



}
