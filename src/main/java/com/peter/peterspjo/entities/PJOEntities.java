package com.peter.peterspjo.entities;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class PJOEntities {

    public static final EntityType<Empousai> EMPOUSAI = Empousai.TYPE;
    public static final EntityType<Hellhound> HELLHOUND = Hellhound.TYPE;
    public static final EntityType<Pegasus> PEGASUS = Pegasus.TYPE;
    public static final EntityType<Centaur> CENTAUR = Centaur.TYPE;

    public static final ArrayList<EntityType<?>> MONSTERS = new ArrayList<EntityType<?>>();

    public static void init() {
        SpearEntity.init();
        Empousai.register();
        Hellhound.register();
        Pegasus.register();
        Centaur.register();
    }

    protected static <T extends Entity> EntityType<T> register(Identifier id, EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(Registries.ENTITY_TYPE.getKey(), id);
        return Registry.register(Registries.ENTITY_TYPE, id, builder.build(key));
    }
}
