package com.peter.peterspjo.worldgen.labyrinth;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.worldgen.labyrinth.sections.LabyrinthSection;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class LabyrinthMap extends PersistentState {

    private static LabyrinthMap map;

    private MapLayer[] layers = new MapLayer[16];

    private Lock locker = new ReentrantLock();

    private LabyrinthMap() {
        for (int i = 0; i < 16; i++) {
            layers[i] = new MapLayer();
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {

        return nbt;
    }

    private static LabyrinthMap createFromNbt(NbtCompound tag) {
        LabyrinthMap state = new LabyrinthMap();

        return state;
    }

    // private static Type<LabyrinthMap> type = new Type<>(LabyrinthMap::new,
    // LabyrinthMap::createFromNbt, null);

    public static LabyrinthMap getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        LabyrinthMap state = persistentStateManager.getOrCreate(LabyrinthMap::createFromNbt, LabyrinthMap::new,
                PJO.NAMESPACE);
        map = state;
        return state;
    }

    public static LabyrinthMap get() {
        return map;
    }

    public LabyrinthSection getSection(ChunkPos chunkPos, int y) {
        int yIndex = y / 8;
        MapLayer layer = layers[yIndex];
        LabyrinthSection section = null;
        locker.lock();
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
            locker.unlock();
        }
        return section;
    }

    private LabyrinthSection generate(ChunkPos chunkPos, int yIndex) {
        // LabyrinthSection section = LabyrinthSection.STRAIGHT_ROOM.apply(Direction.NORTH);
        Random random = Random.create(chunkPos.toLong() + (yIndex * 2));
        Direction orientation = Direction.NORTH;
        int dirIndex = random.nextBetween(0, 3);
        switch (dirIndex) {
            case 0:
                orientation = Direction.NORTH;
                break;
            case 1:
                orientation = Direction.EAST;
                break;
            case 2:
                orientation = Direction.SOUTH;
                break;
            case 3:
                orientation = Direction.WEST;
                break;
        
            default:
                break;
        }
        LabyrinthSection section = LabyrinthSection.SECTIONS[random.nextBetween(0,
                LabyrinthSection.SECTIONS.length - 1)].gen(orientation);
        
        int matSetIndex = LabyrinthMaterials.WEIGHTED_MATERIAL_INDEXES[random.nextBetween(0,
                LabyrinthMaterials.WEIGHTED_MATERIAL_INDEXES.length - 1)];
        section.set = LabyrinthMaterials.MATERIALS[matSetIndex];
        // section.set = LabyrinthMaterials.DEFAULT;
        layers[yIndex].set(chunkPos, section);
        return section;
    }
    
    private static class MapLayer {

        private HashMap<ChunkPos, LabyrinthSection> layer = new HashMap<ChunkPos, LabyrinthSection>();

        public boolean has(ChunkPos pos) {
            return layer.containsKey(pos) && layer.get(pos) != null;
        }

        public LabyrinthSection get(ChunkPos pos) {
            LabyrinthSection section = layer.get(pos);
            if (section == null)
                PJO.LOGGER.error("Tried to get labyrinth section for "+pos.toString()+" but was null");
            return section;
        }

        public LabyrinthSection set(ChunkPos pos, LabyrinthSection section) {
            return layer.put(pos, section);
        }
    }

}
