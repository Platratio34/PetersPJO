package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CelestialBronzeSpear extends CelestialSpear implements TooltipSupplier {

    public static final String NAME = "celestial_bronze_spear";
    public static final Identifier ID = PJO.id(NAME);

    public static final CelestialBronzeSpear ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeSpear(PJOMaterials.CELESTIAL_BRONZE_MATERIAL, 7, -3f, new Item.Settings()));

    public static void init() {}

    public CelestialBronzeSpear(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, ID);
    }

    @Override
    public void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> tooltip) {

        // default white text
        tooltip.add(PJO.tooltip("item", NAME).formatted(Formatting.GOLD));
    }
    
    @Override
    public ItemStack getDefaultStack() {
        return new ItemStack(ITEM);
    }

}
