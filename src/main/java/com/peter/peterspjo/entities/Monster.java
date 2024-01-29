package com.peter.peterspjo.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class Monster extends HostileEntity {

    protected Monster(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

}
