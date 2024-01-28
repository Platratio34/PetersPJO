package com.peter.peterspjo.blocks;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class StoneBrazier extends HorizontalFacingBlock {

    public static final String NAME = "stone_brazier";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);

    public static final IntProperty VARIANT = IntProperty.of("variant", 0, 5);
    // public static final EnumProperty<Variant> VARIENT;

    public static final StoneBrazier BLOCK = new StoneBrazier(
            FabricBlockSettings.create().strength(4.0f).sounds(BlockSoundGroup.STONE).nonOpaque());
    public static final BlockItem ITEM = new BlockItem(BLOCK, new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.BLOCK, ID, BLOCK);
        Registry.register(Registries.ITEM, ID, ITEM);
    }

    public StoneBrazier(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(VARIANT, 0));
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block." + PJO.NAMESPACE + "." + NAME + ".tooltip"));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
        builder.add(Properties.HORIZONTAL_FACING);
    }

    // @Override
    // public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
    //         BlockHitResult hit) {
    //     if (world.isClient())
    //         return ActionResult.SUCCESS;
    //     player.playSound(SoundEvents.BLOCK_STONE_PLACE, 1, 1);
    //     int v = state.get(VARIANT);
    //     v++;
    //     if (v > 5) {
    //         v = 0;
    //     }
    //     world.setBlockState(pos, state.with(VARIANT, v));
    //     return ActionResult.SUCCESS;
    // }

    public BlockState updateState(BlockState state, WorldAccess world, BlockPos pos) {
        boolean n = world.getBlockState(pos.offset(Direction.NORTH)).getBlock() instanceof StoneBrazier;
        boolean e = world.getBlockState(pos.offset(Direction.EAST)).getBlock() instanceof StoneBrazier;
        boolean s = world.getBlockState(pos.offset(Direction.SOUTH)).getBlock() instanceof StoneBrazier;
        boolean w = world.getBlockState(pos.offset(Direction.WEST)).getBlock() instanceof StoneBrazier;
        if (!(n || e || s || w)) {
            return state.with(VARIANT, 0);
        } else if (n && !e && !s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.SOUTH).with(VARIANT, 1);
        } else if (!n && e && !s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.WEST).with(VARIANT, 1);
        } else if (!n && !e && s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(VARIANT, 1);
        } else if (!n && !e && !s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.EAST).with(VARIANT, 1);
        } else if (n && !e && s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(VARIANT, 2);
        } else if (!n && e && !s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.EAST).with(VARIANT, 2);
        } else if (n && e && !s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.WEST).with(VARIANT, 3);
        } else if (!n && e && s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(VARIANT, 3);
        } else if (!n && !e && s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.EAST).with(VARIANT, 3);
        } else if (n && !e && !s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.SOUTH).with(VARIANT, 3);
        } else if (n && e && s && !w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.WEST).with(VARIANT, 4);
        } else if (!n && e && s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(VARIANT, 4);
        } else if (n && !e && s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.EAST).with(VARIANT, 4);
        } else if (n && e && !s && w) {
            return state.with(Properties.HORIZONTAL_FACING, Direction.SOUTH).with(VARIANT, 4);
        } else if (n && e && s && w) {
            return state.with(VARIANT, 5);
        }
        return state;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
            WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        
        if (direction.getAxis().isHorizontal()) { // a block on the horizontal plane just updated
            return updateState(state, world, pos);
        }
        return state;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING,
                ctx.getHorizontalPlayerFacing());
    }

    public static enum Variant {
        SINGLE(0),
        END(1),
        LINE(2),
        CORNER(3),
        SIDE(4),
        MIDDLE(5);

        private int id;

        Variant(int id){
            this.id = id;
        }

        public int getID(){
            return id;
        }
    }

}
