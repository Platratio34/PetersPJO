package com.peter.peterspjo.worldgen.labyrinth;

import java.util.HashMap;
import java.util.Set;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.worldgen.PJODimensions;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
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
            if (state.doors.containsKey(worldKey)) {
                worldDoors = state.doors.get(worldKey);
            } else {
                worldDoors = new HashMap<BlockPos, DoorData>();
                state.doors.put(worldKey, worldDoors);
            }
            NbtList worldNbt = nbt.getList(worldString, NbtList.COMPOUND_TYPE);
            for (int i = 0; i < worldNbt.size(); i++) {
                DoorData door = new DoorData(worldNbt.getCompound(i));
                if (worldDoors.containsKey(door.targetPos)) { // this door was already added from a connection
                    continue;
                } else if (isLabyrinth) {
                    worldDoors.put(door.labyrinthPosition, door);
                    if (door.connected && !door.oneWayOut) {
                        state.getDim(door.targetDimension).put(door.targetPos, door);
                    }
                } else {
                    if (door.connected && !door.oneWayIn) {
                        if (!state.getDim(PJODimensions.LABYRINTH).containsKey(door.labyrinthPosition)) {
                            worldDoors.put(door.targetPos, door);
                            state.doors.get(PJODimensions.LABYRINTH).put(door.labyrinthPosition, door);
                        }
                    } else {
                        worldDoors.put(door.targetPos, door);
                    }
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

    /**
     * Get the current door manager
     * 
     * @return door manager
     */
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

    /**
     * Get the door at provided location in world
     * 
     * @param worldKey world door is in
     * @param pos      position of bottom of door
     * @return Door or null if not present
     */
    public DoorData getDoor(RegistryKey<World> worldKey, BlockPos pos) {
        HashMap<BlockPos, DoorData> map = getDim(worldKey);
        if (!map.containsKey(pos)) {
            return null;
        }
        return map.get(pos);
    }

    protected void putDoor(RegistryKey<World> worldKey, BlockPos pos, DoorData door) {
        getDim(worldKey).put(pos, door);
        markDirty();
    }

    /**
     * Get the door at provided location in world OR create one if none exist
     * 
     * @param worldKey world door is in
     * @param pos      position of bottom of door
     * @return Door or null if not present
     */
    public DoorData getOrCreateDoor(RegistryKey<World> worldKey, BlockPos pos) {
        HashMap<BlockPos, DoorData> map = getDim(worldKey);
        if (!map.containsKey(pos)) {
            DoorData door = new DoorData();
            PJO.LOGGER.info(
                    "New labyrinth door created at " + pos.toString() + " in " + worldKey.getValue().toString());
            if (worldKey.equals(PJODimensions.LABYRINTH)) {
                door.labyrinthPosition = pos;
            } else {
                door.targetDimension = worldKey;
                door.targetPos = pos;
            }
            map.put(pos, door);
            markDirty();
            return door;
        }
        return map.get(pos);
    }

    /**
     * Check if the manager has a door at location in world
     * 
     * @param worldKey world to check
     * @param pos      location to check
     * @return If a door is known at location
     */
    public boolean hasDoor(RegistryKey<World> worldKey, BlockPos pos) {
        return getDim(worldKey).containsKey(pos);
    }

    private static NbtIntArray posToNbt(BlockPos pos) {
        return new NbtIntArray(new int[] { pos.getX(), pos.getY(), pos.getZ() });
    }

    private static BlockPos nbtToPos(int[] array) {
        return new BlockPos(array[0], array[1], array[2]);
    }

    public static class DoorData {

        protected BlockPos labyrinthPosition;
        protected RegistryKey<World> targetDimension;
        protected BlockPos targetPos;
        protected boolean connected = false;
        protected boolean oneWayIn = false;
        protected boolean oneWayOut = false;

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

        protected NbtCompound writeNbt() {
            NbtCompound nbt = new NbtCompound();
            if (labyrinthPosition != null)
                nbt.put("lPos", posToNbt(labyrinthPosition));
            if (targetPos != null)
                nbt.putString("tDim", targetDimension.getValue().toString());
            if (targetPos != null)
                nbt.put("tPos", posToNbt(targetPos));
            nbt.putBoolean("con", connected);
            return nbt;
        }

        public ServerWorld getTargetWorld(World world) {
            MinecraftServer server = world.getServer();
            return server.getWorld(targetDimension);
        }

        /**
         * Attempt to teleport an entity thought this door
         * 
         * @param world  server world the entity is currently in
         * @param entity entity to teleport
         * @return If the entity was teleported
         */
        public boolean tryTeleport(World world, Entity entity) {
            if (!connected) {
                return false;
            }
            RegistryKey<World> tWorldKey;
            BlockPos tPos;
            boolean isInLabyrinth = world.getRegistryKey().equals(PJODimensions.LABYRINTH);
            if (isInLabyrinth) {
                tWorldKey = targetDimension;
                tPos = targetPos;
            } else {
                tWorldKey = PJODimensions.LABYRINTH;
                tPos = labyrinthPosition;
            }
            ServerWorld tWorld = world.getServer().getWorld(tWorldKey);
            if (!tWorld.getBlockState(tPos).getBlock().equals(PJOBlocks.LABYRINTH_DOOR)) {
                connected = false;
                if (isInLabyrinth) {
                    targetDimension = null;
                    targetPos = null;
                } else {
                    labyrinthPosition = null;
                }
                manager.getDim(tWorldKey).remove(tPos);
                manager.markDirty();
                PJO.LOGGER.info("Link broken to " + tPos.toString() + " in " + tWorldKey.getValue().toString());
                return false;
            }

            entity.teleport(tWorld, tPos.getX() + 0.5d, tPos.getY() + 0.5d, tPos.getZ() + 0.5d, Set.of(),
                    entity.getYaw(), entity.getPitch());

            return true;
        }

        private void connect(RegistryKey<World> targetDimension, BlockPos targetPosition) {
            BlockPos pos = targetPosition;
            boolean targetIsInLabyrinth = targetDimension.equals(PJODimensions.LABYRINTH);
            if (!targetIsInLabyrinth) {
                this.targetPos = pos;
                this.targetDimension = targetDimension;
            } else {
                this.labyrinthPosition = pos;
            }
            this.connected = true;
            if (manager.hasDoor(targetDimension, pos)) {
                DoorData other = manager.getDoor(targetDimension, pos);
                if (other.connected) {
                    this.oneWayOut = targetIsInLabyrinth;
                    this.oneWayIn = !targetIsInLabyrinth;
                } else {
                    manager.putDoor(targetDimension, pos, this);
                }
            } else {
                manager.putDoor(targetDimension, pos, this);
            }
            manager.markDirty();
        }

        /**
         * Try to connect this door. If in labyrinth, searches overworld, otherwise
         * searches labyrinth
         * 
         * @param server minecraft server door exists on
         * @return If a connection able to be established
         */
        public boolean tryConnect(MinecraftServer server) {
            if (labyrinthPosition != null) { // this door is in the labyrinth
                Mutable posInOverworld = LabyrinthMap.translatePositionFrom(labyrinthPosition);
                PJO.LOGGER.info(posInOverworld.toString());
                ServerWorld world = server.getWorld(World.OVERWORLD);

                if (manager.hasDoor(World.OVERWORLD, posInOverworld.toImmutable())) {
                    connect(World.OVERWORLD, posInOverworld);
                    return true;
                } else if (world.getBlockState(posInOverworld).getBlock().equals(PJOBlocks.LABYRINTH_DOOR)) {
                    if (world.getBlockState(posInOverworld).get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
                        posInOverworld.setY(posInOverworld.getY() - 1);
                    }
                    connect(World.OVERWORLD, posInOverworld);
                    return true;
                }

                for (int i = -63; i < 320; i += 1) { // lets try up and down from here
                    BlockPos pos = new BlockPos(posInOverworld.getX(), i, posInOverworld.getZ());
                    if (world.getBlockState(pos).getBlock().equals(PJOBlocks.LABYRINTH_DOOR)) {
                        connect(World.OVERWORLD, pos);
                        return true;
                    }
                }

            } else { // we need to connect it to the labyrinth
                Mutable posInLabyrinth = LabyrinthMap.translatePositionTo(targetPos);
                PJO.LOGGER.info(posInLabyrinth.toString());
                ServerWorld world = server.getWorld(PJODimensions.LABYRINTH);

                // Gets a y that will be the block above the floor of a layer of the labyrinth
                int y = posInLabyrinth.getY() - 2; // that block is a multiple of 8, plus 2
                if (y < 0) { // clamp it to reasonable range;
                    y = 0;
                } else if (posInLabyrinth.getY() > 120) {
                    y = 120;
                }
                y -= y % 8; // bring it down to a multiple of 8
                y += 2; // undo offset
                posInLabyrinth.setY(y);

                if (manager.hasDoor(PJODimensions.LABYRINTH, posInLabyrinth.toImmutable())) {
                    connect(PJODimensions.LABYRINTH, posInLabyrinth);
                    return true;
                } else if (world.getBlockState(posInLabyrinth).getBlock().equals(PJOBlocks.LABYRINTH_DOOR)) {
                    if (world.getBlockState(posInLabyrinth).get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
                        posInLabyrinth.setY(posInLabyrinth.getY() - 1);
                    }
                    connect(PJODimensions.LABYRINTH, posInLabyrinth);
                    return true;
                }

                for (int i = 2; i < 128; i += 8) { // lets try up and down from here
                    BlockPos pos = new BlockPos(posInLabyrinth.getX(), i, posInLabyrinth.getZ());
                    if (world.getBlockState(pos).getBlock().equals(PJOBlocks.LABYRINTH_DOOR)) {
                        connect(PJODimensions.LABYRINTH, pos);
                        return true;
                    }
                }

                // TODO more attemping to find other end

            }
            return false;
        }
    }

}
