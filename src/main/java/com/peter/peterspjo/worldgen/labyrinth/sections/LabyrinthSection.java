package com.peter.peterspjo.worldgen.labyrinth.sections;

import java.util.HashMap;
import java.util.function.BiFunction;

import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.util.NoiseGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMap;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMaterials.LabyrinthMaterialSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;

public abstract class LabyrinthSection {

    public static final Block DEFAULT_BLOCK = Blocks.STONE;
    public static final Block DEFAULT_AIR = Blocks.AIR;
    public static final Block DEFAULT_LIGHT = Blocks.LANTERN;
    public static final Block DEFAULT_DOOR = PJOBlocks.LABYRINTH_DOOR;

    /** Normal floor block y */
    public static final int FLOOR_HEIGHT = 1;
    /** Normal celling block y */
    public static final int CELLING_HEIGHT = 5;
    /** Narrow corridor wall block for minimum x and or z */
    public static final int CORRIDOR_N_MIN = 6;
    /** Narrow corridor wall block for maximum x and or z */
    public static final int CORRIDOR_N_MAX = 9;

    /** ID of labyrinth section. <b>MUST BE UNIQUE</b>. Used for storage */
    public final String id;

    /** Connection types, ordered: north, east, south, west, up, down */
    public final ConnectionType[] connections;

    /** Orientation of section */
    public final Direction orientation;
    /** Material set for section */
    public final LabyrinthMaterialSet set;

    /**
     * Initialism the labyrinth section, setting ID, connections, and orientation
     * 
     * @param id          ID of section type
     * @param connections section connections
     * @param orientation orientation of section instance
     * @param set         material set for section instance
     */
    public LabyrinthSection(String id, ConnectionType[] connections, Direction orientation, LabyrinthMaterialSet set) {
        this.id = id;
        this.connections = connections;
        this.orientation = orientation;
        this.set = set;
    }

