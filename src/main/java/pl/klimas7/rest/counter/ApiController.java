package pl.klimas7.rest.counter;


import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private Counters counters;

    @GetMapping("/count/{word}")
    public String count(@PathVariable String word) {
        return counters.count(word);
    }

    @GetMapping("/badCounter/{word}")
    public String badCounter(@PathVariable String word) {
        return counters.badCount(word);
    }
}
