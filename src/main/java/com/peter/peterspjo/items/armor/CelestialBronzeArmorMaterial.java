package com.peter.peterspjo.items.armor;

import java.util.Map;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.PJOItemTags;
import com.peter.peterspjo.items.PJOItems;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class CelestialBronzeArmorMaterial {

    public static final int BASE_DURABILITY = 15;

    public static final String NAME = "celestial_bronze";
    public static final Identifier MATERIAL_ID = PJO.id(NAME);
    public static final RegistryKey<EquipmentAsset> ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, MATERIAL_ID);
    
    public static final ArmorMaterial MATERIAL_INSTANCE = new ArmorMaterial(
        BASE_DURABILITY, // durability
        Map.of( // defence
                EquipmentType.HELMET, 3,
                EquipmentType.CHESTPLATE, 8,
                EquipmentType.LEGGINGS, 6,
                EquipmentType.BOOTS, 3
        ),
        5, // enchantment value
        SoundEvents.ITEM_ARMOR_EQUIP_IRON, // equip sound
        2f, // toughness
        0.0f, // nockback resistance
        PJOItemTags.CELESTIAL_BRONZE_MATERIALS, // repair ingreident
        ARMOR_MATERIAL_KEY
    );

    public static final Identifier HELMET_ID = PJO.id(NAME + "_helmet");
    public static final Item HELMET = register(EquipmentType.HELMET, HELMET_ID);
    public static final Identifier CHESTPLATE_ID = PJO.id(NAME + "_chestplate");
    public static final Item CHESTPLATE = register(EquipmentType.CHESTPLATE, CHESTPLATE_ID);
    public static final Identifier LEGGINGS_ID = PJO.id(NAME + "_leggings");
    public static final Item LEGGINGS = register(EquipmentType.LEGGINGS, LEGGINGS_ID);
    public static final Identifier BOOTS_ID = PJO.id(NAME + "_boots");
    public static final Item BOOTS = register(EquipmentType.BOOTS, BOOTS_ID);

    private static Item register(EquipmentType type, Identifier id) {
        return PJOItems.registerItem(id,
            new Item(new Item.Settings().armor(MATERIAL_INSTANCE, type).maxDamage(type.getMaxDamage(BASE_DURABILITY)).registryKey(PJOItems.registryKey(id))));
    }

    public static void init() {
    }

}
