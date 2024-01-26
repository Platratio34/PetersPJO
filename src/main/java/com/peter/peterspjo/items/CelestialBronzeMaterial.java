package com.peter.peterspjo.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class CelestialBronzeMaterial implements ToolMaterial {

    public static final CelestialBronzeMaterial INSTNACE = new CelestialBronzeMaterial();

    @Override
    public int getDurability() {
        return 500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1.0f;
    }

    @Override
    public float getAttackDamage() {
        return 0f;
    }

    @Override
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 30;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(CelestialBronzeIngot.ITEM);
    }


}
