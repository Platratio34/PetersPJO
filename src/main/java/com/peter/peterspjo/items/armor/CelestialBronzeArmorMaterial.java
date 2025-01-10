package com.peter.peterspjo.items.armor;

import java.util.Map;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.PJOItemTags;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;

public class CelestialBronzeArmorMaterial {

    private static final Map<EquipmentType, Integer> PROTECTION_VALUES = Map.of(
            EquipmentType.HELMET, 3,
            EquipmentType.CHESTPLATE, 6,
            EquipmentType.LEGGINGS, 8,
            EquipmentType.BOOTS, 3);

    private static final int DURABILITY_MULTIPLIER = 30;

    private static final String NAME = "celestial_bronze";

    public static final ArmorMaterial MATERIAL = PJOArmorMaterials.registerMaterial(NAME, 20,
            PROTECTION_VALUES, 255,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, PJOItemTags.CELESTIAL_BRONZE_MATERIALS, 2, 0);

    public static final ArmorItem HELMET = Registry.register(Registries.ITEM,
            PJO.id(NAME + "_helmet"),
            new ArmorItem(MATERIAL, EquipmentType.HELMET,
                    new Item.Settings().maxDamage(EquipmentType.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));
    public static final ArmorItem CHESTPLATE = Registry.register(Registries.ITEM,
            PJO.id(NAME + "_chestplate"),
            new ArmorItem(MATERIAL, EquipmentType.CHESTPLATE,
                    new Item.Settings().maxDamage(EquipmentType.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));
    public static final ArmorItem LEGGINGS = Registry.register(Registries.ITEM,
            PJO.id(NAME + "_leggings"),
            new ArmorItem(MATERIAL, EquipmentType.LEGGINGS,
                    new Item.Settings().maxDamage(EquipmentType.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));
    public static final ArmorItem BOOTS = Registry.register(Registries.ITEM,
            PJO.id(NAME + "_boots"),
            new ArmorItem(MATERIAL, EquipmentType.BOOTS,
                    new Item.Settings().maxDamage(EquipmentType.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));

    public static void init() {
    }

}
