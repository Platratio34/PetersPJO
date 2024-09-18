package com.peter.peterspjo.blocks;

import com.peter.peterspjo.abilities.AbilityManager;
import com.peter.peterspjo.abilities.PJOAbilities;
import com.peter.peterspjo.data.BrazierRecipe;
import com.peter.peterspjo.data.BrazierRecipeData;
import com.peter.peterspjo.data.BrazierRecipe.BrazierRecipeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.MutableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public abstract class BrazierBlock extends TooltipedBlock {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    private static Settings settingsConstructor(Settings settings) {
        return settings.luminance(state -> getLightLevel(state));
    }

    private void constructor() {
        setDefaultState(getDefaultState().with(LIT, false));
    }

    public BrazierBlock(Settings settings, String blockName) {
        super(settingsConstructor(settings), blockName);
        constructor();
    }

    public BrazierBlock(Settings settings, MutableText tooltip) {
        super(settingsConstructor(settings), tooltip);
        constructor();
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT);
    }

    public static boolean isLit(BlockState state) {
        return state.get(LIT);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return super.onUse(state, world, pos, player, hit);
        }
        if (isLit(state)) {
            world.setBlockState(pos, state.with(LIT, false));
            return ActionResult.CONSUME;
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        // if (world.isClient) {
        // return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        // }
        if (stack.isOf(Items.FLINT_AND_STEEL)) {
            if (!isLit(state)) {
                if (!world.isClient) {
                    world.setBlockState(pos, state.with(LIT, true));
                    stack.damage(1, player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1f, 1f);
                }
            }
            return ItemActionResult.SUCCESS;
        } else if (isLit(state)) {
            if (stack.isOf(Items.AIR)) {
                if (!world.isClient) {
                    world.setBlockState(pos, state.with(LIT, false));
                    world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                }
                return ItemActionResult.SUCCESS;
            }
            // if (!world.isClient) {
            //     PJO.LOGGER.info(stack.toString());
            // }
            BrazierRecipe recipe = BrazierRecipeData.INSTANCE.getMatching(stack);
            if (recipe != null) {
                boolean success = false;

                if (recipe.type == BrazierRecipeType.GRANT_ABILITY) {
                    if (AbilityManager.INSTANCE.hasAbility(player, recipe.resultId)) {
                        if (PJOAbilities.isCharged(recipe.resultId)) {
                            success = true;
                            if (!world.isClient) {
                                success = AbilityManager.INSTANCE.chargeAbility(player, recipe.resultId);
                            }
                        }
                    } else if(AbilityManager.INSTANCE.canAdd(player, recipe.resultId)) {
                        if (!world.isClient) {
                            AbilityManager.INSTANCE.addAbility(player, recipe.resultId);
                        }
                        success = true;
                    }
                } else if (recipe.type == BrazierRecipeType.CHARGE_ABILITY) {
                    if (AbilityManager.INSTANCE.hasAbility(player, recipe.resultId)) {
                        if (PJOAbilities.isCharged(recipe.resultId)) {
                            success = true;
                            if (!world.isClient) {
                                success = AbilityManager.INSTANCE.chargeAbility(player, recipe.resultId, recipe.amount);
                            }
                        }
                    }
                } else if (recipe.type == BrazierRecipeType.GRANT_ITEM) {
                    if (!world.isClient) {
                        player.giveItemStack(recipe.result.copy());
                    }
                    success = true;
                }
                if (success) {
                    if (!world.isClient)
                        if (!player.isCreative())
                            stack.decrement(1);
                        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1f);
                    return ItemActionResult.SUCCESS;
                } else {
                    return ItemActionResult.CONSUME;
                }
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(24) == 0 && isLit(state)) {
            world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5,
                    SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.3F, false);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    protected static int getLightLevel(BlockState state) {
        return isLit(state) ? 15 : 0;
    }

}
