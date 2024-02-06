package com.peter.peterspjo.worldgen.labyrinth;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class LabyrinthMaterials {

    public static final LabyrinthMaterialSet DEFAULT = new LabyrinthMaterialSet(3, Blocks.STONE);

    public static final LabyrinthMaterialSet[] MATERIALS = {
        new LabyrinthMaterialSet(3, Blocks.STONE),
        new LabyrinthMaterialSet(3, Blocks.COBBLESTONE),
        new LabyrinthMaterialSet(3, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE),
        new LabyrinthMaterialSet(1, Blocks.STONE_BRICKS),
        new LabyrinthMaterialSet(1, Blocks.STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS),
        new LabyrinthMaterialSet(1, Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS),
        new LabyrinthMaterialSet(1, Blocks.STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS),
        new LabyrinthMaterialSet(1, Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS),
        new LabyrinthMaterialSet(1, Blocks.STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS),
        new LabyrinthMaterialSet(6, Blocks.BRICKS),
        new LabyrinthMaterialSet(3, Blocks.MUD_BRICKS),
    };

    public static final int[] WEIGHTED_MATERIAL_INDEXES = getMaterialSetsWeighted();

    private static int[] getMaterialSetsWeighted() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < MATERIALS.length; i++) {
            for (int j = 1; j < MATERIALS[i].freq; j++) {
                list.add(i);
            }
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static class LabyrinthMaterialSet {

        public final int freq;

        /** Floor block */
        public final Block floor;
        /** Random replace floor block */
        public final Block floor_rand;
        /** Wall block */
        public final Block wall;
        /** Random replace wall block */
        public final Block wall_rand;
        /** Celling block */
        public final Block celling;
        /** Random replace celling block */
        public final Block celling_rand;
        /** Accent block */
        public final Block accent;

        public LabyrinthMaterialSet(int freq, Block block) {
            this.freq = freq;
            this.floor = block;
            this.floor_rand = block;
            this.wall = block;
            this.wall_rand = block;
            this.celling = block;
            this.celling_rand = block;
            this.accent = block;
        }

        public LabyrinthMaterialSet(int freq, Block block, Block rand) {
            this.freq = freq;
            this.floor = block;
            this.floor_rand = rand;
            this.wall = block;
            this.wall_rand = rand;
            this.celling = block;
            this.celling_rand = rand;
            this.accent = block;
        }
        public LabyrinthMaterialSet(int freq, Block floor, Block wall, Block celling) {
            this.freq = freq;
            this.floor = floor;
            this.floor_rand = floor;
            this.wall = wall;
            this.wall_rand = wall;
            this.celling = celling;
            this.celling_rand = celling;
            this.accent = wall;
        }
        public LabyrinthMaterialSet(int freq, Block floor, Block wall, Block celling, Block accent) {
            this.freq = freq;
            this.floor = floor;
            this.floor_rand = floor;
            this.wall = wall;
            this.wall_rand = wall;
            this.celling = celling;
            this.celling_rand = celling;
            this.accent = accent;
        }
        public LabyrinthMaterialSet(int freq, Block floor, Block wall, Block celling, Block accent, Block floor_rand, Block wall_rand, Block celling_rand) {
            this.freq = freq;
            this.floor = floor;
            this.floor_rand = floor_rand;
            this.wall = wall;
            this.wall_rand = wall_rand;
            this.celling = celling;
            this.celling_rand = celling_rand;
            this.accent = accent;
        }
    }
}
