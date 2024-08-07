package com.peter.peterspjo.blocks.fluids;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class StyxWater extends WaterFluid {

    public static final String NAME = "styx_water";
    public static final FlowableFluid STILL = Registry.register(Registries.FLUID, Identifier.of(PJO.NAMESPACE, NAME),
            new Still());
    public static final FlowableFluid FLOWING = Registry.register(Registries.FLUID,
            Identifier.of(PJO.NAMESPACE, NAME + "_flowing"), new Flowing());
    public static final BucketItem BUCKET = Registry.register(Registries.ITEM,
            Identifier.of(PJO.NAMESPACE, NAME + "_bucket"),
            new BucketItem(STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final FluidBlock BLOCK = Registry.register(Registries.BLOCK, Identifier.of(PJO.NAMESPACE, NAME),
            new FluidBlock(STILL, AbstractBlock.Settings.copy(Blocks.WATER).liquid()));

    @Override
    public Fluid getFlowing() {
        return FLOWING;
    }

    @Override
    public Fluid getStill() {
        return STILL;
    }

    @Override
    protected boolean isInfinite(World world) { // TODO make this only true in underworld
        return true;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    public int getLevelDecreasePerBlock(WorldView var1) {
        return 1;
    }

    @Override
    public Item getBucketItem() {
        return BUCKET;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid,
            Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickRate(WorldView var1) {
        return 5;
    }

    @Override
    protected float getBlastResistance() {
        return 100f;
    }

    @Override
    public BlockState toBlockState(FluidState fluidState) {
        return BLOCK.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(fluidState));
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == STILL || fluid == FLOWING;
    }

    public static class Flowing extends StyxWater {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends StyxWater {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }

}
