package com.peter.peterspjo.items;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class CelestialBronzeMaterial implements ToolMaterial {

    public static final CelestialBronzeMaterial INSTANCE = new CelestialBronzeMaterial();

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
    public int getEnchantability() {
        return 30;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(CelestialBronzeIngot.ITEM);
    }

    @Override
    public TagKey<Block> getInverseTag() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInverseTag'");
    }

}
