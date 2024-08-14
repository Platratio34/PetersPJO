package com.peter.peterspjo;

import java.util.Collection;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.peter.peterspjo.abilities.AbilityManager;
import com.peter.peterspjo.abilities.AbstractAbility;
import com.peter.peterspjo.abilities.PJOAbilities;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PJOCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(initAbilitiesCommand(registryAccess));
        });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> initAbilitiesCommand(
            CommandRegistryAccess registryAccess) {
        /*
         * ability give <player> <ability>
         * ability remove <player> <ability>
         * ability list <player> [id]
         */
        LiteralArgumentBuilder<ServerCommandSource> cmd = CommandManager.literal("ability");
        cmd.then(CommandManager.literal("give")
                .then(CommandManager.argument("player", EntityArgumentType.players())
                        .then(CommandManager.argument("ability",
                                RegistryEntryReferenceArgumentType.registryEntry(registryAccess,
                                        PJOAbilities.ABILITIES_REGISTRY_KEY))
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context,
                                            "player");
                                    Reference<AbstractAbility> ability = RegistryEntryReferenceArgumentType
                                            .getRegistryEntry(
                                                    context, "ability",
                                                    PJOAbilities.ABILITIES_REGISTRY_KEY);
                                    if (players.size() < 1) {
                                        context.getSource().sendError(Text.of("Must select at least 1 player"));
                                        return -1;
                                    }
                                    for (ServerPlayerEntity playerEntity : players) {
                                        AbilityManager.INSTANCE.addAbility(playerEntity.getUuid(), ability);
                                    }
                                    context.getSource().sendFeedback(
                                            () -> {
                                                String playerNames = "";
                                                for (ServerPlayerEntity playerEntity : players) {
                                                    if (playerNames.length() > 0) {
                                                        playerNames += "§r, §c";
                                                    }
                                                    playerNames += playerEntity.getNameForScoreboard();
                                                }
                                                return Text.of(String.format("Giving ability §a%s§r to §a%s§r",
                                                        ability.getIdAsString(), playerNames));
                                            },
                                            false);
                                    return 1;
                                }))));
        cmd.then(CommandManager.literal("remove")
                .then(CommandManager.argument("player", EntityArgumentType.players())
                        .then(CommandManager.argument("ability",
                                RegistryEntryReferenceArgumentType.registryEntry(registryAccess,
                                        PJOAbilities.ABILITIES_REGISTRY_KEY))
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context,
                                            "player");
                                    Reference<AbstractAbility> ability = RegistryEntryReferenceArgumentType
                                            .getRegistryEntry(
                                                    context, "ability",
                                                    PJOAbilities.ABILITIES_REGISTRY_KEY);
                                    if (players.size() < 1) {
                                        context.getSource().sendError(Text.of("Must select at least 1 player"));
                                        return -1;
                                    }
                                    for (ServerPlayerEntity playerEntity : players) {
                                        AbilityManager.INSTANCE.removeAbility(playerEntity.getUuid(), ability);
                                    }
                                    context.getSource().sendFeedback(
                                            () -> {
                                                String playerNames = "";
                                                for (ServerPlayerEntity playerEntity : players) {
                                                    if (playerNames.length() > 0) {
                                                        playerNames += "§r, §c";
                                                    }
                                                    playerNames += playerEntity.getNameForScoreboard();
                                                }
                                                return Text.of(String.format("Removing ability §c%s§r from §c%s§r",
                                                        ability.getIdAsString(), playerNames));
                                            },
                                            false);
                                    return 1;
                                }))));
        cmd.then(CommandManager.literal("list")
                .then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> {
                    ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                    context.getSource().sendFeedback(
                            () -> {
                                MutableText text = Text.literal(player.getNameForScoreboard() + " has: ");
                                Identifier[] abilities = AbilityManager.INSTANCE
                                        .listAbilityIdentifiers(player.getUuid());
                                for (int i = 0; i < abilities.length; i++) {
                                    if (i > 0) {
                                        text.append("§r, ");
                                    }
                                    text.append(AbilityManager.getAbilityNameTranslated(abilities[i]));
                                }
                                return text;
                            },
                            false);
                    return 1;
                })
                        .then(CommandManager.literal("id").executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            context.getSource().sendFeedback(
                                    () -> {
                                        MutableText text = Text.literal(player.getNameForScoreboard() + " has: ");
                                        Identifier[] abilities = AbilityManager.INSTANCE
                                                .listAbilityIdentifiers(player.getUuid());
                                        for (int i = 0; i < abilities.length; i++) {
                                            if (i > 0) {
                                                text.append(", ");
                                            }
                                            text.append(abilities[i].toString());
                                        }
                                        return text;
                                    },
                                    false);
                            return 1;
                        }))));
        return cmd;
    }
}
