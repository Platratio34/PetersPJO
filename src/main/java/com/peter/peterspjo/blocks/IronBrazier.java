package com.peter.peterspjo.blocks;

import com.peter.peterspjo.PJO;

import net.minecraft.item.Item;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IronBrazier extends TooltipedBlock {

    public static final String NAME = "iron_brazier";
    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);
    public static final IronBrazier BLOCK = new IronBrazier(
            AbstractBlock.Settings.create().strength(4.0f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final BlockItem ITEM = new BlockItem(BLOCK, new Item.Settings());
    
    public static void register() {
        Registry.register(Registries.BLOCK, ID, BLOCK);
        Registry.register(Registries.ITEM, ID, ITEM);
    }

    public IronBrazier(Settings settings) {
        super(settings, Text.translatable("block."+PJO.NAMESPACE+"."+NAME+".tooltip"));
    }
}
