package com.peter.peterspjo.items;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TooltipedItem extends Item implements TooltipSupplier {

    private Text tooltip;
    
    public TooltipedItem(Settings settings, Text tooltip, Identifier id) {
        super(settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id)));
        this.tooltip = tooltip;
    }

    @Override
    public void addTooltip(ItemStack itemStack, TooltipContext context, TooltipType type, List<Text> tooltip) {

        // default white text
        tooltip.add(this.tooltip);
    }

}
