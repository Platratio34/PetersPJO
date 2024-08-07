package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.CelestialBronzeSpear;
import com.peter.peterspjo.items.PJOItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SpearEntity extends PersistentProjectileEntity {

    public static final String NAME = "spear_entity";
    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);
    public static final EntityType<SpearEntity> TYPE = EntityType.Builder.<SpearEntity>create(SpearEntity::new, SpawnGroup.MISC)
            .dimensions(0.25f, 0.25f)
            .maxTrackingRange(16).trackingTickInterval(10)
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
        super(TYPE, world);
        setOwner(owner);
        setPosition(owner.getX(), owner.getEyeY() - 0.10000000149011612, owner.getZ());
        constructor();
    }

    public SpearEntity(World world, double x, double y, double z) {
        super(TYPE, world);
        setPosition(x, y, z);
        constructor();
    }

    private void constructor() {
        this.setDamage(5f);
        this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        this.setSound(SoundEvents.ENTITY_ARROW_HIT);
        this.setPierceLevel((byte) 0xf);
        // this.age
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

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(PJOItems.CELESTIAL_BRONZE_SPEAR);
    }

    // if (ent instanceof LivingEntity livingEntity) {
    // livingEntity.playSound(SoundEvents.ENTITY_ARROW_HIT, 2f, 1f);
    // // livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner(),
    // 1f));
    // }
    // }

}
