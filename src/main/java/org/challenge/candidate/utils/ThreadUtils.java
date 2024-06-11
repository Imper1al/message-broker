package org.challenge.candidate.utils;

import org.apache.log4j.Logger;
import org.challenge.candidate.services.ScanService;

import java.util.concurrent.CountDownLatch;

public class ThreadUtils {

    private static final Logger LOGGER = Logger.getLogger(ScanService.class);
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void countDown() {
        latch.countDown();
    }
}
