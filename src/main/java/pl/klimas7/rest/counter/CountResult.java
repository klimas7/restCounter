package pl.klimas7.rest.counter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class CountResult {
    private final String world;
    private final Long count;

    public static CountResult of(String world, Long count) {
        return new CountResult(world, count);
    }

    public static CountResult of(String world, Integer count) {
        return new CountResult(world, Long.valueOf(count));
    }
}
