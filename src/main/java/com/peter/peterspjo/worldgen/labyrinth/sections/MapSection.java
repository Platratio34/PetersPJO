package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class MapSection extends LabyrinthSection {

    public static final int AIR = 0;
    public static final int SOLID = 1;
    public static final int FLOOR = 2;
    public static final int WALL = 3;
    public static final int CELLING = 4;
    public static final int ACCENT = 5;
    public static final int LIGHT = 6;

    protected int[][][] map;

    public MapSection(Direction orientation, int[][][] map, ConnectionType[] connections) {
        super(orientation);
        this.map = map;
        this.connections = connections;
    }

    @Override
    public BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet, NoiseGenerator noise) {
        
        boolean rand = isRand(sectionX, sectionY, sectionZ, noise);
        LabyrinthMaterialSet useSet = getMaterialSet(sectionX, sectionY, sectionZ, nSet, eSet, sSet, wSet);

        int x = sectionX;
        int z = sectionZ;
        switch (orientation) {
            case NORTH:
                break;
            case EAST:
                x = 15 - sectionZ;
                z = sectionX;
                break;
            case SOUTH:
                x = 15 - sectionX;
                z = 15 - sectionZ;
                break;
            case WEST:
                x = sectionZ;
                z = 15 - sectionX;
                break;

            default:
                break;
        }
        

        int bt = map[sectionY][z][x];
        switch (bt) {
            case AIR:
                return DEFAULT_AIR.getDefaultState();
            case SOLID:
                return DEFAULT_BLOCK.getDefaultState();
            case FLOOR:
                return useSet.getFloor(rand).getDefaultState();
            case WALL:
                return useSet.getWall(rand).getDefaultState();
            case CELLING:
                return useSet.getCelling(rand).getDefaultState();
            case ACCENT:
                return useSet.getAccent().getDefaultState();
            case LIGHT:
                return DEFAULT_LIGHT.getDefaultState();
        
            default:
                return DEFAULT_BLOCK.getDefaultState();
        }
    }

}
