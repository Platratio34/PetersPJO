package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Hellhound extends Monster {

    public static final String NAME = "hellhound";
    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);
    public static final EntityType<Hellhound> TYPE = EntityType.Builder.create(Hellhound::new, SpawnGroup.MONSTER)
            .dimensions(1.75f, 2f).build();

    public static final Identifier EGG_ID = Identifier.of(PJO.NAMESPACE, NAME + "_spawn_egg");
    public static final SpawnEggItem EGG = new SpawnEggItem(TYPE, 0x2c2c2c, 0x232323, new Item.Settings());

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
        FabricDefaultAttributeRegistry.register(TYPE, Hellhound.createMobAttributes());
        Registry.register(Registries.ITEM, EGG_ID, EGG);
        PJOEntities.MONSTERS.add(TYPE);
    }

    protected Hellhound(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0f, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 10.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity) this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
                .add(EntityAttributes.GENERIC_ARMOR, 3.0);
    }

}
