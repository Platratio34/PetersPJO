package com.peter.peterspjo.abilities;

import com.peter.peterspjo.PJO;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class PJOAbilities {

    private static final AbstractAbility NONE_ABILITY = new NoneAbility();

    public static final RegistryKey<Registry<AbstractAbility>> ABILITIES_REGISTRY_KEY = RegistryKey
            .ofRegistry(PJO.id("abilities"));
    /** Registry of Abilities */
    public static final Registry<AbstractAbility> ABILITIES_REGISTRY = Registries.create(ABILITIES_REGISTRY_KEY, (registry) -> {
            return NONE_ABILITY;
    });

    public static void init() {
        register(new PoseidonAbility());
    }

    /**
     * Register a new Ability
     * @param ability Ability to register
     */
    public static void register(AbstractAbility ability) {
        Registry.register(ABILITIES_REGISTRY, ability.id, ability);
    }

    /**
     * Get Ability from registry by ID
     * @param abilityID Ability type ID
     * @return Ability type
     */
    public static AbstractAbility getAbility(Identifier abilityID) {
        return ABILITIES_REGISTRY.get(abilityID);
    }
}
