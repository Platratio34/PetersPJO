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
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.MultiNoiseSampler;

public class UnderworldBiomeSource extends BiomeSource {

    public static final Codec<UnderworldBiomeSource> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(RegistryOps.getEntryCodec(PJOBiomes.UNDERWORLD_PLAINS), RegistryOps.getEntryCodec(PJOBiomes.UNDERWORLD_OUTER), RegistryOps.getEntryCodec(PJOBiomes.UNDERWORLD_CELLING), RegistryOps.getEntryCodec(PJOBiomes.UNDERWORLD_ASPHODEL_FIELDS))
                    .apply(instance, UnderworldBiomeSource::new));

    public static void register() {
        Registry.register(Registries.BIOME_SOURCE, new Identifier(PJO.NAMESPACE, "underworld"), CODEC);
    }
    // private static final RegistryEntry<Biome> UNDERWORLD_PLAINS_ENTRY =
    // Registries.;

    private RegistryEntry<Biome> plains;
    private RegistryEntry<Biome> outer;
    private RegistryEntry<Biome> celling;
    private RegistryEntry<Biome> asphodel;

    public UnderworldBiomeSource(RegistryEntry<Biome> plains, RegistryEntry<Biome> outer, RegistryEntry<Biome> celling, RegistryEntry<Biome> asphodel) {
        this.plains = plains;
        this.outer = outer;
        this.asphodel = asphodel;
        this.celling = celling;
    }

    public Optional<Integer> getUnused() {
        return Optional.of(0);
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return Stream.of(plains, outer, celling, asphodel);
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseSampler noise) {
        int wx = BiomeCoords.toBlock(x);
        int wy = BiomeCoords.toBlock(y);
        int wz = BiomeCoords.toBlock(z);
        if (wy > UnderworldChunkGenerator.CELLING_HEIGHT - 24) {
            return celling;
        }
        double flatDistFromOrigin = Math.sqrt((wx * wx) + (wz * wz));
        if (flatDistFromOrigin > UnderworldChunkGenerator.EREBOS_SIZE) {
            return outer;
        }
        if (wy >= -24 && wy <= 32 && flatDistFromOrigin < UnderworldChunkGenerator.EREBOS_SIZE - 32
                && flatDistFromOrigin > UnderworldChunkGenerator.EREBOS_SIZE - 128) {
            return asphodel;
        }
        return plains;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

}
