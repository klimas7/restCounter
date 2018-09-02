package pl.klimas7.rest.counter;

import static pl.klimas7.rest.counter.Utils.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountersTest {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private final String NAME = "Threads_";
    @Autowired
    @Qualifier("safeCounter")
    private Counter safeCounter;

    @Test
    public void test01Thread() throws InterruptedException, ExecutionException {
        int threadCount = 1;
        count(threadCount, getGoodTask(threadCount));
    }

    @Test
    public void test02Thread() throws InterruptedException, ExecutionException {
        int threadCount = 2;
        count(threadCount, getGoodTask(threadCount));
    }

    @Test
    public void test04Thread() throws InterruptedException, ExecutionException {
        int threadCount = 4;
        count(threadCount, getGoodTask(threadCount));
    }

    @Test
    public void test08Thread() throws InterruptedException, ExecutionException {
        int threadCount = 8;
        count(threadCount, getGoodTask(threadCount));
    }

    @Test
    public void test16Thread() throws InterruptedException, ExecutionException {
        int threadCount = 16;
        count(threadCount, getGoodTask(threadCount));
    }

    @Test
    public void test32Thread() throws InterruptedException, ExecutionException {
        int threadCount = 32;
        count(threadCount, getGoodTask(threadCount));
    }

    @Test
    @Ignore
    public void test32ThreadBad() throws InterruptedException, ExecutionException {
        int threadCount = 32;
        count(threadCount, getBadTask(threadCount));
    }

    private void count(final int threadCount, Callable<String> task) throws InterruptedException, ExecutionException {

        List<Callable<String>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<String>> futures = executorService.invokeAll(tasks);

        List<String> resultList = new ArrayList<>(futures.size());
        for (Future<String> future : futures) {
            resultList.add(future.get());
        }

        Collections.sort(resultList);
        LOGGER.log(Level.WARNING, "resultList: " + resultList.toString());

        String expected = NAME + threadCount + " : " + String.format("%07d", 100 * threadCount);
        Assert.assertEquals(expected, resultList.get(threadCount - 1));
    }

    private Callable<String> getGoodTask(int threadCount) {
        return () -> {
            for (int i = 0; i < 99; i++) {
                safeCounter.count(NAME + threadCount);
            }
            return safeCounter.count(NAME + threadCount);
        };
    }

    private Callable<String> getBadTask(int threadCount) {
        return () -> {
            for (int i = 0; i < 99; i++) {
                safeCounter.count(NAME + threadCount);
            }
            return safeCounter.count(NAME + threadCount);
        };
    }

    @Test
    @Ignore
    public void initializeCounterTest() throws InterruptedException, ExecutionException {
        int nThreads = 64;
        int timeout = 60000;


        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        while ((System.currentTimeMillis() - startTime) < timeout) {
            String word = UUID.randomUUID().toString();
            Callable<String> task = () -> safeCounter.count(word);
            List<Callable<String>> tasks = Collections.nCopies(nThreads, task);
            executorService.invokeAll(tasks);

            Future<String> future = executorService.submit(task);
            String nextCount = future.get();
            String expectedNextCount = format(word, nThreads + 1);
            if (!nextCount.equals(expectedNextCount)) {
                Assert.assertEquals(expectedNextCount, nextCount);
            }
        }
    }
}