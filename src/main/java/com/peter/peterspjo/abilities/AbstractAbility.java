package com.peter.peterspjo.abilities;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Supplier;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.networking.AbilityUpdatePayload;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

/**
 * Abstract Ability class.<br>
 * <br>
 * Base for all divine-type abilities in the mod
 */
public abstract class AbstractAbility {

    /** Namespace and name of ability */
    public final Identifier id;
    /** Constructor function for instancing ability */
    private final Supplier<AbstractAbility> constructor;

    /** Never passively tick this ability */
    public static final int NEVER = -1;
    /** Run the passive tick every tick */
    public static final int EVERY_TICK = 1;
    /** Run the passive tick once per second */
    public static final int ONCE_PER_SECOND = 20;
    /** Run the passive tick once per minute */
    public static final int ONCE_PER_MINUTE = ONCE_PER_SECOND * 60;

    /** How often this ability should receive a passive tick. Set to `-1` to disable */
    private int passiveTickRate = NEVER;
    /** Number of ticks since the last passive tick */
    private int lastPassiveTick = 0;

    /** UUID of the player this ability applies to */
    protected UUID playerUuid;
    /**
     * The player this ability applies to
     * @Nullable
     */
    protected PlayerEntity player;

    /**
     * If this instance of the ability exists on the client or server
     */
    public boolean isClient = false;

    /**
     * Create a new Ability
     * @param constructor 0 parameter constructor function for instancing
     * @param id Identifier for ability type
     */
    public AbstractAbility(Supplier<AbstractAbility> constructor, Identifier id) {
        this.constructor = constructor;
        this.id = id;
    }
    /**
     * Create a new Ability in the <code>peterspjo</code> namespace
     * @param constructor 0 parameter constructor function for instancing
     * @param name Ability type name for PJO namespace
     */
    public AbstractAbility(Supplier<AbstractAbility> constructor, String name) {
        this(constructor, PJO.id(name));
    }

    /**
     * Create a new instance of this Ability type
     * @return new instance of Ability type
     */
    public AbstractAbility instance() {
        return constructor.get();
    };

    /**
     * Called when the ability is used actively
     * @param player Player using this ability
     * @param world World the ability is being used in
     */
    public void onUseAbility(PlayerEntity player, World world) { };

    /**
     * Called when the ability is used passively based on value set in <code>setPassiveTickRate()</code><br>
     * @param player Player using this ability
     * @param world World the ability is being used in
     */
    protected void onPassiveTick(PlayerEntity player, World world) { };

    /**
     * Checks if this ability can be held at the same time as the other
     * @param otherAbility Ability to check if this ability is compatible with
     * @return compatible with <code>otherAbility</code>
     */
    public abstract boolean compatibleWith(AbstractAbility otherAbility);

    /**
     * Set the passive tick rate of this ability.
     * May be one of the following or an number:
     * <ul>
     *  <li><code>AbstractAbility.NEVER</code></li>
     *  <li><code>AbstractAbility.EVERY_TICK</code></li>
     *  <li><code>AbstractAbility.ONCE_PER_SECOND</code></li>
     *  <li><code>AbstractAbility.ONCE_PER_MINUTE</code></li>
     * </ul>
     * @param tickRate passive tick rate (<code>-1</code> to disable)
     */
    protected void setPassiveTickRate(int tickRate) {
        this.passiveTickRate = tickRate;
        lastPassiveTick = 0;
    }

    /**
     * Passively tick this ability.<br><br>
     * <b>SHOULD ONLY BE CALLED BY <code>AbilityManager</code></b>
     * @param player Player with this ability
     * @param world World the ability ability is in
     */
    public void passiveTick(PlayerEntity player, World world) {
        if (this.player != player)
            setPlayerEntity(player);
        if (passiveTickRate < 0) {
            return;
        }
        lastPassiveTick++;
        if (lastPassiveTick >= passiveTickRate) {
            lastPassiveTick = 0;
        } else {
            return;
        }
        onPassiveTick(player, world);
    }

    /**
     * Set the UUID of the player this ability is associated with
     * @param playerUuid The UUID of the player this ability is associated with
     */
    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    /**
     * Set the UUID of the player this ability is associated with
     * @param player The player this ability is associated with
     */
    public void setPlayerUuid(PlayerEntity player) {
        setPlayerUuid(player.getUuid());
    }

