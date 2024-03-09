package com.peter.peterspjo.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.peter.peterspjo.items.CelestialBronzeDagger;
import com.peter.peterspjo.items.Spear;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    MinecraftClient instance = MinecraftClient.getInstance();

    @Inject(method="getReachDistance", at=@At("HEAD"), cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = instance.player;
        if (player != null && player.getMainHandStack().getItem() instanceof Spear) {
            cir.setReturnValue(12.0f);
            cir.cancel();
        } else if (player != null && player.getMainHandStack().getItem() instanceof CelestialBronzeDagger) {
            cir.setReturnValue(3.0f);
            cir.cancel();
        }
    }
    
    @Inject(method="hasExtendedReach", at=@At("HEAD"), cancellable = true)
    public void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = instance.player;
        if (player != null && player.getMainHandStack().getItem() instanceof Spear && player.getOffHandStack().isEmpty()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
