package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Empousai extends Monster {

    public static final String NAME = "empousai";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final EntityType<Empousai> TYPE = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Empousai::new)
            .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build();

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
        FabricDefaultAttributeRegistry.register(TYPE, Empousai.createMobAttributes());
    }

    protected Empousai(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

}
