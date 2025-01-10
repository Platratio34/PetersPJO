package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJODamageTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class Monster extends HostileEntity {

    protected Monster(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isInvulnerableTo(ServerWorld world, DamageSource damageSource) {
        if (damageSource.isOf(PJODamageTypes.CELESTIAL_DAMAGE_TYPE))
            return super.isInvulnerableTo(world, damageSource);
        // if (!(damageSource.isIn(DamageTypeTags.IS_FIRE) || damageSource.isIn(DamageTypeTags.IS_EXPLOSION) || damageSource.isIn(DamageTypeTags.IS_DROWNING) || damageSource.isIn(DamageTypeTags.IS_FALL) || damageSource.isIn(DamageTypeTags.IS_FREEZING) || damageSource.isIn(DamageTypeTags.IS_LIGHTNING))) {
        //     return true;
        // }
        return super.isInvulnerableTo(world, damageSource);
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (source.isOf(DamageTypes.PLAYER_ATTACK)) {
            amount *= 0.5f;
        }
        return super.damage(world, source, amount);
    }

}
