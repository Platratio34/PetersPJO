package com.peter.peterspjo.worldgen.labyrinth;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class LabyrinthMaterials {

    /** Default material set */
    public static final LabyrinthMaterialSet DEFAULT = new LabyrinthMaterialSet(3, "stone", Blocks.STONE);

    /** Array of all material sets */
    public static final LabyrinthMaterialSet[] MATERIALS = {
            new LabyrinthMaterialSet(3, "stone", Blocks.STONE),
            new LabyrinthMaterialSet(3, "cobblestone", Blocks.COBBLESTONE),
            new LabyrinthMaterialSet(3, "cobblestone_mossy", Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE),
            new LabyrinthMaterialSet(1, "stone_bricks", Blocks.STONE_BRICKS),
            new LabyrinthMaterialSet(1, "stone_bricks_chiseled", Blocks.STONE_BRICKS, Blocks.STONE_BRICKS,
                    Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS),
            new LabyrinthMaterialSet(1, "stone_bricks_mossy", Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS),
            new LabyrinthMaterialSet(1, "stone_bricks_mossy_chiseled", Blocks.STONE_BRICKS, Blocks.STONE_BRICKS,
                    Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS,
                    Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS),
            new LabyrinthMaterialSet(1, "stone_bricks_cracked", Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS),
            new LabyrinthMaterialSet(1, "stone_bricks_cracked_chiseled", Blocks.STONE_BRICKS, Blocks.STONE_BRICKS,
                    Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
                    Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS),
            new LabyrinthMaterialSet(6, "bricks", Blocks.BRICKS),
            new LabyrinthMaterialSet(3, "mud_bricks", Blocks.MUD_BRICKS),
    };

    /** Material set IDs with weighted frequency */
    public static final String[] WEIGHTED_MATERIAL_ID = getMaterialSetsWeighted();
    /** Map of all material sets by ID */
    public static final HashMap<String, LabyrinthMaterialSet> MATERIALS_BY_ID = new HashMap<String, LabyrinthMaterialSet>();

    static {
        for (int i = 0; i < MATERIALS.length; i++) {
            MATERIALS_BY_ID.put(MATERIALS[i].id, MATERIALS[i]);
        }
    }

    private static String[] getMaterialSetsWeighted() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < MATERIALS.length; i++) {
            for (int j = 1; j < MATERIALS[i].freq; j++) {
                list.add(MATERIALS[i].id);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /** Set of materials for a section of the labyrinth */
    public static class LabyrinthMaterialSet {

        public final int freq;

        /** Floor block */
        protected final Block floor;
        /** Random replace floor block */
        protected final Block floor_rand;
        /** Wall block */
        protected final Block wall;
        /** Random replace wall block */
        protected final Block wall_rand;
        /** Celling block */
        protected final Block celling;
        /** Random replace celling block */
        protected final Block celling_rand;
        /** Accent block */
        protected final Block accent;

        public final String id;

        /**
         * Create a new material set
         * 
         * @param freq  frequency of set. Number of instances in weighted list
         * @param id    <b>unique</b> id for material set
         * @param block block for all parts of material set
         */
        public LabyrinthMaterialSet(int freq, String id, Block block) {
            this.freq = freq;
            this.id = id;
            this.floor = block;
            this.floor_rand = block;
            this.wall = block;
            this.wall_rand = block;
            this.celling = block;
            this.celling_rand = block;
            this.accent = block;
        }

        /**
         * Create a new material set
         * 
         * @param freq  frequency of set. Number of instances in weighted list
         * @param id    <b>unique</b> id for material set
         * @param block block for all parts of material set
         * @param rand  random replacement block for all parts of material set
         */
        public LabyrinthMaterialSet(int freq, String id, Block block, Block rand) {
            this.freq = freq;
            this.id = id;
            this.floor = block;
            this.floor_rand = rand;
            this.wall = block;
            this.wall_rand = rand;
            this.celling = block;
            this.celling_rand = rand;
            this.accent = block;
        }

        /**
         * Create a new material set
         * 
         * @param freq    frequency of set. Number of instances in weighted list
         * @param id      <b>unique</b> id for material set
         * @param floor   block for section floor
         * @param wall    block for section walls
         * @param celling block for section celling
         */
        public LabyrinthMaterialSet(int freq, String id, Block floor, Block wall, Block celling) {
            this.freq = freq;
            this.id = id;
            this.floor = floor;
            this.floor_rand = floor;
            this.wall = wall;
            this.wall_rand = wall;
            this.celling = celling;
            this.celling_rand = celling;
            this.accent = wall;
        }

        /**
         * Create a new material set
         * 
         * @param freq    frequency of set. Number of instances in weighted list
         * @param id      <b>unique</b> id for material set
         * @param floor   block for section floor
         * @param wall    block for section walls
         * @param celling block for section celling
         * @param accent  block for section accents
         */
        public LabyrinthMaterialSet(int freq, String id, Block floor, Block wall, Block celling, Block accent) {
            this.freq = freq;
            this.id = id;
            this.floor = floor;
            this.floor_rand = floor;
            this.wall = wall;
            this.wall_rand = wall;
            this.celling = celling;
            this.celling_rand = celling;
            this.accent = accent;
        }

        /**
         * Create a new material set
         * 
         * @param freq         frequency of set. Number of instances in weighted list
         * @param id           <b>unique</b> id for material set
         * @param floor        block for section floor
         * @param wall         block for section walls
         * @param celling      block for section celling
         * @param accent       block for section accents
         * @param floor_rand   random replacement block for floor
         * @param wall_rand    random replacement block for walls
         * @param celling_rand random replacement block for celling
         */
        public LabyrinthMaterialSet(int freq, String id, Block floor, Block wall, Block celling, Block accent,
                Block floor_rand, Block wall_rand, Block celling_rand) {
            this.freq = freq;
            this.id = id;
            this.floor = floor;
            this.floor_rand = floor_rand;
            this.wall = wall;
            this.wall_rand = wall_rand;
            this.celling = celling;
            this.celling_rand = celling_rand;
            this.accent = accent;
        }

        /**
         * Get the floor block, or random replacement
         * 
         * @param rand if it should be the random replacement block
         * @return Floor block
         */
        public Block getFloor(boolean rand) {
            if (rand) {
                return floor_rand;
            }
            return floor;
        }

        /**
         * Get the wall block, or random replacement
         * 
         * @param rand if it should be the random replacement block
         * @return Wall block
         */
        public Block getWall(boolean rand) {
            if (rand) {
                return wall_rand;
            }
            return wall;
        }

        /**
         * Get the celling block, or random replacement
         * 
         * @param rand if it should be the random replacement block
         * @return Celling block
         */
        public Block getCelling(boolean rand) {
            if (rand) {
                return celling_rand;
            }
            return celling;
        }

        /**
         * Get the accent block
         * 
         * @return Accent block
         */
        public Block getAccent() {
            return accent;
        }
    }
}
