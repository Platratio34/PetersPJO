package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CelestialBronzeDagger extends CelestialSword {

    public static final String NAME = "celestial_bronze_dagger";
    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);

    public static final CelestialBronzeDagger ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeDagger(
            new Item.Settings()));

    public static void init() {}

    public CelestialBronzeDagger(Item.Settings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 4, 0.3f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {

        // default white text
        tooltip.add(Text.translatable("item."+PJO.NAMESPACE+"."+NAME+".tooltip").formatted(Formatting.GOLD));
    }

}
