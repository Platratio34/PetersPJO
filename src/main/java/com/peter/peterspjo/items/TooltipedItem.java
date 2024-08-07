package com.peter.peterspjo.items;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

public class TooltipedItem extends Item {

    private Text tooltip;
    
    public TooltipedItem(Settings settings, Text tooltip) {
        super(settings);
        this.tooltip = tooltip;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {

        // default white text
        tooltip.add(this.tooltip);
    }

}
