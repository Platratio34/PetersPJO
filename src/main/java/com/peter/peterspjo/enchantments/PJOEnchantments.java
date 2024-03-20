package com.peter.peterspjo.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PJOEnchantments {

    public static final Enchantment CELESTIAL = Registry.register(Registries.ENCHANTMENT, CelestialEnchantment.ID,
            new CelestialEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

    public static void init() {
    }
    
    public static void disenchant(ItemStack stack, String enchantment) {
        NbtList enchantments = stack.getEnchantments();
        NbtList enchantments2 = enchantments.copy();
        for (NbtElement nbtElement : enchantments2) {
            NbtCompound ench = (NbtCompound) nbtElement;
            if (ench.get("id").asString().equals(enchantment)) {
                enchantments.remove(nbtElement);
            }
        }
    }

    public static void disenchant(ItemStack stack, Identifier enchantment) {
        disenchant(stack, enchantment.toString());
    }
    
    public static boolean has(ItemStack stack, String enchantment) {
        NbtList enchantments = stack.getEnchantments();
        for (NbtElement nbtElement : enchantments) {
            NbtCompound ench = (NbtCompound) nbtElement;
            if (ench.get("id").asString().equals(enchantment)) {
                return true;
            }
        }
        return false;
    }
    public static boolean has(ItemStack stack, Identifier enchantment) {
        return has(stack, enchantment.toString());
    }
}
