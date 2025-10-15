package com.peter.peterspjo.blocks;

import com.peter.peterspjo.PJO;

import net.minecraft.block.AbstractBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class IronBrazier extends BrazierBlock {

    public static final String NAME = "iron_brazier";
    public static final Identifier ID = PJO.id(NAME);
    public static final IronBrazier BLOCK = new IronBrazier(
            AbstractBlock.Settings.create().strength(4.0f).sounds(BlockSoundGroup.METAL).nonOpaque().registryKey(PJOBlocks.registryKey(ID)));
    public static final BlockItem ITEM = PJOBlocks.registerBlockItem(ID, BLOCK);
    
    public static void register() {
        Registry.register(Registries.BLOCK, ID, BLOCK);
    }

    public IronBrazier(Settings settings) {
        super(settings, NAME);
    }
}
