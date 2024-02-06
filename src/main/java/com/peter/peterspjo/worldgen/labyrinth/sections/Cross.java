package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
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
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet, NoiseGenerator noise) {
        

        boolean rand = noise.noise(sectionX, sectionY, sectionZ) > 0.2;
        LabyrinthMaterialSet useSet = set;
        if (sectionX == 0) {
            if (sectionY % 2 == 0 && sectionZ % 2 == 0) {
                useSet = nSet;
            }
        } else if (sectionX == 15) {
            if (sectionY % 2 == 0 && sectionZ % 2 == 0) {
                useSet = sSet;
            }
        } else if (sectionZ == 0) {
            if (sectionY % 2 == 0 && sectionX % 2 == 0) {
                useSet = eSet;
            }
        } else if (sectionZ == 15) {
            if (sectionY % 2 == 0 && sectionX % 2 == 0) {
                useSet = wSet;
            }
        }
                
        if ((sectionX < CORRIDOR_N_MIN || sectionX > CORRIDOR_N_MAX)
                && (sectionZ < CORRIDOR_N_MIN || sectionZ > CORRIDOR_N_MAX)) {
            if (sectionY == CELLING_HEIGHT + 1) {
                return useSet.getCelling(rand).getDefaultState();
            } else if (sectionY == CELLING_HEIGHT) {
                return DEFAULT_AIR.getDefaultState();
            }
        }

        if ((sectionX < CORRIDOR_N_MIN || sectionX > CORRIDOR_N_MAX)
                && (sectionZ < CORRIDOR_N_MIN || sectionZ > CORRIDOR_N_MAX)) {
            return DEFAULT_BLOCK.getDefaultState();
        } else if ((sectionX == CORRIDOR_N_MIN || sectionX == CORRIDOR_N_MAX)
                && (sectionZ < CORRIDOR_N_MIN || sectionZ > CORRIDOR_N_MAX)) {
            return useSet.getWall(rand).getDefaultState();
        } else if ((sectionZ == CORRIDOR_N_MIN || sectionZ == CORRIDOR_N_MAX)
                && (sectionX < CORRIDOR_N_MIN || sectionX > CORRIDOR_N_MAX)) {
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