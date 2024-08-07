package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CelestialBronzeSword extends CelestialSword {

    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, "celestial_bronze_sword");

    public static final CelestialBronzeSword ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeSword(
            new Item.Settings()));

    public static void init() {}
    
    public CelestialBronzeSword(Item.Settings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 6, -2f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {

        // default white text
        tooltip.add(Text.translatable("item.peterspjo.celestial_bronze_sword.tooltip").formatted(Formatting.GOLD));
    }

}
