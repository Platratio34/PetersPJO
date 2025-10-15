package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;

public class MapSection extends LabyrinthSection {

    /** Map value for air blocks */
    public static final int AR = 0;
    /** Map value for solid (fill) blocks */
    public static final int SD = 1;
    /** Map value for floor blocks */
    public static final int FL = 2;
    /** Map value for wall blocks */
    public static final int WL = 3;
    /** Map value for celling blocks */
    public static final int CL = 4;
    /** Map value for accent blocks */
    public static final int AC = 5;
    /** Map value for light blocks */
    public static final int LI = 6;

    public static final int DN = 10;
    public static final int DS = 11;
    public static final int DW = 12;
    public static final int DE = 13;

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
            case NORTH -> {
            }
            case EAST -> {
                x = 15 - sectionZ;
                z = sectionX;
            }
            case SOUTH -> {
                x = 15 - sectionX;
                z = 15 - sectionZ;
            }
            case WEST -> {
                x = sectionZ;
                z = 15 - sectionX;
            }

            default -> {
            }
        }
        

        int bt = map[sectionY][z][x];
        if (bt >= DN && bt <= DN + 4) {
            Direction doorDir = Direction.byId(bt - DN + 2);
            if (null != orientation)
                switch (orientation) {
                    case WEST -> doorDir = doorDir.rotateClockwise(Axis.Y);
                    case SOUTH -> doorDir = doorDir.getOpposite();
                    case EAST -> doorDir = doorDir.rotateCounterclockwise(Axis.Y);
                    default -> {
                }
            }
            BlockState door = DEFAULT_DOOR.getDefaultState();
            if (map[sectionY - 1][z][x] == bt) {
                door = door.with(DoorBlock.HALF, DoubleBlockHalf.UPPER);
            } else {
                door = door.with(DoorBlock.HALF, DoubleBlockHalf.LOWER);
            }
            return door.with(DoorBlock.FACING, doorDir);
        }
        return switch (bt) {
            case AR -> DEFAULT_AIR.getDefaultState();
            case SD -> DEFAULT_BLOCK.getDefaultState();
            case FL -> useSet.getFloor(rand).getDefaultState();
            case WL -> useSet.getWall(rand).getDefaultState();
            case CL -> useSet.getCelling(rand).getDefaultState();
            case AC -> useSet.getAccent().getDefaultState();
            case LI -> DEFAULT_LIGHT.getDefaultState();
            default -> DEFAULT_BLOCK.getDefaultState();
        };
    }

}
