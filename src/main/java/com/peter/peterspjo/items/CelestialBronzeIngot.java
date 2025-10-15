package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CelestialBronzeIngot extends Item implements TooltipSupplier {

    public static final String NAME = "celestial_bronze_ingot";
    public static final Identifier ID = PJO.id(NAME);
    public static final CelestialBronzeIngot ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeIngot(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, ID))));

    public static void init() {}

    public CelestialBronzeIngot(Settings settings) {
        super(settings);
    }

    @Override
    public void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> tooltip) {

        // default white text
        tooltip.add(PJO.tooltip("item", NAME).formatted(Formatting.GOLD));
    }
}
