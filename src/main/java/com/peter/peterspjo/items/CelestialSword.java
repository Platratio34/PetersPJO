package com.peter.peterspjo.items;

import com.peter.peterspjo.PJODamageTypes;
import com.peter.peterspjo.entities.Monster;

import net.minecraft.item.Item;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Rarity;

public class CelestialSword extends SwordItem {

    public int attackDamage;

    public CelestialSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, settings.rarity(getRarity()));
        this.attackDamage = attackDamage;
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        if (this instanceof Switchable) {
            stack = ((Switchable) this).getDefaultStateStack(stack);
        }
        return stack;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this instanceof Switchable && !((Switchable) this).isWeapon(stack)) {
            return super.postHit(stack, target, attacker);
        }
        if (target instanceof Monster) {
            DamageSource source = new DamageSource(
                    attacker.getDamageSources().registry.entryOf(PJODamageTypes.CELESTIAL_DAMAGE_TYPE), attacker);
            target.damage(source, this.attackDamage);
        }
        return super.postHit(stack, target, attacker);
    }

    public static Rarity getRarity() {
        return Rarity.UNCOMMON;
    }

}
