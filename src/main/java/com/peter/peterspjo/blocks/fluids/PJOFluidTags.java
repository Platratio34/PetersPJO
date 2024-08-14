package com.peter.peterspjo.blocks.fluids;

import com.peter.peterspjo.PJO;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PJOFluidTags {

    public static final TagKey<Fluid> STYX = of("styx");

    private static TagKey<Fluid> of(String id) {
        return TagKey.of(RegistryKeys.FLUID, PJO.id(id));
    }
}
