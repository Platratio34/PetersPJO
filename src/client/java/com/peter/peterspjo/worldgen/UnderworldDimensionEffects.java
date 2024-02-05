package com.peter.peterspjo.worldgen;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.mixin.client.rendering.DimensionEffectsAccessor;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class UnderworldDimensionEffects extends DimensionEffects {

    public static void register() {
        DimensionEffectsAccessor.getIdentifierMap()
                .put(new Identifier(PJO.NAMESPACE, "underworld"), new UnderworldDimensionEffects());
    }

    public UnderworldDimensionEffects() {
        super(Float.NaN, true, SkyType.NONE, false, true);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        double dist = Math.sqrt(camX*camX + camY*camY);
        double oDist = UnderworldChunkGenerator.EREBOS_SIZE + UnderworldChunkGenerator.OUTER_STEP_OFFSET
                + (UnderworldChunkGenerator.OUTER_STEP_SIZE * (UnderworldChunkGenerator.CELLING_HEIGHT - 24));
        return camY < -32 || camY > UnderworldChunkGenerator.CELLING_HEIGHT || dist > oDist;
    }

}
