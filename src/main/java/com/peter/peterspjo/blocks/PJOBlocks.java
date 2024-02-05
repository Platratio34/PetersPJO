package com.peter.peterspjo.blocks;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.blocks.fluids.StyxWater;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PJOBlocks {

    public static final Block CELESTIAL_BRONZE_BLOCK = new TooltipedBlock(FabricBlockSettings.create().strength(4.0f).sounds(BlockSoundGroup.METAL).luminance(1), Text.translatable("block.peterspjo.celestial_bronze_block.tooltip").formatted(Formatting.GOLD));
    public static final Identifier CELESTIAL_BRONZE_BLOCK_ID = new Identifier(PJO.NAMESPACE, "celestial_bronze_block");
    public static final BlockItem CELESTIAL_BRONZE_BLOCK_ITEM = new BlockItem(CELESTIAL_BRONZE_BLOCK,
            new FabricItemSettings());
    
    public static final Block UNDERWORLD_SAND_DARK = UnderworldSandDarkBlock.BLOCK;
    public static final Block IRON_BRAZIER = IronBrazier.BLOCK;
    public static final Block STONE_BRAZIER = StoneBrazier.BLOCK;
    
    public static final FluidBlock STYX_WATER = StyxWater.BLOCK;
    
    public static void init() {
        Registry.register(Registries.BLOCK, CELESTIAL_BRONZE_BLOCK_ID, CELESTIAL_BRONZE_BLOCK);
        Registry.register(Registries.ITEM, CELESTIAL_BRONZE_BLOCK_ID, CELESTIAL_BRONZE_BLOCK_ITEM);

        IronBrazier.register();
        StoneBrazier.register();

        UnderworldSandDarkBlock.register();
    }
}
