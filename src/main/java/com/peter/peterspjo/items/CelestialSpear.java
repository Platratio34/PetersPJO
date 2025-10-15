package com.peter.peterspjo.items;

import com.peter.peterspjo.entities.SpearEntity;

import net.minecraft.item.Item;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;

public class CelestialSpear extends CelestialSword {

    public CelestialSpear(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings, Identifier id) {
        super(toolMaterial, attackDamage, attackSpeed, settings, id);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(itemStack, 10);

        if (!world.isClient) {
            SpearEntity spearEntity = new SpearEntity(world, user);
            ItemStack s2 = itemStack.copy();
            if (!user.isCreative()) {
                s2.damage(1, user, (hand==Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
            spearEntity.setItem(s2);
            spearEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(spearEntity); // spawns entity
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ActionResult.SUCCESS;
    }

}
