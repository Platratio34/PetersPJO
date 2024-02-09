package com.peter.peterspjo.worldgen.labyrinth;

import java.util.HashMap;

import org.apache.http.client.utils.Idn;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.worldgen.PJODimensions;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class DoorManager extends PersistentState {

    private static String NAME = "labyrinth_door_manager";

    private static DoorManager manager;

    protected HashMap<RegistryKey<World>, HashMap<BlockPos, DoorData>> doors = new HashMap<RegistryKey<World>, HashMap<BlockPos, DoorData>>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        for (RegistryKey<World> worldKey : doors.keySet()) {
            NbtList worldNbt = new NbtList();
            nbt.put(worldKey.getValue().toString(), worldNbt);
            HashMap<BlockPos, DoorData> worldMap = doors.get(worldKey);
            for (DoorData door : worldMap.values()) {
                worldNbt.add(door.writeNbt());
            }
        }
        PJO.LOGGER.info("Saving labyrinth doors");
        return nbt;
    }

    private static DoorManager createFromNbt(NbtCompound nbt) {
        PJO.LOGGER.info("Loading labyrinth doors");
        DoorManager state = new DoorManager();
        for (String worldString : nbt.getKeys()) {
            RegistryKey<World> worldKey = RegistryKey.of(RegistryKeys.WORLD, new Identifier(worldString));
            boolean isLabyrinth = worldKey.equals(PJODimensions.LABYRINTH);
            HashMap<BlockPos, DoorData> worldDoors;
            if(state.doors.containsKey(worldKey)) {
                worldDoors = state.doors.get(worldKey);
            } else {
                worldDoors = new HashMap<BlockPos, DoorData>();
                state.doors.put(worldKey, worldDoors);
            }
            NbtList worldNbt = nbt.getList(worldString, NbtList.COMPOUND_TYPE);
            for (int i = 0; i < worldNbt.size(); i++) {
                DoorData door = new DoorData(worldNbt.getCompound(i));
                if (isLabyrinth) {
                    worldDoors.put(door.labyrinthPosition, door);
                    if (door.connected) {
                        if (!state.doors.containsKey(door.targetDimension)) {
                            state.doors.put(door.targetDimension, new HashMap<BlockPos, DoorData>());
                        }
                        state.doors.get(door.targetDimension).put(door.targetPos, door);
                    }
                } else if(!worldDoors.containsKey(door.targetPos)) {
                    worldDoors.put(door.targetPos, door);
                }
            }
        }
        return state;
    }

    public static DoorManager getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        DoorManager state = persistentStateManager.getOrCreate(DoorManager::createFromNbt, DoorManager::new, NAME);
                manager = state;
        return state;
    }

    public static DoorManager get() {
        return manager;
    }

    private HashMap<BlockPos, DoorData> getDim(RegistryKey<World> dimension) {
        if (doors.containsKey(dimension)) {
            return doors.get(dimension);
        }
        HashMap<BlockPos, DoorData> map = new HashMap<BlockPos, DoorData>();
        doors.put(dimension, map);
        return map;
    }

    public DoorData getDoor(RegistryKey<World> dimensionKey, BlockPos pos) {
        HashMap<BlockPos, DoorData> map = getDim(dimensionKey);
        if (!map.containsKey(pos)) {
            return null;
        }
        return map.get(pos);
    }

    public DoorData getOrCreateDoor(RegistryKey<World> dimensionKey, BlockPos pos) {
        HashMap<BlockPos, DoorData> map = getDim(dimensionKey);
        if (!map.containsKey(pos)) {
            DoorData door = new DoorData();
            PJO.LOGGER.info("New labyrinth door created at " + pos + " in " + dimensionKey.getValue().toString());
            if (dimensionKey.equals(PJODimensions.LABYRINTH)) {
                door.labyrinthPosition = pos;
            } else {
                door.targetDimension = dimensionKey;
                door.targetPos = pos;
            }
            map.put(pos, door);
            markDirty();
            return door;
        }
        return map.get(pos);
    }
    
    private static NbtIntArray posToNbt(BlockPos pos) {
        return new NbtIntArray(new int[] { pos.getX(), pos.getY(), pos.getZ() });
    }

    private static BlockPos nbtToPos(int[] array) {
        return new BlockPos(array[0], array[1], array[2]);
    }

    public static class DoorData {

        public BlockPos labyrinthPosition;
        public RegistryKey<World> targetDimension;
        public BlockPos targetPos;
        public boolean connected = false;

        public DoorData() {

        }

        public DoorData(BlockPos labyrinthPosition) {
            this.labyrinthPosition = labyrinthPosition;
        }

        public DoorData(BlockPos targetPos, RegistryKey<World> targetDimension) {
            this.targetPos = targetPos;
            this.targetDimension = targetDimension;
        }

        protected DoorData(NbtCompound nbt) {
            this.connected = nbt.getBoolean("con");
            if (nbt.contains("lPos")) {
                labyrinthPosition = nbtToPos(nbt.getIntArray("lPos"));
            }
            if (nbt.contains("tPos")) {
                targetPos = nbtToPos(nbt.getIntArray("tPos"));
                targetDimension = RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString("tDim")));
            }
        }

        public ServerWorld getTargetWorld(World world) {
            MinecraftServer server = world.getServer();
            return server.getWorld(targetDimension);
        }

        protected NbtCompound writeNbt() {
            NbtCompound nbt = new NbtCompound();
            if(labyrinthPosition != null) nbt.put("lPos", posToNbt(labyrinthPosition));
            if(targetPos != null) nbt.putString("tDim", targetDimension.getValue().toString());
            if(targetPos != null) nbt.put("tPos", posToNbt(targetPos));
            nbt.putBoolean("con", connected);
            return nbt;
        }
    }

}
