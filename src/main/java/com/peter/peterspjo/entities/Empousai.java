package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Empousai extends Monster {

    public static final String NAME = "empousai";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final EntityType<Empousai> TYPE = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Empousai::new)
            .dimensions(EntityDimensions.fixed(0.75f, 2f)).build();
    
    public static final Identifier EGG_ID = new Identifier(PJO.NAMESPACE, NAME + "_spawn_egg");
    public static final SpawnEggItem EGG = new SpawnEggItem(TYPE, 0x5b276c, 0xfdff2f, new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
        FabricDefaultAttributeRegistry.register(TYPE, Empousai.createMobAttributes());
        Registry.register(Registries.ITEM, EGG_ID, EGG);
    }

    protected Empousai(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

}
