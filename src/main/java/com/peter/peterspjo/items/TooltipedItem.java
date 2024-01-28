package com.peter.peterspjo.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class TooltipedItem extends Item {

    private Text tooltip;
    
    public TooltipedItem(Settings settings, Text tooltip) {
        super(settings);
        this.tooltip = tooltip;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // default white text
        tooltip.add(this.tooltip);
    }

}
