package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class Straight extends LabyrinthSection {

    public Straight(Direction orientation) {
        super(orientation);
        connections = new ConnectionType[] { ConnectionType.CORRIDOR_NARROW, ConnectionType.WALL,
                ConnectionType.CORRIDOR_NARROW, ConnectionType.WALL };
    }

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet) {
        int c1 = sectionX;
        if (orientation == Direction.EAST || orientation == Direction.WEST) {
            c1 = sectionZ;
        }

        if (c1 < CORRIDOR_N_MIN || c1 > CORRIDOR_N_MAX) {
            return DEFAULT_BLOCK.getDefaultState();
        } else if (c1 == CORRIDOR_N_MIN || c1 == CORRIDOR_N_MAX) {
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

}