    /**
     * Set the player entity this ability is associated with
     * @param player The player entity this ability is associated with
     */
    public void setPlayerEntity(PlayerEntity player) {
        World world = player.getWorld();
        if (!world.isClient) {
            if (this.player != null && this.player != player) {
                AbilityUpdatePayload.sendRemove((ServerPlayerEntity) this.player, id);
            }
            AbilityUpdatePayload.sendAdd((ServerPlayerEntity) player, id);
        }
        isClient = world.isClient;
        this.player = player;
        setPlayerUuid(player);
    }

    /**
     * Get the player entity this ability is associated with.
     * <br/><br/>
     * <b>MAY BE <code>null</code></b> if ability is player is not in the game
     * @return Player entity this ability is associated with
     */
    @Nullable
    public PlayerEntity getPlayerEntity() {
        return player;
    }

    /**
     * Mark this instance of the ability as existing on the client.
     * <br/><br/>
     * Disables sync to client and persistance state dirtying
     * @return this instance of the ability
     */
    public AbstractAbility markClient() {
        isClient = true;
        return this;
    }

    /**
     * Cleanup for removal of ability from player
     */
    public void remove() {
        onRemove();
        if (isClient)
            return;
        if (this.player == null)
            return;
        AbilityUpdatePayload.sendRemove((ServerPlayerEntity) player, id);
    }

    /**
     * Cleanup for removal of ability from player
     * @param player Player to remove the ability from
     */
    public void remove(PlayerEntity player) {
        onRemove();
        if (player == null)
            return;
        World world = player.getWorld();
        if (world.isClient)
            return;
        AbilityUpdatePayload.sendRemove((ServerPlayerEntity) player, id);
    }
    
    /**
     * Un-associated the player <i>entity</i> from this ability. <b>DOES NOT REMOVE FROM PLAYER</b>
     */
    public void removePlayerEntity() {
        player = null;
    }
    
    /**
     * Cleanup function for derivative classes for when removed from player.
     * <br/><br/>
     * Called at the start of <code>remove()</code> 
     */
    protected void onRemove() {
    };

    /**
     * Helper function for entity spawning related abilities.
     * <br/><br/>
     * Places the provided entity at the player entity and spawns it in the provided world;
     * @param world World to spawn the entity in
     * @param player Player to use for placement
     * @param entity Entity to spawn
     */
    public static void spawnEntityAtPlayer(World world, PlayerEntity player, Entity entity) {
        entity.setPosition(player.getPos());
        world.spawnEntity(entity);
    }

    /**
     * Helper function for entity spawning related abilities.
     * <br/><br/>
     * Places the provided entity at the player's cursor and spawns it in the provided world
     * @param world World to spawn the entity in
     * @param player Player to use for placement
     * @param entity Entity to spawn
     * @param length Max distance to block under cursor
     * @return If the entity was actually spawned
     */
    public static boolean spawnEntityAtPlayerLook(World world, PlayerEntity player, Entity entity, double length) {
        // entity.setPosition(player.getPos());
        HitResult hit = player.raycast(length, 0, false);
        if (hit.getType() == HitResult.Type.MISS) {
            return false;
        }
        // Vec3d pos = new Vec3d(0f,0f,0f);
        // if (hit.getType() == HitResult.Type.BLOCK) {
        //     pos = hit.getPos();
        // }
        entity.setPosition(hit.getPos());
        world.spawnEntity(entity);
        return true;
    }
    
    /**
     * Write NBT data for this ability.
     * <br/><br/>
     * Writes the key <code>id</code> and calls <code>writeNbt(NbtCompound)</code>
     * @return
     */
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", id.toString());
        writeNbt(nbt);
        return nbt;
    }

    /**
     * Write NBT data for custom ability information
     * @param nbt NBT compound for ability. Already contains key <code>id</code>
     */
    protected void writeNbt(NbtCompound nbt) {
    }
    
    /**
     * Load data from NBT for custom ability
     * @param nbt NBT data to load
     */
    public void fromNbt(NbtCompound nbt) {
    }
    
    /**
     * Mark the ability as dirty in the manager so that changes are saved
     */
    public void markDirty() {
        if (isClient)
            return;
        AbilityManager.INSTANCE.markDirty();
    }
}
