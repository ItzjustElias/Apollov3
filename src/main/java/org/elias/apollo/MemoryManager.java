package org.elias.apollo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryManager.class);
    private final long interval;
    private boolean running = true;

    public MemoryManager(long interval) {
        this.interval = interval;
    }

    public void startMemoryMonitor() {
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(interval);
                    // Perform memory check and log information
                    logMemoryUsage();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void logMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        LOGGER.info("Used memory: " + (usedMemory / 1024 / 1024) + " MB");
    }

    public void stopMemoryMonitor() {
        running = false;
    }
}
