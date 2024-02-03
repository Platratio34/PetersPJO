package com.peter.peterspjo.blocks;

import java.util.List;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.TooltipedBlock;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.SandBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class UnderworldSandDarkBlock extends SandBlock {

    public static String NAME = "underworld_sand_dark";
    public static Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static UnderworldSandDarkBlock BLOCK = new UnderworldSandDarkBlock(FabricBlockSettings.copy(Blocks.SAND));
    public static final BlockItem ITEM = new BlockItem(BLOCK, new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.BLOCK, ID, BLOCK);
        Registry.register(Registries.ITEM, ID, ITEM);
    }

    public UnderworldSandDarkBlock(Settings settings) {
        super(0x303030, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block."+PJO.NAMESPACE+"."+NAME+".tooltip"));
    }

}
