package com.peter.peterspjo;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class PJODamageTypes {

    public static final RegistryKey<DamageType> STYX = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(PJO.NAMESPACE, "styx"));
}