    /**
     * Get connections for the section
     * 
     * @param orientation Section orientation
     * @return Array of connections, ordered north, east, south, west
     */
    public ConnectionType[] getConnections(Direction orientation) {
        return switch (orientation) {
            case NORTH -> connections;
            case EAST -> new ConnectionType[] { connections[1], connections[2], connections[3], connections[0],
                connections[4], connections[5] };
            case SOUTH -> new ConnectionType[] { connections[2], connections[3], connections[0], connections[1],
                connections[4], connections[5] };
            case WEST -> new ConnectionType[] { connections[3], connections[0], connections[1], connections[2],
                connections[4], connections[5] };
            default -> connections;
        };
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
     * 
     * @param sectionX x (east positive) coordinate
     * @param sectionY y (up positive) coordinate
     * @param sectionZ z (south positive) coordinate
     * @param nSet     material set for section to the north (-z)
     * @param eSet     material set for section to the east (+x)
     * @param sSet     material set for section to the south (+z)
     * @param wSet     material set for section to the west (-x)
     * @param noise    random noise generator for random replacement
     * @return Block state at location
     */
    public abstract BlockState sample(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet, NoiseGenerator noise);

    /**
     * Returns if the given location should have the random replace block of the
     * material set
     * 
     * @param sectionX x (east positive) coordinate
     * @param sectionY y (up positive) coordinate
     * @param sectionZ z (south positive) coordinate
     * @param noise    random noise generator for random replacement
     * @return If the block should be the random replace block
     */
    protected boolean isRand(int sectionX, int sectionY, int sectionZ, NoiseGenerator noise) {
        return noise.noise(sectionX * 2, sectionY * 2, sectionZ * 2) > 0.2;
    }

    /**
     * Gets the material set for a specific block, blending between sections
     * 
     * @param sectionX x (east positive) coordinate
     * @param sectionY y (up positive) coordinate
     * @param sectionZ z (south positive) coordinate
     * @param nSet     material set for section to the north (-z)
     * @param eSet     material set for section to the east (+x)
     * @param sSet     material set for section to the south (+z)
     * @param wSet     material set for section to the west (-x)
     * @return Material set to use for specific block, blending between sections
     */
    protected LabyrinthMaterialSet getMaterialSet(int sectionX, int sectionY, int sectionZ, LabyrinthMaterialSet nSet,
            LabyrinthMaterialSet eSet, LabyrinthMaterialSet sSet, LabyrinthMaterialSet wSet) {

        if (sectionX == 0) {
            if (sectionY % 2 == sectionZ % 2) {
                return wSet;
            }
        } else if (sectionX == 15) {
            if (sectionY % 2 == sectionZ % 2) {
                return eSet;
            }
        } else if (sectionZ == 0) {
            if (sectionY % 2 == sectionX % 2) {
                return nSet;
            }
        } else if (sectionZ == 15) {
            if (sectionY % 2 == sectionX % 2) {
                return sSet;
            }
        }
        return set;
    }

    /**
     * If this section could connect to other section in direction from this section
     * 
     * @param other     section to check against
     * @param direction direction from this section
     * @return If the connection types match on specified face
     */
    public boolean canConnect(LabyrinthSection other, Direction direction) {
        return canConnect(other, other.orientation, direction);
    }

    /**
     * If this section could connect to other section in direction from this section
     * 
     * @param other            section to check against
     * @param otherOrientation override direction for other section
     * @param direction        direction from this section
     * @return If the connection types match on specified face
     */
    public boolean canConnect(LabyrinthSection other, Direction otherOrientation, Direction direction) {
        ConnectionType[] con = getConnections();
        ConnectionType[] oCon = other.getConnections(otherOrientation);
        return switch (direction) {
            case NORTH -> con[0] == oCon[2];
            case EAST -> con[1] == oCon[3];
            case SOUTH -> con[2] == oCon[0];
            case WEST -> con[3] == oCon[1];
            case UP -> con[4] == oCon[5];
            case DOWN -> con[5] == oCon[4];
            default -> false;
        };
    }

    /**
     * If this section could connect to other section in direction from this section
     * with a corridor
     * 
     * @param other     section to check against
     * @param direction direction from this section
     * @return If the connection types match on specified face
     */
    public boolean canConnectCorridor(LabyrinthSection other, Direction direction) {
        return canConnectCorridor(other, other.orientation, direction);
    }

    /**
     * If this section could connect to other section in direction from this section
     * with a corridor
     * 
     * @param other            section to check against
     * @param otherOrientation override direction for other section
     * @param direction        direction from this section
     * @return If the connection types match on specified face
     */
    public boolean canConnectCorridor(LabyrinthSection other, Direction otherOrientation, Direction direction) {
        ConnectionType[] con = getConnections();
        ConnectionType[] oCon = other.getConnections(otherOrientation);
        return switch (direction) {
            case NORTH -> con[0] == oCon[2] && con[0] != ConnectionType.WALL;
            case EAST -> con[1] == oCon[3] && con[1] != ConnectionType.WALL;
            case SOUTH -> con[2] == oCon[0] && con[2] != ConnectionType.WALL;
            case WEST -> con[3] == oCon[1] && con[3] != ConnectionType.WALL;
            case UP -> con[4] == oCon[5] && con[4] != ConnectionType.WALL;
            case DOWN -> con[5] == oCon[4] && con[5] != ConnectionType.WALL;
            default -> false;
        };
    }

    public boolean canPlace(ChunkPos pos, int yIndex, LabyrinthMap map) {
        return true;
    }

    /**
     * Convert this section into an NBT compound that can be stored.<br>
     * <br>
     * Contains section type ID, orientation, and material set ID
     * 
     * @return NBT representing the section
     */
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", id);
        nbt.putInt("dir", orientation.getId());
        nbt.putString("set", set.id);
        return nbt;
    }

    /**
     * Create a new labyrinth section from NBT written by the {@link #toNbt()}
     * 
     * @param nbt NBT data to create a section from
     * @return Section created from NBT data
     * @see #toNbt()
     */
    public static LabyrinthSection sectionFromNbt(NbtCompound nbt) {
        Direction direction = Direction.byId(nbt.getInt("dir"));
        LabyrinthMaterialSet set = LabyrinthMaterials.MATERIALS_BY_ID.get(nbt.getString("set"));
        LabyrinthSection section = SECTIONS_BY_ID.get(nbt.getString("id")).gen(direction, set);
        return section;
    }

