package org.elias.apollo.mixin;

import net.minecraft.util.math.MathHelper;
import org.elias.apollo.MathUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MathHelper.class)
public class MixinMathHelper {

    /**
     * @author Elias
     * @reason my own thing
     */
    @Overwrite
    public static float sin(float f) {
        return MathUtil.sin(f);
    }

    /**
     * @author Elias
     * @reason my own thing
     */
    @Overwrite
    public static float cos(float f) {
        return MathUtil.cos(f);
    }
}