package com.peter.peterspjo.networking;

import java.util.UUID;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.abilities.AbilityManager;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public record AbilityUsePayload(RegistryKey<World> world, String uuid) implements CustomPayload {

    public static final Id<AbilityUsePayload> ID = new Id<AbilityUsePayload>(PJO.id("ability_use"));

    public static final PacketCodec<RegistryByteBuf, AbilityUsePayload> CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), AbilityUsePayload::world,
            PacketCodecs.STRING, AbilityUsePayload::uuid,
            AbilityUsePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ID, (payload, context) -> {
            context.server().execute(() -> {
                AbilityManager.INSTANCE.useAbility(context.server().getWorld(payload.world), payload.getUuid());
            });
        });
    }

}
