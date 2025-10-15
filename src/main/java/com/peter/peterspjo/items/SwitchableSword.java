package com.peter.peterspjo.items;

import java.util.List;

import com.mojang.serialization.Codec;
import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;

public abstract class SwitchableSword extends CelestialSword implements Switchable, TooltipSupplier {

    public static ComponentType<Boolean> IS_SWORD_COMPONENT = Registry.register(Registries.DATA_COMPONENT_TYPE, PJO.id("is_sword"), ComponentType.<Boolean>builder().codec(Codec.BOOL).build());

    public SwitchableSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed,
            Item.Settings settings, Identifier id) {
        super(toolMaterial, attackDamage, attackSpeed, settings, id);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(stack)) {
            boolean isSword = !stack.getOrDefault(IS_SWORD_COMPONENT, false);
            stack.set(IS_SWORD_COMPONENT, isSword);
            user.getItemCooldownManager().set(stack, 1);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    // @Override
    // public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    //     ItemStack stack = user.getStackInHand(hand);
    //     if (!user.getItemCooldownManager().isCoolingDown(this)) {
    //         boolean isSword = !stack.getOrDefault(IS_SWORD_COMPONENT, false);
    //         stack.set(IS_SWORD_COMPONENT, isSword);
    //         user.getItemCooldownManager().set(this, 1);
    //     }
    //     return TypedActionResult.success(stack, world.isClient());
    // }

    @Override
    public ItemStack getDefaultStateStack(ItemStack stack) {
        return stack;
    }

    @Override
    public boolean isWeapon(ItemStack stack) {
        return stack.getOrDefault(IS_SWORD_COMPONENT, false);
    }

    @Override
    public void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> tooltip) {
        if (tooltipType == TooltipType.ADVANCED) {
            tooltip.add(Text.of("is_sword: " + isWeapon(itemStack)));
        }
    }

}
