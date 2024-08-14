package com.peter.peterspjo.worldgen;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.util.NoiseGenerator;

import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
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

    public static final MapCodec<UnderworldChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
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
        Registry.register(Registries.CHUNK_GENERATOR, PJO.id("underworld"), CODEC);
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
        return (x, y, z) -> {
            return y < Math.min(-54, i) ? fluidLevel : fluidLevel2;
        };
    }

    public RegistryEntry<ChunkGeneratorSettings> getSettings() {
        return this.settings;
    }

    @Override
    protected MapCodec<UnderworldChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion var1, long var2, NoiseConfig var4, BiomeAccess var5, StructureAccessor var6,
            Chunk var7, Carver var8) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {

    }

    @Override
    public void populateEntities(ChunkRegion var1) {

    }

    @Override
    public int getWorldHeight() {
        return 512;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Blender blender, NoiseConfig noiseConfig,
            StructureAccessor structureAccessor, Chunk chunk) {
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

            return CompletableFuture.supplyAsync(Util.debugSupplier("wgen_fill_underworld", () -> {
                return this.populateNoise(blender, structureAccessor, noiseConfig, chunk, j, k);
            }), Util.getMainWorkerExecutor()).whenCompleteAsync((chunkx, throwable) -> {
                Iterator<ChunkSection> var3 = set.iterator();

                while (var3.hasNext()) {
                    ChunkSection chunkSection = var3.next();
                    chunkSection.unlock();
                }

            });
        }
    }

    private static final Block FLOOR_BASE = Blocks.BLACKSTONE;
    private static final Block FLOOR_TOP_INNER = Blocks.SAND;
    private static final Block FLOOR_TOP_OUTER = PJOBlocks.UNDERWORLD_SAND_DARK;
    private static final Block FLOOR_ASPHODEL = Blocks.GRASS_BLOCK;
    private static final Block ROOF = Blocks.BASALT;
    private static final Block EREBOS_WALL_BLOCK = Blocks.POLISHED_BLACKSTONE_BRICKS;
    private static final Block STYX_RIVER = PJOBlocks.STYX_WATER;

    public static final int PIT_ENTRANCE_X = -512;
    public static final int PIT_ENTRANCE_Z = -512;
    public static final int PIT_INNER_SIZE = 48;
    public static final int PIT_OUTER_SIZE = 64 + 32;
    public static final double PIT_LERP_HEIGHT = 64;
    public static final double PIT_MIN_SAND_HEIGHT = -24;
    public static final int PALACE_PAD_SIZE = 48;
    public static final int PALACE_PIT_SIZE = 64 + 32;
    public static final int EREBOS_SIZE = 1024;
    public static final int EREBOS_WALL_HEIGHT = 16;
    public static final double EREBOS_WALL_PILLAR_ANGLE = Math.PI / 500d;
    public static final int CELLING_HEIGHT = 128;
    public static final double OUTER_STEP_SIZE = 32;
    public static final int OUTER_STEP_OFFSET = 64;

    public static final int ASPHODEL_INNER_OFFSET = 32;
    public static final int ASPHODEL_WIDTH = 128;

    private Chunk populateNoise(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig,
            Chunk chunk, int minimumCellY, int cellHeight) {
        ChunkNoiseSampler chunkNoiseSampler = chunk.getOrCreateChunkNoiseSampler((chunkX) -> {
            return this.createChunkNoiseSampler(chunkX, structureAccessor, blender, noiseConfig);
        });
        Heightmap oceanFloorHeightmap = chunk.getHeightmap(Type.OCEAN_FLOOR_WG);
        Heightmap worldSurfaceHeightMap = chunk.getHeightmap(Type.WORLD_SURFACE_WG);
        ChunkPos chunkPos = chunk.getPos();
        int offsetX = chunkPos.getStartX();
        int offsetZ = chunkPos.getStartZ();
        chunkNoiseSampler.sampleStartDensity();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int hCellBlockCount = chunkNoiseSampler.getHorizontalCellBlockCount();
        int vCellBlockCount = chunkNoiseSampler.getVerticalCellBlockCount();
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
                                double flatDirFromOrigin = Math.abs(Math.atan2(worldBlockZ, worldBlockX));
                                double flatDistFromPit = Math
                                        .sqrt(Math.pow(worldBlockX - PIT_ENTRANCE_X, 2)
                                                + Math.pow(worldBlockZ - PIT_ENTRANCE_Z, 2));
                                int terrainHeight = getTerrainHeightAtLocation(worldBlockX, worldBlockZ, noise,
                                        flatDistFromOrigin);

                                if (worldBlockY > CELLING_HEIGHT) {
                                    int stepHeight = getOuterStepHeight(flatDistFromOrigin);
                                    int rDepth = stepHeight - terrainHeight;
                                    int rRoofHeight = stepHeight + rDepth + 4;
                                    if (!(worldBlockZ < 0 && worldBlockY < rRoofHeight && worldBlockX > -9
                                            && worldBlockX < 9)) {
                                        blockState = ROOF.getDefaultState();
                                    } else if (worldBlockZ < 0 && worldBlockX > -9 && worldBlockX < 9) {
                                        if (worldBlockY < terrainHeight) {
                                            blockState = ROOF.getDefaultState();
                                        } else if (worldBlockY == terrainHeight) {
                                            blockState = FLOOR_TOP_OUTER.getDefaultState();
                                        } else if (worldBlockY < stepHeight) {
                                            blockState = STYX_RIVER.getDefaultState();
                                        }
                                    }
                                } else if (worldBlockY > CELLING_HEIGHT - 24) {
                                    int stepHeight = getOuterStepHeight(flatDistFromOrigin);
                                    int fHeight = stepHeight;
                                    int rDepth = stepHeight - terrainHeight;
                                    int rRoofHeight = stepHeight + rDepth + 4;
                                    if (worldBlockZ < 0) {
                                        fHeight = terrainHeight;
                                    }
                                    double celHeight = CELLING_HEIGHT
                                            - (noise.noise(worldBlockX * 2, worldBlockZ * 2) * 16) - 8;
                                    if (worldBlockY < fHeight) {
                                        blockState = FLOOR_BASE.getDefaultState();
                                    } else if (worldBlockY == fHeight) {
                                        blockState = FLOOR_TOP_OUTER.getDefaultState();
                                    } else if (worldBlockZ < 0 && worldBlockY < stepHeight) {
                                        blockState = STYX_RIVER.getDefaultState();
                                    } else if (worldBlockZ < 0 && worldBlockY < rRoofHeight && worldBlockX > -9
                                            && worldBlockX < 9) {
                                        // air
                                    } else if (worldBlockY > celHeight) {
                                        blockState = ROOF.getDefaultState();
                                    }
                                } else if (flatDistFromPit < PIT_OUTER_SIZE + 32) {
                                    if (flatDistFromPit < PIT_INNER_SIZE) {
                                        blockState = AIR;
                                    } else {
                                        double alpha = PIT_LERP_HEIGHT / Math.sqrt(PIT_OUTER_SIZE - PIT_INNER_SIZE);
                                        double pitH = alpha * Math.sqrt(flatDistFromPit - PIT_INNER_SIZE)
                                                - PIT_LERP_HEIGHT;
                                        pitH = (int) Math.min(pitH, terrainHeight);
                                        if (worldBlockY == pitH) {
                                            if (pitH < PIT_MIN_SAND_HEIGHT) {
                                                blockState = FLOOR_BASE.getDefaultState();
                                            } else {
                                                blockState = FLOOR_TOP_INNER.getDefaultState();
                                            }
                                        } else if (worldBlockY < pitH) {
                                            blockState = FLOOR_BASE.getDefaultState();
                                        }
                                    }
                                } else if (worldBlockY < terrainHeight) {
                                    blockState = FLOOR_BASE.getDefaultState();
                                } else if (flatDistFromOrigin >= EREBOS_SIZE && flatDistFromOrigin < EREBOS_SIZE + 5) {
                                    if (worldBlockY <= EREBOS_WALL_HEIGHT && flatDistFromOrigin < EREBOS_SIZE + 4) {
                                        blockState = EREBOS_WALL_BLOCK.getDefaultState();
                                    } else if ((flatDirFromOrigin % EREBOS_WALL_PILLAR_ANGLE)
                                            / EREBOS_WALL_PILLAR_ANGLE < 0.25d && worldBlockY <= EREBOS_WALL_HEIGHT + 2
                                            && flatDistFromOrigin >= EREBOS_SIZE + 3) {
                                        blockState = EREBOS_WALL_BLOCK.getDefaultState();
                                        // } else if (worldBlockY <= (flatDirFromOrigin %
                                        // EREBOS_WALL_PILLAR_ANGLE)/EREBOS_WALL_PILLAR_ANGLE * 100 + 16) {
                                        // blockState = EREBOS_WALL_BLOCK.getDefaultState();
                                    } else if (worldBlockY == terrainHeight) {
                                        blockState = FLOOR_TOP_OUTER.getDefaultState();
                                    }
                                } else if (worldBlockY == terrainHeight) {
                                    if (flatDistFromOrigin > EREBOS_SIZE) {
                                        blockState = FLOOR_TOP_OUTER.getDefaultState();
                                    } else if (flatDistFromOrigin < EREBOS_SIZE - ASPHODEL_INNER_OFFSET
                                            && flatDistFromOrigin > EREBOS_SIZE - ASPHODEL_INNER_OFFSET
                                                    - ASPHODEL_WIDTH) {
                                        blockState = FLOOR_ASPHODEL.getDefaultState();
                                    } else if (flatDistFromOrigin > PALACE_PIT_SIZE + 4) {
                                        blockState = FLOOR_TOP_INNER.getDefaultState();
                                    } else {
                                        blockState = FLOOR_BASE.getDefaultState();
                                    }
                                } else if (flatDistFromOrigin > EREBOS_SIZE) {
                                    if (flatDistFromOrigin < EREBOS_SIZE + OUTER_STEP_OFFSET) {
                                        if (worldBlockY < 0) {
                                            blockState = STYX_RIVER.getDefaultState();
                                        }
                                    } else {
                                        int surHeight = getOuterStepHeight(flatDistFromOrigin);
                                        if ((worldBlockZ < 0 && worldBlockY < surHeight) || worldBlockY < -surHeight) {
                                            blockState = STYX_RIVER.getDefaultState();
                                        } else if (worldBlockZ > 0 && terrainHeight < 0) {
                                            int rPlane = -getOuterStepHeight(flatDistFromOrigin);
                                            int rDepth = rPlane - terrainHeight;
                                            int rRoofHeight = rPlane + rDepth + 4;
                                            if (worldBlockY < rRoofHeight) {
                                                // air
                                            } else if (worldBlockY == rRoofHeight && worldBlockY <= surHeight) {
                                                blockState = FLOOR_BASE.getDefaultState();
                                            } else if (worldBlockY >= rRoofHeight && worldBlockY < surHeight) {
                                                blockState = FLOOR_BASE.getDefaultState();
                                            } else if (worldBlockY == surHeight) {
                                                blockState = FLOOR_TOP_OUTER.getDefaultState();
                                            }
                                        }
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
                }
            }

            chunkNoiseSampler.swapBuffers();
        }

        chunkNoiseSampler.stopInterpolation();
        return chunk;
    }

    private int getOuterStepHeight(double flatDistFromOrigin) {
        return (int) ((flatDistFromOrigin - EREBOS_SIZE - OUTER_STEP_OFFSET) / OUTER_STEP_SIZE);
    }

    private int getTerrainHeightAtLocation(int x, int z, NoiseGenerator noise, double flatDistFromOrigin) {
        if (flatDistFromOrigin < PALACE_PIT_SIZE) {
            double maxY = Math.max((-PALACE_PIT_SIZE + flatDistFromOrigin) / 2d, -8);
            maxY = Math.max(maxY, -2 * (flatDistFromOrigin - (PALACE_PAD_SIZE + 2)));
            return (int) Math.min(maxY, 2);
        } else if (flatDistFromOrigin < PALACE_PIT_SIZE + 8) {
            return 0;
        } else if (flatDistFromOrigin < EREBOS_SIZE) {
            double lerpVal = Math.min(1d, (flatDistFromOrigin - (PALACE_PIT_SIZE + 8d)) / 16d);
            lerpVal = Math.min(lerpVal, (EREBOS_SIZE - flatDistFromOrigin) / 16d);
            double h = (noise.noise(x / 2d, z / 2d) * 8) + 1;
            return (int) (h * lerpVal);
        } else if (flatDistFromOrigin > EREBOS_SIZE + 24 && flatDistFromOrigin < EREBOS_SIZE + OUTER_STEP_OFFSET) {
            double rpos = flatDistFromOrigin - EREBOS_SIZE - 24 - 7;
            double h = Math.max(Math.abs(rpos / 2d) - 5, -4);
            if (x > -32 && x < 32 && flatDistFromOrigin > EREBOS_SIZE + 24 + 6) {
                double w = Math.max(5, 12 - (rpos / 4d));
                double r = Math.max(Math.abs(x / 2d) - w, -4);
                r = Math.min(r, 0);
                h = Math.min(h, r);
            }
            return (int) Math.min(h, -1);
        } else {
            int h = getOuterStepHeight(flatDistFromOrigin);
            if (x > -9 && x < 9 && flatDistFromOrigin > EREBOS_SIZE + 24) {
                double r = Math.max(Math.abs(x / 2d) - 5, -4);
                r = Math.min(r, 0);
                if (z > 0) {
                    h = -h - 1;
                }
                h += r;
            }
            return h;
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
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
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
