package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class Empty extends LabyrinthSection {

    private static final String ID = "empty";
    private static final ConnectionType[] CONNECTIONS = { ConnectionType.WALL, ConnectionType.WALL, ConnectionType.WALL,
            ConnectionType.WALL, ConnectionType.WALL, ConnectionType.WALL };

    public Empty(Direction orientation, LabyrinthMaterialSet set) {
        super(ID, CONNECTIONS, orientation, set);
    }

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet, NoiseGenerator noise) {
        return DEFAULT_BLOCK.getDefaultState();
    }

}
