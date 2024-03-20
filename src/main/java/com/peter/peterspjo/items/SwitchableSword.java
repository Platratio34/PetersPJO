package com.peter.peterspjo.items;

import com.peter.peterspjo.enchantments.CelestialEnchantment;
import com.peter.peterspjo.enchantments.PJOEnchantments;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class SwitchableSword extends CelestialSword implements Switchable {

    public SwitchableSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed,
            FabricItemSettings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            NbtCompound nbt = stack.getOrCreateNbt();
            boolean isSword = !nbt.getBoolean("is_sword");
            nbt.putBoolean("is_sword", isSword);
            // if (isSword) {
            //     stack.addEnchantment(PJOEnchantments.CELESTIAL, 1);
            // } else {
            //     PJOEnchantments.disenchant(stack, CelestialEnchantment.ID);
            // }
            stack.setNbt(nbt);
            user.getItemCooldownManager().set(this, 1);
        }
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack getDefaultStateStack(ItemStack stack) {
        return stack;
    }

    @Override
    public boolean isWeapon(ItemStack stack) {
        if (!stack.hasNbt())
            return false;
        return stack.getNbt().getBoolean("is_sword");
    }

}
