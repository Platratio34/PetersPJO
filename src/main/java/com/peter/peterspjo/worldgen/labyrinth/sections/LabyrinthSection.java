package com.peter.peterspjo.worldgen.labyrinth.sections;

import com.google.common.base.Function;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;

public abstract class LabyrinthSection {

    public static final Block DEFAULT_BLOCK = Blocks.STONE;
    public static final Block DEFAULT_AIR = Blocks.AIR;

    public static final int FLOOR_HEIGHT = 1;
    public static final int CELLING_HEIGHT = 5;
    public static final int CORRIDOR_N_MIN = 6;
    public static final int CORRIDOR_N_MAX = 9;

    /** Connection types, ordered: north, east, south, west */
    protected ConnectionType[] connections = { ConnectionType.WALL, ConnectionType.WALL, ConnectionType.WALL,
            ConnectionType.WALL };

    /** Orientation of section */
    protected Direction orientation;
    /** Material set for section */
    public LabyrinthMaterialSet set = LabyrinthMaterials.DEFAULT;

    public LabyrinthSection(Direction orientation) {
        this.orientation = orientation;
    }

    /**
     * Get connections for the section
     * 
     * @param orientation Section orientation
     * @return Array of connections, ordered north, east, south, west
     */
    public ConnectionType[] getConnections(Direction orientation) {
        switch (orientation) {
            case NORTH:
                return connections;
            case EAST:
                return new ConnectionType[] { connections[1], connections[2], connections[3], connections[0] };
            case SOUTH:
                return new ConnectionType[] { connections[2], connections[3], connections[0], connections[1] };
            case WEST:
                return new ConnectionType[] { connections[3], connections[0], connections[1], connections[2] };

            default:
                return connections;
        }
    }

    /**
     * Get connections for the section
     * 
     * @return Array of connections, ordered north, east, south, west
     */
    public ConnectionType[] getConnections() {
        return getConnections(orientation);
    }

    /**
     * Get the block at location within the section
     * @param sectionX x (south positive) coordinate
     * @param sectionY y (up positive) coordinate
     * @param sectionZ z (east positive) coordinate
     * @param nSet material set for section to the north (-x)
     * @param eSet material set for section to the east (+z)
     * @param sSet material set for section to the south (+x)
     * @param wSet material set for section to the west (-z)
     * @return block state at location
     */
    public abstract BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet);

    /**
     * If this section could connect to other section in direction from this section
     * @param other section to check against
     * @param direction direction from this section
     * @return if the connection types match on specified face
     */
    public boolean canConnect(LabyrinthSection other, Direction direction) {
        return canConnect(other, other.orientation, direction);
    }

    /**
     * If this section could connect to other section in direction from this section
     * @param other section to check against
     * @param otherOrientation override direction for other section
     * @param direction direction from this section
     * @return if the connection types match on specified face
     */
    public boolean canConnect(LabyrinthSection other, Direction otherOrientation, Direction direction) {
        ConnectionType[] con = getConnections();
        ConnectionType[] oCon = other.getConnections(otherOrientation);
        switch (direction) {
            case NORTH:
                return con[0] == oCon[2];
            case EAST:
                return con[1] == oCon[3];
            case SOUTH:
                return con[2] == oCon[0];
            case WEST:
                return con[3] == oCon[1];

            default:
                return false;
        }
    }

    public static final Function<Direction, LabyrinthSection> STRAIGHT = Straight::new;
    public static final Function<Direction, LabyrinthSection> CROSS = Cross::new;

    public static enum ConnectionType {
        WALL,
        CORRIDOR_NARROW
    }
}
