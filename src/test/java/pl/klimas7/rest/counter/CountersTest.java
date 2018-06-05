package pl.klimas7.rest.counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountersTest {

    @Autowired
    private Counters counters;

    private void test(final int threadCount, String name) throws InterruptedException, ExecutionException {
        Callable<String> task = () -> {
            for (int i = 0; i < 99; i++) {
                counters.count(name);
            }
            return counters.count(name);
        };

        List<Callable<String>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<String>> futures = executorService.invokeAll(tasks);
        List<String> resultList = new ArrayList<>(futures.size());
        // Check for exceptions
        for (Future<String> future : futures) {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        List<String> expectedList = new ArrayList<>(threadCount);
        for (long i = 1; i <= threadCount; i++) {
            expectedList.add(name + " : " + String.format("%07d", 100 * i));
        }
        Collections.sort(resultList);
        Assert.assertEquals(expectedList.get(threadCount - 1), resultList.get(threadCount - 1));
    }

    @Test
    public void test01() throws InterruptedException, ExecutionException {
        test(1, "Test_1");
    }

    @Test
    public void test02() throws InterruptedException, ExecutionException {
        test(2, "Test_2");
    }

    @Test
    public void test04() throws InterruptedException, ExecutionException {
        test(4, "Test_4");
    }

    @Test
    public void test08() throws InterruptedException, ExecutionException {
        test(8, "Test_8");
    }

    @Test
    public void test16() throws InterruptedException, ExecutionException {
        test(16, "Test_16");
    }

    @Test
    public void test32() throws InterruptedException, ExecutionException {
        test(32, "Test_32");
    }
}