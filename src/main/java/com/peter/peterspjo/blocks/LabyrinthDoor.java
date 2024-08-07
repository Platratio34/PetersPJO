package com.peter.peterspjo.blocks;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.worldgen.labyrinth.DoorManager;
import com.peter.peterspjo.worldgen.labyrinth.DoorManager.DoorData;

import net.minecraft.item.Item;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;

public class LabyrinthDoor extends DoorBlock {

    public static String NAME = "labyrinth_door";
    public static Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);
    public static DoorBlock BLOCK = Registry.register(Registries.BLOCK, ID,
            new LabyrinthDoor(AbstractBlock.Settings.copy(Blocks.IRON_DOOR), BlockSetType.IRON));
    public static BlockItem ITEM = Registry.register(Registries.ITEM, ID,
            new BlockItem(BLOCK, new Item.Settings()));

    public LabyrinthDoor(Settings settings, BlockSetType blockSetType) {
        super(blockSetType, settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient())
            return ActionResult.PASS;
        
        Mutable doorPos = pos.mutableCopy();
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            doorPos.setY(doorPos.getY()-1);
        }

        RegistryKey<World> dimensionKey = world.getRegistryKey();
        DoorManager doorManager = DoorManager.get();
        DoorData door = doorManager.getOrCreateDoor(dimensionKey, doorPos);
        if (!door.tryTeleport(world, player)) {
            if (door.tryConnect(world.getServer())) {
                if (!door.tryTeleport(world, player)) {
                    PJO.LOGGER.warn("Tried to link door at " + doorPos.toString() + " in "
                            + dimensionKey.getValue().toString()+"; Teleport failed");
                }
            } else {
                PJO.LOGGER.warn("Unable to link labyrinth door at " + doorPos.toString() + " in " + dimensionKey.getValue().toString());
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }

}
