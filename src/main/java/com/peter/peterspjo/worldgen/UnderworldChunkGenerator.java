package com.peter.peterspjo.worldgen;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.util.NoiseGenerator;

import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
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
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.StructureWeightSampler;
import net.minecraft.world.gen.GenerationStep.Carver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Type;

public final class UnderworldChunkGenerator extends ChunkGenerator {

    public static final Codec<UnderworldChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> {
            return generator.biomeSource;
        }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((generator) -> {
            return generator.settings;
        })).apply(instance, instance.stable(UnderworldChunkGenerator::new));
    });
    private final RegistryEntry<ChunkGeneratorSettings> settings;
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private final Supplier<AquiferSampler.FluidLevelSampler> fluidLevelSampler;

    public static void register() {
        Registry.register(Registries.CHUNK_GENERATOR, new Identifier(PJO.NAMESPACE, "underworld"), CODEC);
        UnderworldBiomeSource.register();
    }

    public UnderworldChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
        this.fluidLevelSampler = Suppliers.memoize(() -> {
            return createFluidLevelSampler((ChunkGeneratorSettings) settings.value());
        });
    }

    private static AquiferSampler.FluidLevelSampler createFluidLevelSampler(ChunkGeneratorSettings settings) {
        AquiferSampler.FluidLevel fluidLevel = new AquiferSampler.FluidLevel(-54, Blocks.LAVA.getDefaultState());
        int i = settings.seaLevel();
        AquiferSampler.FluidLevel fluidLevel2 = new AquiferSampler.FluidLevel(i, settings.defaultFluid());
        AquiferSampler.FluidLevel fluidLevel3 = new AquiferSampler.FluidLevel(DimensionType.MIN_HEIGHT * 2,
                Blocks.AIR.getDefaultState());
        return (x, y, z) -> {
            return y < Math.min(-54, i) ? fluidLevel : fluidLevel2;
        };
    }

    public RegistryEntry<ChunkGeneratorSettings> getSettings() {
        return this.settings;
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
    public void buildSurface(ChunkRegion chunkRegion, StructureAccessor var2, NoiseConfig noiseConfig, Chunk chunk) {

    }

    @Override
    public void populateEntities(ChunkRegion var1) {

    }

    @Override
    public int getWorldHeight() {
        return 512;
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

            return CompletableFuture.supplyAsync(Util.debugSupplier("wgen_fill_noise", () -> {
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

    private static final Block FLOOR_BASE = Blocks.BLACKSTONE;
    private static final Block FLOOR_TOP1 = Blocks.SAND;
    private static final Block FLOOR_TOP2 = Blocks.GRAVEL;
    private static final Block ROOF = Blocks.BASALT;

    private Chunk populateNoise(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig,
            Chunk chunk, int minimumCellY, int cellHeight) {
        ChunkNoiseSampler chunkNoiseSampler = chunk.getOrCreateChunkNoiseSampler((chunkx) -> {
            return this.createChunkNoiseSampler(chunkx, structureAccessor, blender, noiseConfig);
        });
        Heightmap oceanFloorHeightmap = chunk.getHeightmap(Type.OCEAN_FLOOR_WG);
        Heightmap worldSurfaceHeightMap = chunk.getHeightmap(Type.WORLD_SURFACE_WG);
        ChunkPos chunkPos = chunk.getPos();
        int offsetX = chunkPos.getStartX();
        int offsetZ = chunkPos.getStartZ();
        AquiferSampler aquiferSampler = chunkNoiseSampler.getAquiferSampler();
        chunkNoiseSampler.sampleStartDensity();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int hCellBlockCount = chunkNoiseSampler.getHorizontalCellBlockCount();
        int vCellBlockCount = chunkNoiseSampler.getVerticalCellBlockCount();
        // int hCellBlockCount = 16;
        // int vCellBlockCount = 16;
        int m = 16 / hCellBlockCount;
        int n = 16 / hCellBlockCount;

        NoiseGenerator noise = new NoiseGenerator(324684654);

        for (int cellX = 0; cellX < m; ++cellX) {
            chunkNoiseSampler.sampleEndDensity(cellX);

            for (int cellZ = 0; cellZ < n; ++cellZ) {
                int q = chunk.countVerticalSections() - 1;
                ChunkSection chunkSection = chunk.getSection(q);

                for (int cellY = 31; cellY >= -32; --cellY) {
                    chunkNoiseSampler.onSampledCellCorners(cellY, cellZ);

                    for (int s = vCellBlockCount - 1; s >= 0; --s) {
                        int worldBlockY = (minimumCellY + cellY) * vCellBlockCount + s;
                        int chunkBlockY = worldBlockY & 15;
                        int v = chunk.getSectionIndex(worldBlockY);
                        if (q != v) {
                            q = v;
                            chunkSection = chunk.getSection(v);
                        }

                        double d = (double) s / (double) vCellBlockCount;
                        chunkNoiseSampler.interpolateY(worldBlockY, d);

                        for (int w = 0; w < hCellBlockCount; ++w) {
                            int worldBlockX = offsetX + cellX * hCellBlockCount + w;
                            int chunkBlockX = worldBlockX & 15;
                            double e = (double) w / (double) hCellBlockCount;
                            chunkNoiseSampler.interpolateX(worldBlockX, e);

                            for (int z = 0; z < hCellBlockCount; ++z) {
                                int worldBlockZ = offsetZ + cellZ * hCellBlockCount + z;
                                int chunkBlockZ = worldBlockZ & 15;
                                double f = (double) z / (double) hCellBlockCount;
                                chunkNoiseSampler.interpolateZ(worldBlockZ, f);
                                // BlockState blockState = chunkNoiseSampler.sampleBlockState();
                                BlockState blockState = Blocks.AIR.getDefaultState();
                                double flatDistFromOrigin = Math
                                        .sqrt((worldBlockX * worldBlockX) + (worldBlockZ * worldBlockZ));
                                // if (blockY < 0 && flatDistFromOrigin % 16 < 1) {
                                //     blockState = Blocks.BLACKSTONE.getDefaultState();
                                // }
                                if (worldBlockY > 128) {
                                    blockState = ROOF.getDefaultState();
                                } else if (worldBlockY < getTerrainHeightAtLocation(worldBlockX, worldBlockZ, noise)) {
                                    blockState = FLOOR_BASE.getDefaultState();
                                    // if (flatDistFromOrigin < 64) {
                                    //     double maxY = Math.max(-32 + (flatDistFromOrigin / 2d), -8);
                                    //     maxY = Math.max(maxY, -2 * (flatDistFromOrigin - 32));
                                    //     if (worldBlockY > maxY) {
                                    //         blockState = AIR;
                                    //     }
                                    // } else if (flatDistFromOrigin > 68) {
                                    //     // if (worldBlockY == -1) {
                                    //     //     blockState = FLOOR_TOP1;
                                    //     // }

                                    // }
                                } else if (worldBlockY == getTerrainHeightAtLocation(worldBlockX, worldBlockZ, noise)) {
                                    if (flatDistFromOrigin > 512) {
                                        blockState = FLOOR_TOP2.getDefaultState();
                                    } else if (flatDistFromOrigin > 68) {
                                        blockState = FLOOR_TOP1.getDefaultState();
                                    } else {
                                        blockState = FLOOR_BASE.getDefaultState();
                                    }
                                }
                                if (blockState == null) {
                                    blockState = ((ChunkGeneratorSettings) this.settings.value()).defaultBlock();
                                }

                                blockState = this.getBlockState(chunkNoiseSampler, worldBlockX, worldBlockY,
                                        worldBlockZ, blockState);
                                if (blockState != AIR && !SharedConstants.isOutsideGenerationArea(chunk.getPos())) {
                                    chunkSection.setBlockState(chunkBlockX, chunkBlockY, chunkBlockZ, blockState,
                                            false);
                                    oceanFloorHeightmap.trackUpdate(chunkBlockX, worldBlockY, chunkBlockZ, blockState);
                                    worldSurfaceHeightMap.trackUpdate(chunkBlockX, worldBlockY, chunkBlockZ,
                                            blockState);
                                    if (aquiferSampler.needsFluidTick() && !blockState.getFluidState().isEmpty()) {
                                        mutable.set(worldBlockX, worldBlockY, worldBlockZ);
                                        chunk.markBlockForPostProcessing(mutable);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            chunkNoiseSampler.swapBuffers();
        }

        chunkNoiseSampler.stopInterpolation();
        return chunk;
    }
    
    private int getTerrainHeightAtLocation(int x, int z, NoiseGenerator noise) {
        double flatDistFromOrigin = Math.sqrt((x * x) + (z * z));
        if (flatDistFromOrigin < 64) {
            double maxY = Math.max(-32 + (flatDistFromOrigin / 2d), -8);
            maxY = Math.max(maxY, -2 * (flatDistFromOrigin - 34));
            return (int) Math.min(maxY,2);
        } else if(flatDistFromOrigin < 72) {
            return 0;
        } else if (flatDistFromOrigin < 512) {
            double lerpVal = Math.min(1d, (flatDistFromOrigin - 72d) / 16d);
            lerpVal = Math.min(lerpVal, (512-flatDistFromOrigin) / 16d);
            double h = (noise.noise(x/2d, z/2d) * 8) + 1;
            return (int)(h * lerpVal);
        } else {
            return (int) ((flatDistFromOrigin - 512 - 32) / 32d);
        }
        // return 0;
    }

    private ChunkNoiseSampler createChunkNoiseSampler(Chunk chunk, StructureAccessor world, Blender blender,
            NoiseConfig noiseConfig) {
        return ChunkNoiseSampler.create(chunk, noiseConfig,
                StructureWeightSampler.createStructureWeightSampler(world, chunk.getPos()),
                (ChunkGeneratorSettings) this.settings.value(),
                (AquiferSampler.FluidLevelSampler) this.fluidLevelSampler.get(), blender);
    }

    private BlockState getBlockState(ChunkNoiseSampler chunkNoiseSampler, int x, int y, int z, BlockState state) {
       return state;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return -256;
    }

    @Override
    public int getHeight(int var1, int var2, Type var3, HeightLimitView var4, NoiseConfig var5) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int var1, int var2, HeightLimitView var3, NoiseConfig var4) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColumnSample'");
    }

    @Override
    public void getDebugHudText(List<String> var1, NoiseConfig var2, BlockPos var3) {

    }

}
