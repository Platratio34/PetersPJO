package com.peter.peterspjo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.peter.peterspjo.items.CelestialBronzeDagger;
import com.peter.peterspjo.items.CelestialSpear;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method="getEntityInteractionRange", at=@At("HEAD"), cancellable = true)
    public void getEntityInteractionRange(CallbackInfoReturnable<Double> cir) {
        PlayerEntity player = (PlayerEntity)((Entity) this);
        if (player.getMainHandStack().getItem() instanceof CelestialSpear) {
            cir.setReturnValue(12.0);
            cir.cancel();
        } else if (player.getMainHandStack().getItem() instanceof CelestialBronzeDagger) {
            cir.setReturnValue(3.0);
            cir.cancel();
        }
    }

}
