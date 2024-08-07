package com.peter.peterspjo.blocks;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;

public class UnderworldSandDarkBlock extends ColoredFallingBlock {

    public static String NAME = "underworld_sand_dark";
    public static Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);
    public static UnderworldSandDarkBlock BLOCK = new UnderworldSandDarkBlock(AbstractBlock.Settings.copy(Blocks.SAND));
    public static final BlockItem ITEM = new BlockItem(BLOCK, new Item.Settings());

    public static void register() {
        Registry.register(Registries.BLOCK, ID, BLOCK);
        Registry.register(Registries.ITEM, ID, ITEM);
    }

    public UnderworldSandDarkBlock(Settings settings) {
        super(new ColorCode(0x303030), settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("block."+PJO.NAMESPACE+"."+NAME+".tooltip"));
    }

}
