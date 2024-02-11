package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJODamageTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class Monster extends HostileEntity {

    protected Monster(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.isOf(PJODamageTypes.CELESTIAL_DAMAGE_TYPE))
            return super.isInvulnerableTo(damageSource);
        // if (!(damageSource.isIn(DamageTypeTags.IS_FIRE) || damageSource.isIn(DamageTypeTags.IS_EXPLOSION) || damageSource.isIn(DamageTypeTags.IS_DROWNING) || damageSource.isIn(DamageTypeTags.IS_FALL) || damageSource.isIn(DamageTypeTags.IS_FREEZING) || damageSource.isIn(DamageTypeTags.IS_LIGHTNING))) {
        //     return true;
        // }
        return super.isInvulnerableTo(damageSource);
    }

}
