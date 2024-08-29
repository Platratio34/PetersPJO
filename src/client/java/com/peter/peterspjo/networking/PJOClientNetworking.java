package com.peter.peterspjo.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.VaultBlockEntity.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PJOClientNetworking {

    public static void registerClient() {

    }
    
    public static void sendAbilityUsePacket(World world, ClientPlayerEntity player) {
        ClientPlayNetworking.send(new AbilityUsePayload(world.getRegistryKey(), player.getUuidAsString()));
    }
}
