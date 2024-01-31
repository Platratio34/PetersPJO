package com.peter.peterspjo.worldgen;

import java.util.Optional;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peter.peterspjo.PJO;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.MultiNoiseSampler;

public class UnderworldBiomeSource extends BiomeSource {
    
    private static final RegistryKey<Biome> UNDERWORLD_PLAINS = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(PJO.NAMESPACE, "underworld_plains"));
            private static final RegistryKey<Biome> UNDERWORLD_OUTER = RegistryKey.of(RegistryKeys.BIOME,
                    new Identifier(PJO.NAMESPACE, "underworld_outer"));

    public static final Codec<UnderworldBiomeSource> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(RegistryOps.getEntryCodec(UNDERWORLD_PLAINS), RegistryOps.getEntryCodec(UNDERWORLD_OUTER))
                    .apply(instance, UnderworldBiomeSource::new));

    public static void register() {
        Registry.register(Registries.BIOME_SOURCE, new Identifier(PJO.NAMESPACE, "underworld"), CODEC);
    }
    // private static final RegistryEntry<Biome> UNDERWORLD_PLAINS_ENTRY = Registries.;

    private RegistryEntry<Biome> plains;
    private RegistryEntry<Biome> outer;

    public UnderworldBiomeSource(RegistryEntry<Biome> plains, RegistryEntry<Biome> outer) {
        this.plains = plains;
        this.outer = outer;
    }
    
    public Optional<Integer> getUnused() {
        return Optional.of(0);
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return Stream.of(plains, outer);
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseSampler noise) {
        double flatDistFromOrigin = Math.sqrt((x * x) + (z * z));
        if (flatDistFromOrigin > 512) {
            return outer;
        }
        return plains;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

}
