package com.peter.peterspjo.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class TooltipedBlock extends Block {

    private Text tooltip;

    public TooltipedBlock(Settings settings, Text tooltip) {
        super(settings);
        this.tooltip = tooltip;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(this.tooltip);
    }

}
