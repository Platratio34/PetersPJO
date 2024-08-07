package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CelestialBronzeSpear extends CelestialSpear {

    public static final String NAME = "celestial_bronze_spear";
    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);

    public static final CelestialBronzeSpear ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeSpear(CelestialBronzeMaterial.INSTANCE, 7, -3f, new Item.Settings()));

    public static void init() {}

    public CelestialBronzeSpear(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {

        // default white text
        tooltip.add(Text.translatable("item.peterspjo." + NAME + ".tooltip").formatted(Formatting.GOLD));
    }
    
    @Override
    public ItemStack getDefaultStack() {
        return new ItemStack(ITEM);
    }

}
