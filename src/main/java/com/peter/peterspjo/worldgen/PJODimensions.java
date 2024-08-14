package com.peter.peterspjo.worldgen;

import com.peter.peterspjo.PJO;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class PJODimensions {

    public static final RegistryKey<DimensionType> UNDERWORLD_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            PJO.id("underworld"));
    public static final RegistryKey<World> UNDERWORLD = RegistryKey.of(RegistryKeys.WORLD,
            PJO.id("underworld"));
    public static final RegistryKey<DimensionType> LABYRINTH_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            PJO.id("labyrinth"));
    public static final RegistryKey<World> LABYRINTH = RegistryKey.of(RegistryKeys.WORLD,
            PJO.id("labyrinth"));

}
