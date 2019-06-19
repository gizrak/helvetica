package me.ted.code.council.service.benchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class LongAddrBenchmark {

    private final LongAdder counter = new LongAdder();

    public class Counter implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000000; i++) {
                counter.increment();
            }
        }
    }

    private void run() throws InterruptedException {
        final int cores = Runtime.getRuntime().availableProcessors();
        final ExecutorService pool = Executors.newFixedThreadPool(cores);

        final long begin = System.currentTimeMillis();
        for (int i = 0; i < cores; i++) { pool.submit(new Counter()); }

        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        final long end = System.currentTimeMillis();

        System.out.println(counter.sum());
        System.out.println((end - begin) + "ms");
    }

    public static void main(String[] args) throws Exception {
        new LongAddrBenchmark().run();
    }
}
