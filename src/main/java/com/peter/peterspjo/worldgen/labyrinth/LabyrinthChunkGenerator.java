package com.peter.peterspjo.worldgen.labyrinth;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;
import com.peter.peterspjo.worldgen.labyrinth.sections.LabyrinthSection;

import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.GenerationStep.Carver;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;

public class LabyrinthChunkGenerator extends ChunkGenerator {

    public static final Codec<LabyrinthChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> {
            return generator.biomeSource;
        }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((generator) -> {
            return generator.settings;
        })).apply(instance, instance.stable(LabyrinthChunkGenerator::new));
    });
    private final RegistryEntry<ChunkGeneratorSettings> settings;
    private static final BlockState AIR = Blocks.AIR.getDefaultState();

    public static void register() {
        Registry.register(Registries.CHUNK_GENERATOR, new Identifier(PJO.NAMESPACE, "labyrinth"), CODEC);
    }

    public LabyrinthChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion var1, long var2, NoiseConfig var4, BiomeAccess var5, StructureAccessor var6,
            Chunk var7, Carver var8) {

    }

    @Override
    public void buildSurface(ChunkRegion var1, StructureAccessor var2, NoiseConfig var3, Chunk var4) {

    }

    @Override
    public void populateEntities(ChunkRegion var1) {

    }

    @Override
    public int getWorldHeight() {
        return 128;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig,
            StructureAccessor structureAccessor,
            Chunk chunk) {
        GenerationShapeConfig generationShapeConfig = ((ChunkGeneratorSettings) this.settings.value())
                .generationShapeConfig().trimHeight(chunk.getHeightLimitView());
        int i = generationShapeConfig.minimumY();
        int j = MathHelper.floorDiv(i, generationShapeConfig.verticalCellBlockCount());
        int k = MathHelper.floorDiv(generationShapeConfig.height(), generationShapeConfig.verticalCellBlockCount());
        if (k <= 0) {
            return CompletableFuture.completedFuture(chunk);
        } else {
            int l = chunk.getSectionIndex(k * generationShapeConfig.verticalCellBlockCount() - 1 + i);
            int m = chunk.getSectionIndex(i);
            Set<ChunkSection> set = Sets.newHashSet();

            for (int n = l; n >= m; --n) {
                ChunkSection chunkSection = chunk.getSection(n);
                chunkSection.lock();
                set.add(chunkSection);
            }

            return CompletableFuture.supplyAsync(Util.debugSupplier("wgen_fill_labyrinth", () -> {
                return this.populateNoise(blender, structureAccessor, noiseConfig, chunk, j, k);
            }), Util.getMainWorkerExecutor()).whenCompleteAsync((chunkx, throwable) -> {
                Iterator var3 = set.iterator();

                while (var3.hasNext()) {
                    ChunkSection chunkSection = (ChunkSection) var3.next();
                    chunkSection.unlock();
                }

            }, executor);
        }
    }

    private Chunk populateNoise(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig,
            Chunk chunk, int minimumCellY, int cellHeight) {
        ChunkPos chunkPos = chunk.getPos();
        int offsetX = chunkPos.getStartX();
        int offsetZ = chunkPos.getStartZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        NoiseGenerator noise = new NoiseGenerator(935698765);

        LabyrinthMap map = LabyrinthMap.get();
        if (map == null) {
            System.err.println("Unable to get labyrinth map");
            return chunk;
        }

        int lChuckSectionIndex = 0;
        ChunkSection chunkSection = chunk.getSection(0);
        for (int worldBlockY = 0; worldBlockY < 128; worldBlockY++) {

            LabyrinthSection section = map.getSection(chunkPos, worldBlockY);
            LabyrinthMaterialSet setN = map.getSection(new ChunkPos(chunkPos.x, chunkPos.z - 1), worldBlockY).set;
            LabyrinthMaterialSet setE = map.getSection(new ChunkPos(chunkPos.x + 1, chunkPos.z), worldBlockY).set;
            LabyrinthMaterialSet setS = map.getSection(new ChunkPos(chunkPos.x, chunkPos.z + 1), worldBlockY).set;
            LabyrinthMaterialSet setW = map.getSection(new ChunkPos(chunkPos.x - 1, chunkPos.z), worldBlockY).set;

            int chunkSectionIndex = chunk.getSectionIndex(worldBlockY);
            if (lChuckSectionIndex != chunkSectionIndex) {
                lChuckSectionIndex = chunkSectionIndex;
                chunkSection = chunk.getSection(chunkSectionIndex);
            }
            for (int chunkBlockZ = 0; chunkBlockZ < 16; chunkBlockZ++) {
                int worldBlockZ = offsetZ + chunkBlockZ;
                for (int chunkBlockX = 0; chunkBlockX < 16; chunkBlockX++) {
                    int worldBlockX = offsetX + chunkBlockX;
                    BlockState blockState = AIR;

                    blockState = section.sample(chunkBlockX, worldBlockY % 8, chunkBlockZ, setN, setE, setS, setW,
                            noise);

                    if (blockState != AIR && !SharedConstants.isOutsideGenerationArea(chunk.getPos())) {
                        chunkSection.setBlockState(chunkBlockX, worldBlockY & 15, chunkBlockZ, blockState,
                                false);
                        // if (aquiferSampler.needsFluidTick() && !blockState.getFluidState().isEmpty())
                        // {
                        if (!blockState.getFluidState().isEmpty()) {
                            mutable.set(worldBlockX, worldBlockY, worldBlockZ);
                            chunk.markBlockForPostProcessing(mutable);
                        }
                    }
                }
            }
        }
        return chunk;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int var1, int var2, Type var3, HeightLimitView var4, NoiseConfig var5) {
        return 128;
    }

    @Override
    public VerticalBlockSample getColumnSample(int var1, int var2, HeightLimitView var3, NoiseConfig var4) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColumnSample'");
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {

    }

}
