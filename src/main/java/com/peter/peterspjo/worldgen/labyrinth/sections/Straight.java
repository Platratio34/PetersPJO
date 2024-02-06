package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class Straight extends LabyrinthSection {

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, Direction orientation,
            LabyrinthMaterialSet set, LabyrinthMaterialSet nSet, LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet,
            LabyrinthMaterialSet wSet) {
        int c1 = sectionX;
        if (orientation == Direction.EAST || orientation == Direction.WEST) {
            c1 = sectionZ;
        }

        if (c1 < CORRIDOR_MIN || c1 > CORRIDOR_MAX) {
            return DEFAULT_BLOCK.getDefaultState();
        } else if (c1 == CORRIDOR_MIN || c1 == CORRIDOR_MAX) {
            return set.wall.getDefaultState();
        } else if (sectionY == FLOOR_HEIGHT) {
            return set.floor.getDefaultState();
        } else if (sectionY == CELLING_HEIGHT) {
            return set.celling.getDefaultState();
        } else if (sectionY > FLOOR_HEIGHT || sectionY < CELLING_HEIGHT) {
            return DEFAULT_AIR.getDefaultState();
        }
        
        return DEFAULT_BLOCK.getDefaultState();
    }

    @Override
    public boolean canConnect(Direction orientation, Direction direction) {
        if (orientation == direction) {
            return true;
        } else if (orientation == Direction.NORTH) {
            return direction == Direction.SOUTH;
        } else if (orientation == Direction.SOUTH) {
            return direction == Direction.NORTH;
        } else if (orientation == Direction.EAST) {
            return direction == Direction.WEST;
        } else if (orientation == Direction.WEST) {
            return direction == Direction.EAST;
        }
        return false;
    }

}
