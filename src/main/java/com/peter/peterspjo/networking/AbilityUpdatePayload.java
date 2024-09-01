package com.peter.peterspjo.networking;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.abilities.AbstractAbility;
import com.peter.peterspjo.abilities.AbstractChargedAbility;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AbilityUpdatePayload implements CustomPayload {

    public static final Id<AbilityUpdatePayload> ID = new Id<AbilityUpdatePayload>(PJO.id("ability_update"));
    public static final PacketCodec<RegistryByteBuf, AbilityUpdatePayload> CODEC = PacketCodec.tuple(
            Action.PACKET_CODEC, AbilityUpdatePayload::getAction,
            Identifier.PACKET_CODEC, AbilityUpdatePayload::getAbilityId,
            PacketCodecs.INTEGER, AbilityUpdatePayload::getCharge,
        AbilityUpdatePayload::new
    );

    public final Action action;
    public final Identifier ability;
    public final int charge;

    public AbilityUpdatePayload(Action action, Identifier ability, int charge) {
        this.action = action;
        this.ability = ability;
        this.charge = charge;
    }

    public Action getAction() {
        return action;
    }

    public Identifier getAbilityId() {
        return ability;
    }

    public int getCharge() {
        return charge;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
    }

    public static void sendUpdate(ServerPlayerEntity player, Action action, Identifier ability, int charge) {
        ServerPlayNetworking.send(player, new AbilityUpdatePayload(action, ability, charge));
    }

    public static void sendAdd(ServerPlayerEntity player, Reference<AbstractAbility> ability) {
        sendAdd(player, Identifier.of(ability.getIdAsString()));
    }

    public static void sendAdd(ServerPlayerEntity player, Identifier ability) {
        sendUpdate(player, Action.ADD, ability, 0);
    }
    
    public static void sendRemove(ServerPlayerEntity player, Reference<AbstractAbility> ability) {
        sendRemove(player, Identifier.of(ability.getIdAsString()));
    }

    public static void sendRemove(ServerPlayerEntity player, Identifier ability) {
        sendUpdate(player, Action.REMOVE, ability, 0);
    }
    
    public static void sendCharge(ServerPlayerEntity player, Reference<AbstractAbility> ability, int charge) {
        sendCharge(player, Identifier.of(ability.getIdAsString()), charge);
    }

    public static void sendCharge(ServerPlayerEntity player, Identifier ability, int charge) {
        sendUpdate(player, Action.CHARGE, ability, charge);
    }
    
    public static void sendCharge(AbstractChargedAbility ability) {
        if (ability.getPlayer() == null)
            return;
        sendCharge((ServerPlayerEntity) ability.getPlayer(), ability.id, ability.getCharge());
    }

    public enum Action {
        ADD(0),
        REMOVE(1),
        CHARGE(2);

        private static final Action[] VALUES = new Action[] { ADD, REMOVE, CHARGE };

        public final int code;

        private Action(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static Action fromCode(int code) {
            return VALUES[code];
        }

        public static final PacketCodec<PacketByteBuf, Action> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER, Action::getCode,
                Action::fromCode
        );
    }

}
