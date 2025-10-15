package com.peter.peterspjo.items;

import java.util.List;

import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public interface TooltipSupplier {

    /**
     * Add a tooltip to the item/block
     * @param itemStack Stack the tooltip will be added to
     * @param tooltipContext Tooltip context
     * @param tooltipType Tooltip type
     * @param list Tooltip text
     */
    public default void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType,
            List<Text> list) {

    }
    
    /**
     * Add a tooltip to the item/block (Called after <code>addTooltip</code>)
     * @param itemStack Stack the tooltip will be added to
     * @param tooltipContext Tooltip context
     * @param tooltipType Tooltip type
     * @param list Tooltip text
     * @param entityData Block entity data (if present) the item stores
     */
    public default void addTooltipEntity(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> list, NbtCompound entityData) {

    }
}
