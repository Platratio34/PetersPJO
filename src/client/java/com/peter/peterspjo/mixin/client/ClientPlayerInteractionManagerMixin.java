package com.peter.peterspjo.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    MinecraftClient instance = MinecraftClient.getInstance();

}
