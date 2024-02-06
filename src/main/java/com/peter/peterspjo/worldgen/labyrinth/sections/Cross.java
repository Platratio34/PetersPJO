package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class Cross extends LabyrinthSection {

    public Cross(Direction orientation) {
        super(orientation);
        connections = new ConnectionType[] { ConnectionType.CORRIDOR_NARROW, ConnectionType.CORRIDOR_NARROW,
                ConnectionType.CORRIDOR_NARROW,
                ConnectionType.CORRIDOR_NARROW };
    }

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet) {

        if ((sectionX < CORRIDOR_N_MIN || sectionX > CORRIDOR_N_MAX)
                && (sectionZ < CORRIDOR_N_MIN || sectionZ > CORRIDOR_N_MAX)) {
            if (sectionY == CELLING_HEIGHT + 1) {
                return set.celling.getDefaultState();
            } else if (sectionY == CELLING_HEIGHT) {
                return DEFAULT_AIR.getDefaultState();
            }
        }

        if ((sectionX < CORRIDOR_N_MIN || sectionX > CORRIDOR_N_MAX)
                && (sectionZ < CORRIDOR_N_MIN || sectionZ > CORRIDOR_N_MAX)) {
            return DEFAULT_BLOCK.getDefaultState();
        } else if ((sectionX == CORRIDOR_N_MIN || sectionX == CORRIDOR_N_MAX)
                && (sectionZ < CORRIDOR_N_MIN || sectionZ > CORRIDOR_N_MAX)) {
            return set.wall.getDefaultState();
        } else if ((sectionZ == CORRIDOR_N_MIN || sectionZ == CORRIDOR_N_MAX)
                && (sectionX < CORRIDOR_N_MIN || sectionX > CORRIDOR_N_MAX)) {
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