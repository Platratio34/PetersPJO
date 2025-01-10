package com.peter.peterspjo.items.armor;

import java.util.Map;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PJOArmorMaterials {

    public static void init() {
        CelestialBronzeArmorMaterial.init();
    }

    static RegistryKey<? extends Registry<EquipmentAsset>> EQUIPMENT_ASSET_REGISTRY = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));

    public static ArmorMaterial registerMaterial(String id, int durability, Map<EquipmentType, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, TagKey<Item> repairIngredient, float toughness, float knockbackResistance) {
        Identifier identifier = PJO.id(id);
        RegistryKey<EquipmentAsset> assetId = RegistryKey.of(EQUIPMENT_ASSET_REGISTRY, identifier);

        ArmorMaterial material = new ArmorMaterial(durability, defensePoints, enchantability, equipSound, toughness,
                knockbackResistance, repairIngredient, assetId);
        
        return material;
    }
}
