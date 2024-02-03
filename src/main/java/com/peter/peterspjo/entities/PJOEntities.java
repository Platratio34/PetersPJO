package com.peter.peterspjo.entities;

import java.util.ArrayList;

import net.minecraft.entity.EntityType;

public class PJOEntities {

    public static final EntityType<Empousai> EMPOUSAI = Empousai.TYPE;

    public static final ArrayList<EntityType<?>> MONSTERS = new ArrayList<EntityType<?>>();

    public static void init() {
        SpearEntity.init();
        Empousai.register();
    }
}