    public static final SectionGen EMPTY = new SectionGen(Empty::new);

    public static final SectionGen STRAIGHT = new SectionGen(Straight::new);
    public static final SectionGen STRAIGHT_ROOM = new SectionGen(StraightRoom::new);

    public static final SectionGen CROSS = new SectionGen(Cross::new);
    public static final SectionGen CROSS_ROOM = new SectionGen(CrossRoom::new);

    public static final SectionGen CROSS_ROOM_UP = new SectionGen(CrossRoomUp::new);
    public static final SectionGen CROSS_ROOM_DOWN = new SectionGen(CrossRoomDown::new);

    public static final SectionGen CORNER = new SectionGen(Corner::new);

    public static final SectionGen TEE = new SectionGen(Tee::new);
    public static final SectionGen TEE_ROOM = new SectionGen(TeeRoom::new);

    /** All sections used in generation */
    public static final SectionGen[] SECTIONS = {
            new SectionGen(STRAIGHT, STRAIGHT_ROOM),
            new SectionGen(CROSS, CROSS_ROOM),
            CORNER,
            new SectionGen(TEE, TEE_ROOM),
            CROSS_ROOM_UP,
            CROSS_ROOM_DOWN,
    };

    /** Map of sections by type IDs */
    public static final HashMap<String, SectionGen> SECTIONS_BY_ID = new HashMap<>();

    static {
        SECTIONS_BY_ID.put("empty", EMPTY);
        for (SectionGen SECTIONS1 : SECTIONS) {
            SECTIONS1.addToMap();
            // SECTIONS_BY_ID.put(SECTIONS[i].gen(null, null).id, SECTIONS[i]);
        }
    }

    public static class SectionGen {

        private BiFunction<Direction, LabyrinthMaterialSet, LabyrinthSection> func;
        private SectionGen[] variants;

        public SectionGen(BiFunction<Direction, LabyrinthMaterialSet, LabyrinthSection> func) {
            this.func = func;
        }

        public SectionGen(BiFunction<Direction, LabyrinthMaterialSet, LabyrinthSection> variant1,
                BiFunction<Direction, LabyrinthMaterialSet, LabyrinthSection> variant2) {
            this.variants = new SectionGen[] {
                    new SectionGen(variant1),
                    new SectionGen(variant2)
            };
        }

        public SectionGen(SectionGen variant1, SectionGen variant2) {
            this.variants = new SectionGen[] {
                    variant1,
                    variant2

            };
        }

        public SectionGen(SectionGen[] variants) {
            this.variants = variants;
        }

        /**
         * Generate a new section of this type
         * 
         * @param orientation orientation of section
         * @param set         material set for section
         * @return New section
         */
        public LabyrinthSection gen(Direction orientation, LabyrinthMaterialSet set) {
            if (func == null) {
                return variants[0].gen(orientation, set);
            }
            return func.apply(orientation, set);
        }

        /**
         * Generate a new section of this type
         * 
         * @param orientation orientation of section
         * @param set         material set for section
         * @param variant     section variation
         * @return New section
         */
        public LabyrinthSection gen(Direction orientation, LabyrinthMaterialSet set, int variant) {
            if (variants == null)
                return func.apply(orientation, set);
            else {
                variant = variant % variants.length;
                return variants[variant].gen(orientation, set);
            }
        }

        protected void addToMap() {
            // LabyrinthSection.
            if (func != null) {
                LabyrinthSection.SECTIONS_BY_ID.put(func.apply(null, null).id, this);
            }
            if (variants != null) {
                for (SectionGen variant : variants) {
                    variant.addToMap();
                }
            }
        }
    }

    /** Connection type for labyrinth sections */
    public static enum ConnectionType {
        /** Full solid wall */
        WALL,
        /**
         * Narrow corridor (2 block wide at standard height)
         * 
         * @see LabyrinthSection#CORRIDOR_N_MIN
         * @see LabyrinthSection#CORRIDOR_N_MAX
         */
        CORRIDOR_NARROW,
        VERTICAL_NARROW,
    }
}
