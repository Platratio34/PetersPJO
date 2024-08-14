package com.peter.peterspjo.worldgen;

import com.peter.peterspjo.PJO;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public class PJOBiomes {

    public static final RegistryKey<Biome> UNDERWORLD_PLAINS = RegistryKey.of(RegistryKeys.BIOME,
            PJO.id("underworld_plains"));
    public static final RegistryKey<Biome> UNDERWORLD_OUTER = RegistryKey.of(RegistryKeys.BIOME,
            PJO.id("underworld_outer"));
    public static final RegistryKey<Biome> UNDERWORLD_CELLING = RegistryKey.of(RegistryKeys.BIOME,
            PJO.id("underworld_celling"));
    public static final RegistryKey<Biome> UNDERWORLD_ASPHODEL_FIELDS = RegistryKey.of(RegistryKeys.BIOME,
            PJO.id("underworld_asphodel_fields"));

}
