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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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

    @Override
    public void onDamaged(DamageSource source) {
        if (source.isOf(DamageTypes.FALL))
            return;
        super.onDamaged(source);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isOf(DamageTypes.FALL))
            return false;
        return super.damage(source, amount);
    }

    // private boolean fly = false;
    // @Override
    // public void startJumping(int height) {
    //     if (isOnGround()) {
    //         super.startJumping(height);
    //     } else {
    //         fly = true;
    //     }
    // }
    
    private int tSJ = 100000000;
    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        tSJ++;
        if (!this.isLogicalSideForUpdatingMovement())
            return;
        if (jumping && !isOnGround()) {
            jumping = false;
            tSJ = 0;
        }
        Vec3d cVel = this.getVelocity();
        float f = MathHelper.sin(this.getYaw() * ((float) Math.PI / 180)) * -1;
        float g = MathHelper.cos(this.getYaw() * ((float) Math.PI / 180));
        float speed = controllingPlayer.forwardSpeed * 100f;
        if (tSJ < 5) {
            cVel.add(f * speed, 0.0, g * speed);
            this.setVelocity(cVel.x, 1, cVel.z);
        } else if (!isOnGround()) {
            cVel.add(f * speed, 0.0, g * speed);
            this.setVelocity(cVel.x, (float) Math.max(cVel.y, -0.2), cVel.z);
        }
    }
    
    @Override
    protected float getOffGroundSpeed() {
        return getMovementSpeed() * 0.9f;
    }

}
