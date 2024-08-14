package com.peter.peterspjo.abilities;

import com.google.common.base.Supplier;
import com.peter.peterspjo.PJO;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
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
    public abstract void onUseAbility(PlayerEntity player, World world);

    /**
     * Called when the ability is used passively based on value set in <code>setPassiveTickRate()</code><br>
     * @param player Player using this ability
     * @param world World the ability is being used in
     */
    protected abstract void onPassiveTick(PlayerEntity player, World world);

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
}
