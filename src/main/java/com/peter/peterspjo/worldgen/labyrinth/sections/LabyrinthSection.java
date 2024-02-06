package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;

public abstract class LabyrinthSection {

    public static final Block DEFAULT_BLOCK = Blocks.STONE;
    public static final Block DEFAULT_AIR = Blocks.AIR;

    public static final int FLOOR_HEIGHT = 1;
    public static final int CELLING_HEIGHT = 5;
    public static final int CORRIDOR_MIN = 6;
    public static final int CORRIDOR_MAX = 9;

    public abstract BlockState sample(int sectionX, int sectionY, int sectionZ, Direction orientation,
            LabyrinthMaterialSet set, LabyrinthMaterialSet nSet, LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet,
            LabyrinthMaterialSet wSet);
    
    public abstract boolean canConnect(Direction orientation, Direction direction);

    public static final LabyrinthSection STRAIGHT = new Straight();
    public static final LabyrinthSection CROSS = new Cross();
}
