package com.peter.peterspjo.blocks;

import java.util.List;

import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

public interface TooltipedBlockItem {

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options);
}
