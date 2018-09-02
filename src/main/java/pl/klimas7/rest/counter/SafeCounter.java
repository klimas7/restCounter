package pl.klimas7.rest.counter;

import static pl.klimas7.rest.counter.Utils.format;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class SafeCounter implements Counter {
    private Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Override
    public String count(String word) {
        AtomicInteger counter = counters.computeIfAbsent(word, val -> new AtomicInteger(1));
        return format(word, counter.getAndIncrement());
    }
}
