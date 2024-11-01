package org.elias.apollo;

public class MathUtil {

    // Precomputed factorials for terms up to the 10th in the Taylor series
    private static final float[] FACTORIALS = {
            1.0f, 1.0f, 2.0f, 6.0f, 24.0f, 120.0f, 720.0f, 5040.0f, 40320.0f, 362880.0f, 3628800.0f
    };

    // Optimized sine calculation using a simplified Taylor series approach
    public static float sin(float radians) {
        // Normalize radians to [-π, π] for faster convergence
        radians %= (float) (2 * Math.PI);
        if (radians > Math.PI) radians -= (float) (2 * Math.PI);
        else if (radians < -Math.PI) radians += (float) (2 * Math.PI);

        // Taylor series for sin(x) centered around 0
        float sin = radians;
        float term = radians;
        for (int n = 1; n < 10; n++) {
            term *= -radians * radians / ((2 * n) * (2 * n + 1));
            sin += term;
        }
        return sin;
    }

    // Optimized cosine calculation using a simplified Taylor series approach
    public static float cos(float radians) {
        // Normalize radians to [-π, π] for faster convergence
        radians %= (float) (2 * Math.PI);
        if (radians > Math.PI) radians -= (float) (2 * Math.PI);
        else if (radians < -Math.PI) radians += (float) (2 * Math.PI);

        // Taylor series for cos(x) centered around 0
        float cos = 1;
        float term = 1;
        for (int n = 1; n < 10; n++) {
            term *= -radians * radians / ((2 * n - 1) * (2 * n));
            cos += term;
        }
        return cos;
    }
}
