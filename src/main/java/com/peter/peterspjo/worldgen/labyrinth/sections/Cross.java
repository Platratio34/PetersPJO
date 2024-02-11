package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class Cross extends LabyrinthSection {

    private static final String ID = "straight";
    private static final ConnectionType[] CONNECTIONS = { ConnectionType.CORRIDOR_NARROW,
            ConnectionType.CORRIDOR_NARROW, ConnectionType.CORRIDOR_NARROW, ConnectionType.CORRIDOR_NARROW, ConnectionType.WALL, ConnectionType.WALL };

    public Cross(Direction orientation, LabyrinthMaterialSet set) {
        super(ID, CONNECTIONS, orientation, set);
    }

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet, NoiseGenerator noise) {

        boolean rand = isRand(sectionX, sectionY, sectionZ, noise);
        LabyrinthMaterialSet useSet = getMaterialSet(sectionX, sectionY, sectionZ, nSet, eSet, sSet, wSet);

        if ((sectionX > CORRIDOR_N_MIN && sectionX < CORRIDOR_N_MAX)
                && (sectionZ > CORRIDOR_N_MIN && sectionZ < CORRIDOR_N_MAX)) {
            if (sectionY == CELLING_HEIGHT + 1) {
                return useSet.getCelling(rand).getDefaultState();
            } else if (sectionY == CELLING_HEIGHT) {    
                return DEFAULT_AIR.getDefaultState();
            }
        }

        if (sectionY < FLOOR_HEIGHT || sectionY > CELLING_HEIGHT) {
            return DEFAULT_BLOCK.getDefaultState();
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
        } else if (sectionY > FLOOR_HEIGHT && sectionY < CELLING_HEIGHT) {
            return DEFAULT_AIR.getDefaultState();
        }

        return DEFAULT_BLOCK.getDefaultState();
    }

}