package com.peter.peterspjo.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;

public class PJOClientNetworking {

    public static void registerClient() {
        
    }
    
    public static void sendAbilityUsePacket(World world, ClientPlayerEntity player) {
        ClientPlayNetworking.send(new AbilityUsePayload(world.getRegistryKey(), player.getUuidAsString()));
    }
}
