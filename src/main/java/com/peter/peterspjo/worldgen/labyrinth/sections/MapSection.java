package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class MapSection extends LabyrinthSection {

    /** Map value for air blocks */
    public static final int AIR = 0;
    /** Map value for solid (fill) blocks */
    public static final int SOLID = 1;
    /** Map value for floor blocks */
    public static final int FLOOR = 2;
    /** Map value for wall blocks */
    public static final int WALL = 3;
    /** Map value for celling blocks */
    public static final int CELLING = 4;
    /** Map value for accent blocks */
    public static final int ACCENT = 5;
    /** Map value for light blocks */
    public static final int LIGHT = 6;

    protected int[][][] map;

    /**
     * Create a new map based section
     * @param map map of block types. North oriented
     * @param id map ID <b>MUST BE UNIQUE TO TYPE</b>
     * @param connections connection map for section
     * @param orientation orientation of section instance
     * @param set material set for section instance
     */
    public MapSection(int[][][] map, String id, ConnectionType[] connections, Direction orientation, LabyrinthMaterialSet set) {
        super(id, connections, orientation, set);
        this.map = map;
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
