package pl.klimas7.rest.counter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class Counters {
    private Map<String, AtomicInteger> counters = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Long> badCounters = new HashMap<>();

    public String count(String word) {
        AtomicInteger counter = counters.computeIfAbsent(word, val -> new AtomicInteger(1));
        return format(word, counter.getAndIncrement());
    }

    public String badCount(String word) {
        Long counter = badCounters.computeIfAbsent(word, val -> new Long(1));
        badCounters.put(word, counter + 1);
        return format(word, counter);
    }

    private String format(String word, Number count) {
        return word + " : " + String.format("%07d", count);
    }
}