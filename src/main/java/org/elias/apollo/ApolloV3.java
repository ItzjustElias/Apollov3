package org.elias.apollo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApolloV3 implements ModInitializer, ClientModInitializer {

	// Logger for debugging
	private static final Logger LOGGER = LoggerFactory.getLogger(ApolloV3.class);

	// Memory manager for monitoring and cleaning
	private final MemoryManager memoryManager = new MemoryManager(10000); // Monitor every 10 seconds
	private final ResourceCleaner resourceCleaner = new ResourceCleaner();

	// Object Pool for Particles
	private final ObjectPool<Particle> particlePool = new ObjectPool<>(Particle::new);
	private final Cache<Particle, Boolean> particleCache = new Cache<>(); // Cache for particles

	@Override
	public void onInitialize() {
		// Start the memory manager
		memoryManager.startMemoryMonitor();
		LOGGER.info("Memory manager started.");

		// Start the resource cleaner to clean up every minute (60000 ms)
		resourceCleaner.startCleanupTask(60000);
		LOGGER.info("Resource cleaner started.");

		// Preload some particles if needed
		for (int i = 0; i < 10; i++) {
			Particle particle = particlePool.acquire(); // Initialize and acquire a few particles
			resourceCleaner.registerActiveResource(particle); // Register the active particle
			particle.activate(i * 10, i * 10, 1, 1); // Activate with initial positions and velocities
		}

		// Example of using the particle pool
		Particle particle = particlePool.acquire();
		LOGGER.info("Particle acquired. In use: " + particlePool.getInUseCount());

		// Use the particle (perform operations on it)...
		particle.setX(5.0f);
		particle.setY(10.0f);
		LOGGER.info("Particle position set to (" + particle.getX() + ", " + particle.getY() + ").");

		// Deactivate the particle when done
		particle.deactivate();
		resourceCleaner.deregisterResource(particle); // Deregister the particle after use
		particlePool.release(particle); // Release the particle back to the pool
		LOGGER.info("Particle released. Available: " + particlePool.getAvailableCount());

		// Shutdown MemoryManager and ResourceCleaner when server is stopping
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> onShutdown());
	}

	@Override
	public void onInitializeClient() {
		// This can be used for client-specific initialization if needed
	}

	// Method to stop resources during mod shutdown
	public void onShutdown() {
		memoryManager.stopMemoryMonitor();
		resourceCleaner.stop();
		LOGGER.info("Memory manager and resource cleaner stopped.");
	}
}
