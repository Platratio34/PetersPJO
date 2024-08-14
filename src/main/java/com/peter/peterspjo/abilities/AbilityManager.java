package com.peter.peterspjo.abilities;

import java.util.HashMap;
import java.util.UUID;

import java.util.ArrayList;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Manages abilities of players
 */
public class AbilityManager {

    private HashMap<UUID, HashMap<String, AbstractAbility>> playerAbilities;

    /**
     * Instance of the Ability manager
     */
    public static final AbilityManager INSTANCE;

    static {
        INSTANCE = new AbilityManager();
    }

    private AbilityManager() {
        playerAbilities = new HashMap<UUID, HashMap<String, AbstractAbility>>();
        ServerTickEvents.START_WORLD_TICK.register(this::tick);
    }

    private void tick(ServerWorld world) {
        for (UUID playerUuid : playerAbilities.keySet()) {
            PlayerEntity playerEntity = world.getPlayerByUuid(playerUuid);
            if (playerEntity != null) {
                HashMap<String, AbstractAbility> abilities = playerAbilities.get(playerUuid);
                for (AbstractAbility ability : abilities.values()) {
                    ability.passiveTick(playerEntity, world);
                }
            }
        }
    }

    /**
     * Check if two abilities are compatible with echoer. (Calls <code>AbstractAbility.compatibleWith()</code> on both abilities)
     * @param ability1 Ability 1 to check
     * @param ability2 Ability 2 to check
     * @return If the two abilities are compatible
     */
    public boolean areCompatible(AbstractAbility ability1, AbstractAbility ability2) {
        return ability1.compatibleWith(ability2) && ability2.compatibleWith(ability1);
    }

    /**
     * If the provided ability can be added to the player
     * @param playerUuid UUID of player to check for
     * @param abilityReference Reference to Ability type 
     * @return If the player doesn't already have the ability and has no incompatible abilities.
     */
    public boolean canAdd(UUID playerUuid, Reference<AbstractAbility> abilityReference) {
        AbstractAbility newAbility = abilityReference.value();
        return canAdd(playerUuid, newAbility);
    }

    /**
     * If the provided ability can be added to the player
     * @param playerUuid UUID of player to check for
     * @param newAbility Ability type 
     * @return If the player doesn't already have the ability and has no incompatible abilities.
     */
    public boolean canAdd(UUID playerUuid, AbstractAbility newAbility) {
        String key = newAbility.id.toString();
        if (!playerAbilities.containsKey(playerUuid)) {
            return true;
        }
        HashMap<String, AbstractAbility> currentAbilities = playerAbilities.get(playerUuid);
        if (currentAbilities.containsKey(key)) {
            return false;
        }
        for (AbstractAbility cAbility : currentAbilities.values()) {
            if (!areCompatible(newAbility, cAbility)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add an ability to a player. Returns <code>false</code> if the player already has the ability, or has an incompatible ability
     * @param playerUuid Player to add the ability to
     * @param newAbilityReference Ability to add
     * @return If the ability was added
     */
    public boolean addAbility(UUID playerUuid, Reference<AbstractAbility> newAbilityReference) {
        if (!canAdd(playerUuid, newAbilityReference)) {
            return false;
        }
        AbstractAbility newAbility = newAbilityReference.value().instance();
        String key = newAbilityReference.getIdAsString();
        if (!playerAbilities.containsKey(playerUuid)) {
            HashMap<String, AbstractAbility> currentAbilities = new HashMap<String, AbstractAbility>();
            currentAbilities.put(key, newAbility);
            playerAbilities.put(playerUuid, currentAbilities);
            return true;
        }
        playerAbilities.get(playerUuid).put(key, newAbility);
        return true;
    }

    /**
     * Remove an ability from a player. Returns <code>false</code> if the player didn't have the ability
     * @param playerUuid Player to remove the ability from
     * @param newAbilityReference Ability to remove
     * @return If the ability was removed
     */
    public boolean removeAbility(UUID playerUuid, Reference<AbstractAbility> abilityReference) {
        if (!playerAbilities.containsKey(playerUuid)) {
            return false;
        }
        String key = abilityReference.getIdAsString();
        HashMap<String, AbstractAbility> currentAbilities = playerAbilities.get(playerUuid);
        if (!currentAbilities.containsKey(key)) {
            return false;
        }
        currentAbilities.remove(key);
        return true;
    }

    /**
     * Get a list of the Identifiers of all the abilities the player has
     * @param playerUuid Player to get list of abilities for
     * @return Identifiers of the abilities the player has
     */
    public Identifier[] listAbilityIdentifiers(UUID playerUuid) {
        if (!playerAbilities.containsKey(playerUuid)) {
            return new Identifier[] {};
        }
        HashMap<String, AbstractAbility> currentAbilities = playerAbilities.get(playerUuid);
        ArrayList<Identifier> rt = new ArrayList<Identifier>();
        for (AbstractAbility ability : currentAbilities.values()) {
            rt.add(ability.id);
        }
        return rt.toArray(new Identifier[0]);
    }

    /**
     * Check if player has ability
     * @param playerUuid Player to check
     * @param ability Ability to check for
     * @return If the player has ability
     */
    public boolean hasAbility(UUID playerUuid, Reference<AbstractAbility> ability) {
        return hasAbility(playerUuid, ability.value().id);
    }
    /**
     * Check if player has ability
     * @param playerUuid Player to check
     * @param ability Ability to check for
     * @return If the player has ability
     */
    public boolean hasAbility(UUID playerUuid, AbstractAbility ability) {
        return hasAbility(playerUuid, ability.id);
    }
    /**
     * Check if player has ability
     * @param playerUuid Player to check
     * @param ability Ability to check for
     * @return If the player has ability
     */
    public boolean hasAbility(UUID playerUuid, Identifier ability) {
        if (!playerAbilities.containsKey(playerUuid)) {
            return false;
        }
        return playerAbilities.get(playerUuid).containsKey(ability.toString());
    }

    /**
     * Get ability name translatable text.<br><br>
     * Translation key in the form: <code>ability.[namespace].[ability-name]</code>
     * @param abilityID ID of ability type
     * @return Translatable text of ability name
     */
    public static MutableText getAbilityNameTranslated(Identifier abilityID) {
        return Text.translatable(abilityID.toTranslationKey("ability"));
    }
}
