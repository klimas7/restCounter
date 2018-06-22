package pl.klimas7.rest.counter;

import static pl.klimas7.rest.counter.Utils.format;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class Counters {
    private Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());
    private Map<String, Long> badCounters = new HashMap<>();
    private Map<String, AtomicInteger> bad2Counters = new HashMap<>();

    public String count(String word) {
        AtomicInteger counter = counters.computeIfAbsent(word, val -> new AtomicInteger(1));
        return format(word, counter.getAndIncrement());
    }

    public String badCount(String word) {
        Long counter = badCounters.computeIfAbsent(word, val -> 1L);
        badCounters.put(word, counter + 1);
        return format(word, counter);
    }

    public String bad2Count(String word) {
        AtomicInteger counter = bad2Counters.computeIfAbsent(word, val -> new AtomicInteger(1));
        return format(word, counter.getAndIncrement());
    }
}
