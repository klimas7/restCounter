package pl.klimas7.rest.counter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {
    private final Counter safeCounters;
    private final Counter halfSafeCounters;
    private final Counter unsafeCounter;

    public ApiController(@Qualifier(value = "safeCounter") Counter safeCounters,
                         @Qualifier(value = "halfSafeCounter") Counter halfSafeCounters,
                         @Qualifier(value = "unsafeCounter") Counter unsafeCounter) {
        this.safeCounters = safeCounters;
        this.halfSafeCounters = halfSafeCounters;
        this.unsafeCounter = unsafeCounter;
    }

    @GetMapping("/count/{word}")
    public CountResult count(@PathVariable String word, HttpServletRequest request) {
        log.info("Remote address: " + request.getRemoteAddr());
        return safeCounters.count(word);
    }

    /*@GetMapping("/badCount/{word}")
    public String badCount(@PathVariable String word) {
        return counters.badCount(word);
    }*/
}
