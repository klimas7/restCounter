package pl.klimas7.rest.counter;

import static pl.klimas7.rest.counter.Utils.format;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class HalfSafeCounter implements Counter {
    private Map<String, AtomicInteger> counters = new HashMap<>();

    @Override
    public CountResult count(String word) {
        AtomicInteger counter = counters.computeIfAbsent(word, val -> new AtomicInteger(1));
        return CountResult.of(word, counter.getAndIncrement());
    }
}
