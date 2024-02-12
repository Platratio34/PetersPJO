package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

public class Pegasus extends AbstractHorseEntity {

    public static final String NAME = "pegasus";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final EntityType<Pegasus> TYPE = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Pegasus::new)
            .dimensions(EntityDimensions.changing(1.5f, 1.5f)).build();

    public static final Identifier EGG_ID = new Identifier(PJO.NAMESPACE, NAME + "_spawn_egg");
    public static final SpawnEggItem EGG = new SpawnEggItem(TYPE, 0x5b276c, 0xfdff2f, new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
        FabricDefaultAttributeRegistry.register(TYPE, Pegasus.createMobAttributes());
        Registry.register(Registries.ITEM, EGG_ID, EGG);
    }

    public Pegasus(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.HORSE_JUMP_STRENGTH, 1.0);
    }

    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }

}
