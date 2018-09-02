package pl.klimas7.rest.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    @Qualifier(value = "safeCounter")
    private Counter safeCounters;

    @Autowired
    @Qualifier(value = "halfSafeCounter")
    private Counter halfSafeCounters;

    @Autowired
    @Qualifier(value = "unsafeCounter")
    private Counter unsafeCounter;

    @GetMapping("/count/{word}")
    public String count(@PathVariable String word) {
        return safeCounters.count(word);
    }

    /*@GetMapping("/badCount/{word}")
    public String badCount(@PathVariable String word) {
        return counters.badCount(word);
    }*/
}
