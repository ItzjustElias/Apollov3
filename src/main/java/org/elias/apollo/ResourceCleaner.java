package org.elias.apollo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ResourceCleaner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceCleaner.class);
    private ScheduledExecutorService executorService;
    private volatile boolean running = true;

    // Cache for weak references to active particles
    private final Cache<Particle, Boolean> particleCache = new Cache<>();

    public ResourceCleaner() {
        // Constructor can remain empty if no initialization is required
    }

    public void startCleanupTask(long interval) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::performCleanup, 0, interval, TimeUnit.MILLISECONDS);
    }

    private void performCleanup() {
        if (!running) {
            return; // Exit if the cleaner is not running
        }

        // Clean up particles that are no longer active
        int removedCount = 0;

        // Create an iterator over the keys of the cache
        Iterator<Particle> iterator = particleCache.cache.keySet().iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            // Check if the particle is no longer active or has been collected
            if (!particle.isActive() || particleCache.get(particle) == null) {
                iterator.remove(); // Remove it from the cache
                removedCount++;
            }
        }

        // Log the number of removed particles
        if (removedCount > 0) {
            LOGGER.info("Cleaned up {} inactive particles.", removedCount);
        }
    }

    // Method to add an active particle (or other resource)
    public void registerActiveResource(Particle particle) {
        particleCache.put(particle, true); // Cache the particle
        LOGGER.info("Registered new active particle.");
    }

    // Method to deregister a resource when it's no longer needed
    public void deregisterResource(Particle particle) {
        particleCache.clear(); // Clear the cache if needed (optional)
        LOGGER.info("Deregistered particle.");
    }

    public void stop() {
        running = false;
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
            LOGGER.info("Resource cleaner stopped.");
        }
    }
}
