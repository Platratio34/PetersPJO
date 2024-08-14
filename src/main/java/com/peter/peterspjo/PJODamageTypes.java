package com.peter.peterspjo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class PJODamageTypes extends DamageSources {

    public PJODamageTypes(DynamicRegistryManager registryManager) {
        super(registryManager);
    }

    public static final RegistryKey<DamageType> STYX = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, PJO.id("styx"));

    public static final RegistryKey<DamageType> CELESTIAL_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            PJO.id("celestial"));
    
    public DamageSource celestial(Entity attacker) {
        return this.create(CELESTIAL_DAMAGE_TYPE, attacker);
    }
}
