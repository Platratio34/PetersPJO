package com.peter.peterspjo.worldgen.labyrinth;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.worldgen.PJODimensions;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;
import com.peter.peterspjo.worldgen.labyrinth.sections.LabyrinthSection;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class LabyrinthMap extends PersistentState {

    private static final String NAME = "labyrinth_map";

    private static final Type<LabyrinthMap> TYPE = new Type<LabyrinthMap>(LabyrinthMap::new,
            LabyrinthMap::createFromNbt, null);

    private static LabyrinthMap map;

    private MapLayer[] layers = new MapLayer[16];

    private Lock mapLocker = new ReentrantLock();

    private LabyrinthMap() {
        for (int i = 0; i < 16; i++) {
            layers[i] = new MapLayer();
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        NbtCompound layersNbt = new NbtCompound();
        nbt.put("layers", layersNbt);
        for (int i = 0; i < layers.length; i++) {
            layersNbt.put(i+"", layers[i].toNbt());
        }
        return nbt;
    }

    private static LabyrinthMap createFromNbt(NbtCompound tag, WrapperLookup registryLookup) {
        LabyrinthMap state = new LabyrinthMap();
        for (int i = 0; i < state.layers.length; i++) {
            state.layers[i].fromNbt(tag.getCompound(i+""));
        }
        return state;
    }

    public static LabyrinthMap getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(PJODimensions.LABYRINTH).getPersistentStateManager();
        LabyrinthMap state = persistentStateManager.getOrCreate(TYPE, NAME);
        map = state;
        state.markDirty();
        return state;
    }

    /**
     * Get the current labyrinth map
     * 
     * @return Labyrinth map
     */
    public static LabyrinthMap get() {
        return map;
    }

    /**
     * Get section of labyrinth, generates a new one if not present
     * 
     * @param chunkPos X,Z location of chunk
     * @param y        Y location of chunk
     * @return Section at location
     */
    public LabyrinthSection getSection(ChunkPos chunkPos, int y) {
        int yIndex = y / 8;
        MapLayer layer = layers[yIndex];
        LabyrinthSection section = null;
        mapLocker.lock();
        try {
            if (layer.has(chunkPos)) {
                section = layer.get(chunkPos);
            }
            if (section == null) {
                section = generate(chunkPos, yIndex);
            }
        } catch (Exception e) {
            PJO.LOGGER.error("Could not get section for " + chunkPos.toString() + " at y=" + y);
            PJO.LOGGER.error(e.toString());
        } finally {
            mapLocker.unlock();
        }
        return section;
    }

    private LabyrinthSection generate(ChunkPos chunkPos, int yIndex) {
        if (chunkPos.x == 0 && chunkPos.z == 0) { // if this is the origin chunk, always make it cross room
            LabyrinthSection section = LabyrinthSection.CROSS_ROOM.gen(Direction.NORTH, LabyrinthMaterials.DEFAULT);
            layers[yIndex].set(chunkPos, section);
            markDirty();
            // PJO.LOGGER.info("Generated section default for labyrinth @ " + chunkPos.toString() + ", y index " + yIndex);
            return section;
        }
        // check if pre-req chunks have already been generated
        ChunkPos xP = new ChunkPos(chunkPos.x + 1, chunkPos.z);
        ChunkPos xN = new ChunkPos(chunkPos.x - 1, chunkPos.z);
        ChunkPos zP = new ChunkPos(chunkPos.x, chunkPos.z + 1);
        ChunkPos zN = new ChunkPos(chunkPos.x, chunkPos.z - 1);
        if (yIndex > 0) {
            if (!layers[yIndex - 1].has(chunkPos)) {
                generate(chunkPos, yIndex - 1);
            }
        }
        if (chunkPos.z > 0) {
            if (!layers[yIndex].has(zN)) {
                generate(zN, yIndex);
            }
        } else if (chunkPos.z < 0) {
            if (!layers[yIndex].has(zP)) {
                generate(zP, yIndex);
            }
        }
        if (chunkPos.x > 0) {
            if (!layers[yIndex].has(xN)) {
                generate(xN, yIndex);
            }
        } else if (chunkPos.x < 0) {
            if (!layers[yIndex].has(xP)) {
                generate(xP, yIndex);
            }
        }

        // LabyrinthSection section =
        // LabyrinthSection.STRAIGHT_ROOM.apply(Direction.NORTH);
        Random random = Random.create(chunkPos.toLong() + (yIndex * 2));
        int dirIndex = random.nextBetween(0, 3);
        int startDir = dirIndex;

        String matSetId = LabyrinthMaterials.WEIGHTED_MATERIAL_ID[random.nextBetween(0,
                LabyrinthMaterials.WEIGHTED_MATERIAL_ID.length - 1)];
        LabyrinthMaterialSet materialSet = LabyrinthMaterials.MATERIALS_BY_ID.get(matSetId);

        int sectionTypeIndex = random.nextBetween(0, LabyrinthSection.SECTIONS.length - 1);
        int startSectionType = sectionTypeIndex;

        int sectionVariantIndex = random.nextBetween(0, 16);

        LabyrinthSection section = LabyrinthSection.SECTIONS[sectionTypeIndex].gen(Direction.byId(dirIndex+2),
                materialSet, sectionVariantIndex);
        
        boolean valid = false;
        int iterationCounter = 0;
        final int maxIterations = (4 * LabyrinthSection.SECTIONS.length) + 2;
        while (!valid) {
            iterationCounter++;
            if (chunkPos.z > 0) {
                if (layers[yIndex].get(zN).canConnectCorridor(section, Direction.SOUTH)) {
                    valid = true;
                }
            } else if (chunkPos.z < 0) {
                if (layers[yIndex].get(zP).canConnectCorridor(section, Direction.NORTH)) {
                    valid = true;
                }
            }
            if (chunkPos.x > 0) {
                if (layers[yIndex].get(xN).canConnectCorridor(section, Direction.EAST)) {
                    valid = true;
                }
            } else if (chunkPos.x < 0) {
                if (layers[yIndex].get(xP).canConnectCorridor(section, Direction.WEST)) {
                    valid = true;
                }
            }
            if (yIndex > 0) {
                if (layers[yIndex - 1].get(chunkPos).canConnectCorridor(section, Direction.UP)) {
                    valid = true;
                } else if (!layers[yIndex - 1].get(chunkPos).canConnect(section, Direction.UP)) {
                    valid = false;
                }
            }
            if (valid && !section.canPlace(chunkPos, yIndex, this)) {
                valid = false;
            }
            if (!valid) {
                dirIndex++;
                if (dirIndex > 3) {
                    dirIndex = 0;
                }
                if (dirIndex == startDir) {
                    sectionTypeIndex++;
                    if (sectionTypeIndex >= LabyrinthSection.SECTIONS.length) {
                        sectionTypeIndex = 0;
                    }
                    if (sectionTypeIndex == startSectionType) {
                        // PJO.LOGGER.warn("Unable to find labyrinth section to fit " + chunkPos.toString()
                        //         + " at y index " + yIndex);
                        // section = LabyrinthSection.EMPTY.gen(Direction.byId(dirIndex+2), materialSet, sectionVariantIndex);
                        section = LabyrinthSection.SECTIONS[sectionTypeIndex].gen(Direction.byId(dirIndex+2), materialSet, sectionVariantIndex);
                        break;
                    }
                }
                section = LabyrinthSection.SECTIONS[sectionTypeIndex].gen(Direction.byId(dirIndex+2), materialSet, sectionVariantIndex);
            }
            if (iterationCounter > maxIterations) {
                PJO.LOGGER.error(
                        "Took to long to generating labyrinth section @ " + chunkPos.toString() + ", y index " + yIndex);
                break;
            }
        }
        // if (iterationCounter > 0) {
            // PJO.LOGGER.info("Generated section for labyrinth @ " + chunkPos.toString() + ", y index " + yIndex);
        // }
        // section.set = LabyrinthMaterials.DEFAULT;
        layers[yIndex].set(chunkPos, section);
        markDirty();
        return section;
    }

    private static class MapLayer {

        private HashMap<ChunkPos, LabyrinthSection> layer = new HashMap<ChunkPos, LabyrinthSection>();

        public boolean has(ChunkPos pos) {
            return layer.containsKey(pos) && layer.get(pos) != null;
        }

        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();
            for (ChunkPos pos : layer.keySet()) {
                nbt.put(pos.toLong()+"", layer.get(pos).toNbt());
            }
            return nbt;
        }

        public void fromNbt(NbtCompound nbt) {
            for (String key : nbt.getKeys()) {
                long posLong = Long.parseLong(key);
                ChunkPos pos = new ChunkPos(posLong);
                layer.put(pos, LabyrinthSection.sectionFromNbt(nbt.getCompound(key)));
            }
        }

        public LabyrinthSection get(ChunkPos pos) {
            if (!layer.containsKey(pos)) {
                PJO.LOGGER.error("Tried to get labyrinth section for " + pos.toString() + " but was not present");
                return null;
            }
            LabyrinthSection section = layer.get(pos);
            if (section == null)
                PJO.LOGGER.error("Tried to get labyrinth section for " + pos.toString() + " but was null");
            return section;
        }

        public LabyrinthSection set(ChunkPos pos, LabyrinthSection section) {
            return layer.put(pos, section);
        }
    }

    /**
     * Translate a position from overworld (or other dimensions) to a location in
     * the labyrinth
     * 
     * @param pos position in overworld (or other dimension)
     * @return Position in labyrinth
     */
    public static Mutable translatePositionTo(BlockPos pos) {
        return new Mutable(pos.getX() / 16, pos.getY(), pos.getZ() / 16);
    }

    /**
     * Translate a position from labyrinth to a location in the overworld (or other
     * dimensions)
     * 
     * @param pos position in labyrinth
     * @return Position in overworld (or other dimension)
     */
    public static Mutable translatePositionFrom(BlockPos pos) {
        return new Mutable(pos.getX() * 16, pos.getY(), pos.getZ() * 16);
    }
}
