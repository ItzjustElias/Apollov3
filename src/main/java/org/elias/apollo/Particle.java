package org.elias.apollo;

public class Particle {
    private float x;
    private float y;
    private float velocityX;
    private float velocityY;
    private boolean isActive;

    // Constructor
    public Particle() {
        this.x = 0;
        this.y = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isActive = false; // Start as inactive
    }

    // Update the particle's position based on its velocity
    public void update(float deltaTime) {
        if (isActive) {
            x += velocityX * deltaTime;
            y += velocityY * deltaTime;

            // Example logic for deactivating particle if it goes out of bounds
            if (x < 0 || x > 800 || y < 0 || y > 600) { // Assuming a screen size of 800x600
                isActive = false;
            }
        }
    }

    // Activate the particle with initial velocity
    public void activate(float startX, float startY, float velocityX, float velocityY) {
        this.x = startX;
        this.y = startY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.isActive = true; // Reset the particle to active
    }

    // Deactivate the particle
    public void deactivate() {
        isActive = false;
    }

    // Getters for particle properties
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters for particle position only (if needed)
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
