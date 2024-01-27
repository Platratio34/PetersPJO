package com.peter.peterspjo.blocks;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.TooltipedBlock;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IronBrazier extends TooltipedBlock {

    public static final String NAME = "iron_brazier";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final IronBrazier BLOCK = new IronBrazier(
            FabricBlockSettings.create().strength(4.0f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final BlockItem ITEM = new BlockItem(BLOCK, new FabricItemSettings());
    
    public static void init() {
        Registry.register(Registries.BLOCK, ID, BLOCK);
        Registry.register(Registries.ITEM, ID, ITEM);
    }

    public IronBrazier(Settings settings) {
        super(settings, Text.translatable("block.peterspjo."+NAME+".tooltip"));
    }
}
