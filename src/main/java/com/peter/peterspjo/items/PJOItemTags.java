package com.peter.peterspjo.items;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PJOItemTags {

    public static final TagKey<Item> CELESTIAL_BRONZE_MATERIALS = TagKey.of(RegistryKeys.ITEM, PJO.id("celestial_bronze_materials"));
}
