package com.peter.peterspjo.items.armor;

import java.util.Map;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.CelestialBronzeIngot;

import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class CelestialBronzeArmorMaterial {

    private static final Map<ArmorItem.Type, Integer> PROTECTION_VALUES = Map.of(
            ArmorItem.Type.HELMET, 3,
            ArmorItem.Type.CHESTPLATE, 6,
            ArmorItem.Type.LEGGINGS, 8,
            ArmorItem.Type.BOOTS, 3);

    private static final int DURABILITY_MULTIPLIER = 30;

    private static final String NAME = "celestial_bronze";

    public static final RegistryEntry<ArmorMaterial> MATERIAL = PJOArmorMaterials.registerMaterial(NAME,
            PROTECTION_VALUES, 255,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, CelestialBronzeArmorMaterial::getRepairIngredient, 2, 0, false);

    public static final ArmorItem HELMET = Registry.register(Registries.ITEM,
            Identifier.of(PJO.NAMESPACE, NAME + "_helmet"),
            new ArmorItem(MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));
    public static final ArmorItem CHESTPLATE = Registry.register(Registries.ITEM,
            Identifier.of(PJO.NAMESPACE, NAME + "_chestplate"),
            new ArmorItem(MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));
    public static final ArmorItem LEGGINGS = Registry.register(Registries.ITEM,
            Identifier.of(PJO.NAMESPACE, NAME + "_leggings"),
            new ArmorItem(MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));
    public static final ArmorItem BOOTS = Registry.register(Registries.ITEM,
            Identifier.of(PJO.NAMESPACE, NAME + "_boots"),
            new ArmorItem(MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(DURABILITY_MULTIPLIER))));

    public static void init() {
    }

    private static Ingredient getRepairIngredient() {
        return Ingredient.ofItems(CelestialBronzeIngot.ITEM);
    }

}
