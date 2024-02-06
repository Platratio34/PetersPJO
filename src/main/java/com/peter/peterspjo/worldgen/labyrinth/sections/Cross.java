package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class Cross extends LabyrinthSection {

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, Direction orientation,
            LabyrinthMaterialSet set, LabyrinthMaterialSet nSet, LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet,
            LabyrinthMaterialSet wSet) {

        if ((sectionX < CORRIDOR_MIN || sectionX > CORRIDOR_MAX) && (sectionZ < CORRIDOR_MIN || sectionZ > CORRIDOR_MAX)) {
            return DEFAULT_BLOCK.getDefaultState();
        } else if ((sectionX == CORRIDOR_MIN || sectionX == CORRIDOR_MAX) && (sectionZ < CORRIDOR_MIN || sectionZ > CORRIDOR_MAX)) {
            return set.wall.getDefaultState();
        } else if ((sectionZ == CORRIDOR_MIN || sectionZ == CORRIDOR_MAX) && (sectionX < CORRIDOR_MIN || sectionX > CORRIDOR_MAX)) {
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
        return true;
    }

}