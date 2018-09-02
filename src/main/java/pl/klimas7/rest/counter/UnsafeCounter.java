package pl.klimas7.rest.counter;

import static pl.klimas7.rest.counter.Utils.format;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UnsafeCounter implements Counter {
    private Map<String, Long> counters = new HashMap<>();

    @Override
    public String count(String word) {
        Long counter = counters.computeIfAbsent(word, val -> 1L);
        counters.put(word, counter + 1);
        return format(word, counter);
    }
}
