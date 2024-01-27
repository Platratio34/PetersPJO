package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.CelestialBronzeSpear;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpearEntity extends PersistentProjectileEntity {

    public static final String NAME = "spear_entity";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final EntityType<SpearEntity> TYPE = FabricEntityTypeBuilder
            .<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .trackRangeBlocks(16).trackedUpdateRate(10)
            .build();
    
    private ItemStack stack = new ItemStack(CelestialBronzeSpear.ITEM);

    public static void init() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
    }

    public SpearEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        constructor();
    }

    public SpearEntity(World world, LivingEntity owner) {
        super(TYPE, owner, world); // null will be changed later
        constructor();
    }

    public SpearEntity(World world, double x, double y, double z) {
        super(TYPE, x, y, z, world); // null will be changed later
        constructor();
    }

    private void constructor() {
        this.setDamage(5f);
        this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        this.setSound(SoundEvents.ENTITY_ARROW_HIT);
        this.setPierceLevel((byte)0xf);
    }

    @Override
    protected ItemStack asItemStack() {
        return stack;
    }

    public void setItem(ItemStack stack) {
        this.stack = stack;
    }
    // super.onEntityHit(entityHitResult);
    // Entity ent = entityHitResult.getEntity();

    // if (ent instanceof LivingEntity livingEntity) {
    // livingEntity.playSound(SoundEvents.ENTITY_ARROW_HIT, 2f, 1f);
    // // livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner(),
    // 1f));
    // }
    // }

}
