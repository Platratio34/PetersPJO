package com.peter.peterspjo.enchantments;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.PJODamageTypes;
import com.peter.peterspjo.entities.Monster;
import com.peter.peterspjo.items.CelestialSword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CelestialEnchantment extends Enchantment {

    public static final String NAME = "celestial";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);

    protected CelestialEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof Monster) {
            DamageSource source = new DamageSource(
                    user.getDamageSources().registry.entryOf(PJODamageTypes.CELESTIAL_DAMAGE_TYPE), user);
            target.damage(source, 10f);
        }
        super.onTargetDamaged(user, target, level);
    }
    
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if (!(stack.getItem() instanceof CelestialSword)) {
            return false;
        }
        return super.isAcceptableItem(stack);
    }

}
