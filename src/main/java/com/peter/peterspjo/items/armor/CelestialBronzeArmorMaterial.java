package com.peter.peterspjo.items.armor;

import net.minecraft.item.ArmorItem.Type;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.CelestialBronzeIngot;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class CelestialBronzeArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = { 13, 15, 16, 11 };
    private static final int[] PROTECTION_VALUES = { 3, 6, 8, 3 };

    public static final CelestialBronzeArmorMaterial MATERIAL = new CelestialBronzeArmorMaterial();

    public static final ArmorItem HELMET = new ArmorItem(MATERIAL, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem CHESTPLATE = new ArmorItem(MATERIAL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem LEGGINGS = new ArmorItem(MATERIAL, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem BOOTS = new ArmorItem(MATERIAL, ArmorItem.Type.BOOTS, new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(PJO.NAMESPACE, "celestial_bronze_helmet"), HELMET);
        Registry.register(Registries.ITEM, new Identifier(PJO.NAMESPACE, "celestial_bronze_chestplate"), CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(PJO.NAMESPACE, "celestial_bronze_leggings"), LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(PJO.NAMESPACE, "celestial_bronze_boots"), BOOTS);
    }

    @Override
    public int getDurability(Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * 33;
    }

    @Override
    public int getProtection(Type type) {
        return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 255;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(CelestialBronzeIngot.ITEM);
    }

    @Override
    public String getName() {
        return "peterspjo:celestial_bronze";
    }

    @Override
    public float getToughness() {
        return 2;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

}
