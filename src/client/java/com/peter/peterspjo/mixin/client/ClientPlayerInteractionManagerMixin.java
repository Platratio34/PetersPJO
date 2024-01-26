package com.peter.peterspjo.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.peter.peterspjo.items.Spear;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method="onGetReachDistance", at=@At("HEAD"), cancellable = true)
    public void onGetReachDistance(CallbackInfoReturnable<Float> cir){
        // PlayerEntity player = MinecraftClient.getInstance().player;
        // if(player != null && player.getMainHandStack().getItem() instanceof Spear){
        //     cir.setReturnValue(12.0f);
        // }
    }
}
