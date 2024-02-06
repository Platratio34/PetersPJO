package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
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
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet, NoiseGenerator noise) {
        int c1 = sectionX;
        int c2 = sectionZ;
        if (orientation == Direction.EAST || orientation == Direction.WEST) {
            c1 = sectionZ;
            c1 = sectionX;
        }

        boolean rand = noise.noise(sectionX,sectionY,sectionZ) > 0.2;
        LabyrinthMaterialSet useSet = set;
        if (c1 == 0) {
            if (sectionY % 2 == 0 && c2 % 2 == 0) {
                useSet = nSet;
            }
        } else if (c1 == 15) {
            if (sectionY % 2 == 0 && c2 % 2 == 0) {
                useSet = sSet;
            }
        } else if (c2 == 0) {
            if (sectionY % 2 == 0 && c1 % 2 == 0) {
                useSet = eSet;
            }
        } else if (c2 == 15) {
            if (sectionY % 2 == 0 && c1 % 2 == 0) {
                useSet = wSet;
            }
        }

        if (c1 < CORRIDOR_N_MIN || c1 > CORRIDOR_N_MAX) {
            return DEFAULT_BLOCK.getDefaultState();
        } else if (c1 == CORRIDOR_N_MIN || c1 == CORRIDOR_N_MAX) {
            return useSet.getWall(rand).getDefaultState();
        } else if (sectionY == FLOOR_HEIGHT) {
            return useSet.getFloor(rand).getDefaultState();
        } else if (sectionY == CELLING_HEIGHT) {
            return useSet.getCelling(rand).getDefaultState();
        } else if (sectionY > FLOOR_HEIGHT || sectionY < CELLING_HEIGHT) {
            return DEFAULT_AIR.getDefaultState();
        }

        return DEFAULT_BLOCK.getDefaultState();
    }

}
