package com.peter.peterspjo.abilities;

import java.util.HashMap;
import java.util.UUID;

import java.util.ArrayList;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

/**
 * Manages abilities of players
 */
public class AbilityManager extends PersistentState {

    private HashMap<UUID, HashMap<String, AbstractAbility>> playerAbilities;

    private static final String NAME = "ability_manager";
    private static final Type<AbilityManager> TYPE = new Type<AbilityManager>(AbilityManager::new, AbilityManager::createFromNbt, null);

    /**
     * Instance of the Ability manager
     */
    public static AbilityManager INSTANCE;

    public static void init() {
    }
    
    private MinecraftServer server;

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
        PlayerEntity player = server.getPlayerManager().getPlayer(playerUuid);
        if (player != null) {
            newAbility.setPlayerEntity(player);
        } else {
            newAbility.setPlayerUuid(playerUuid);
        }
        String key = newAbilityReference.getIdAsString();
        if (!playerAbilities.containsKey(playerUuid)) {
            HashMap<String, AbstractAbility> currentAbilities = new HashMap<String, AbstractAbility>();
            currentAbilities.put(key, newAbility);
            playerAbilities.put(playerUuid, currentAbilities);
            markDirty();
            return true;
        }
        playerAbilities.get(playerUuid).put(key, newAbility);
        markDirty();
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
        PlayerEntity playerEntity = server.getPlayerManager().getPlayer(playerUuid);
        currentAbilities.get(key).remove(playerEntity);
        currentAbilities.remove(key);
        markDirty();
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

    /**
     * Use all active abilities the player has
     * @param world World to use abilities in
     * @param playerUuid UUID of player using their abilities
     */
    public void useAbility(ServerWorld world, UUID playerUuid) {
        PlayerEntity playerEntity = world.getPlayerByUuid(playerUuid);
        if (playerEntity != null && playerAbilities.containsKey(playerUuid)) {
            HashMap<String, AbstractAbility> abilities = playerAbilities.get(playerUuid);
            for (AbstractAbility ability : abilities.values()) {
                ability.onUseAbility(playerEntity, world);
            }
        }
    }

    protected AbstractAbility getAbility(UUID playerUuid, Identifier ability) {
        if (!hasAbility(playerUuid, ability)) {
            return null;
        }
        return playerAbilities.get(playerUuid).get(ability.toString());
    }

    /**
     * Charge specified ability by the default amount
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeAbility(UUID playerUuid, Reference<AbstractAbility> ability) {
        return chargeAbility(playerUuid, ability.value().id);
    }
    /**
     * Charge specified ability by the default amount
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeAbility(UUID playerUuid, AbstractAbility ability) {
        return chargeAbility(playerUuid, ability.id);
    }
    /**
     * Charge specified ability by the default amount
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeAbility(UUID playerUuid, Identifier abilityId) {
        if (!hasAbility(playerUuid, abilityId)) {
            return false;
        }
        AbstractAbility ability = getAbility(playerUuid, abilityId);
        if (!(ability instanceof AbstractChargedAbility)) {
            return false;
        }
        ((AbstractChargedAbility) ability).charge();
        return true;
    }

    /**
     * Charge specified ability by a specific
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeAbility(UUID playerUuid, Reference<AbstractAbility> ability, int charge) {
        return chargeAbility(playerUuid, ability.value().id, charge);
    }
    /**
     * Charge specified ability by a specific
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeAbility(UUID playerUuid, AbstractAbility ability, int charge) {
        return chargeAbility(playerUuid, ability.id, charge);
    }
    /**
     * Charge specified ability by a specific
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeAbility(UUID playerUuid, Identifier abilityId, int charge) {
        if (!hasAbility(playerUuid, abilityId)) {
            return false;
        }
        AbstractAbility ability = getAbility(playerUuid, abilityId);
        if (!(ability instanceof AbstractChargedAbility)) {
            return false;
        }
        ((AbstractChargedAbility) ability).charge(charge);
        return true;
    }

    /**
     * Charge specified ability to a specific
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeSetAbility(UUID playerUuid, Reference<AbstractAbility> ability, int charge) {
        return chargeSetAbility(playerUuid, ability.value().id, charge);
    }
    /**
     * Charge specified ability to a specific
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeSetAbility(UUID playerUuid, AbstractAbility ability, int charge) {
        return chargeSetAbility(playerUuid, ability.id, charge);
    }
    /**
     * Charge specified ability to a specific
     * @param playerUuid Player to charge ability for
     * @param ability Ability to charge
     * @return if the ability could be charged
     */
    public boolean chargeSetAbility(UUID playerUuid, Identifier abilityId, int charge) {
        if (!hasAbility(playerUuid, abilityId)) {
            return false;
        }
        AbstractAbility ability = getAbility(playerUuid, abilityId);
        if (!(ability instanceof AbstractChargedAbility)) {
            return false;
        }
        ((AbstractChargedAbility) ability).setCharge(charge);
        return true;
    }
    
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        for (UUID playerUuid : playerAbilities.keySet()) {
            HashMap<String, AbstractAbility> abilities = playerAbilities.get(playerUuid);
            NbtList abilitiesNbt = new NbtList();
            for (AbstractAbility ability : abilities.values()) {
                abilitiesNbt.add(ability.toNbt());
            }
            nbt.put(playerUuid.toString(), abilitiesNbt);
        }
        return nbt;
    }

    public static AbilityManager createFromNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        AbilityManager manager = new AbilityManager();

        for (String uuidString : nbt.getKeys()) {
            NbtList abilitiesNbt = nbt.getList(uuidString, NbtList.COMPOUND_TYPE);
            UUID playerUuid = UUID.fromString(uuidString);
            HashMap<String, AbstractAbility> abilities = new HashMap<String, AbstractAbility>();
            manager.playerAbilities.put(playerUuid, abilities);

            for (int i = 0; i < abilitiesNbt.size(); i++) {
                NbtCompound abilityNbt = abilitiesNbt.getCompound(i);
                String abilityId = abilityNbt.getString("id");
                AbstractAbility ability = PJOAbilities.getAbility(abilityId).instance();
                ability.setPlayerUuid(playerUuid);
                ability.fromNbt(nbt);

                abilities.put(abilityId, ability);
            }
        }
        return manager;
    }

    /**
     * Update the player entities for all tracked abilities
     */
    public void updatePlayersEntities() {
        if (server == null)
            return;
        PlayerManager playerManager = server.getPlayerManager();
        for (UUID playerUuid : playerAbilities.keySet()) {
            PlayerEntity player = playerManager.getPlayer(playerUuid);
            HashMap<String, AbstractAbility> abilities = playerAbilities.get(playerUuid);

            for (String key : abilities.keySet()) {
                if (player != null)
                    abilities.get(key).setPlayerEntity(player);
                else
                    abilities.get(key).removePlayerEntity();
            }
        }
    }

    public static AbilityManager getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD)
                .getPersistentStateManager();
        INSTANCE = persistentStateManager.getOrCreate(TYPE, NAME);
        INSTANCE.server = server;
        INSTANCE.updatePlayersEntities();
        return INSTANCE;
    }
}
