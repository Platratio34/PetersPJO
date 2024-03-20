package com.peter.peterspjo.items;

import com.peter.peterspjo.PJODamageTypes;
import com.peter.peterspjo.enchantments.CelestialEnchantment;
import com.peter.peterspjo.enchantments.PJOEnchantments;
import com.peter.peterspjo.entities.Monster;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Rarity;

public class CelestialSword extends SwordItem {

    public CelestialSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, FabricItemSettings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        System.out.println("Thing");
        if (this instanceof Switchable) {
            stack = ((Switchable)this).getDefaultStateStack(stack);
        } else if (PJOEnchantments.has(stack, CelestialEnchantment.ID)) {
            stack.addEnchantment(PJOEnchantments.CELESTIAL, 1);
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
            target.damage(source, this.getAttackDamage());
        }
        return super.postHit(stack, target, attacker);
    }
    
    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

}
