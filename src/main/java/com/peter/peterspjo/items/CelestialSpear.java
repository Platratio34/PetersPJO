package com.peter.peterspjo.items;

import com.peter.peterspjo.entities.SpearEntity;

import net.minecraft.item.Item;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CelestialSpear extends CelestialSword {

    public CelestialSpear(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack
                                                         // in-hand

        user.getItemCooldownManager().set(this, 2);

        if (!world.isClient) {
            SpearEntity spearEntity = new SpearEntity(world, user);
            ItemStack s2 = itemStack.copy();
            if (!user.isCreative()) {
                s2.damage(1, user, (hand==Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
            spearEntity.setItem(s2);
            spearEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(spearEntity); // spawns entity
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.isCreative()) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

}
