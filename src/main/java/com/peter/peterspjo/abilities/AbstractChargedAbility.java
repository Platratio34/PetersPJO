package com.peter.peterspjo.abilities;

import com.google.common.base.Supplier;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.networking.AbilityUpdatePayload;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public abstract class AbstractChargedAbility extends AbstractAbility {

    /** The current charge state of the ability. Should range between <code>0</code> and <code>reqCharge * maxOvercharge</code> */
    protected int chargeState = 0;
    /** The charge required for the ability to be used */
    protected int reqCharge = 10;
    /** Maximum number of full charges of the ability that can be stored at one time */
    protected int maxOvercharge = 10;

    /**
     * Create a new Charged Ability with default charge values
     * @param constructor 0 parameter constructor function for instancing
     * @param id Identifier for ability type
     */
    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, Identifier id) {
        this(constructor, id, 10, 10);
    }

    /**
     * Create a new Charged Ability with default charge values
     * @param constructor 0 parameter constructor function for instancing
     * @param id Ability type name for PJO namespace
     */
    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, String id) {
        this(constructor, PJO.id(id), 10, 10);
    }
    
    /**
     * Create a new Charged Ability with default charge values
     * @param constructor 0 parameter constructor function for instancing
     * @param id Ability type name for PJO namespace
     * @param reqCharge The amount of charge required for one usage of this ability
     * @param maxOverCharge The maximum number of full charges that can be stored at one time
     */
    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, String id, int reqCharge,
            int maxOvercharge) {
        this(constructor, PJO.id(id), reqCharge, maxOvercharge);
    }

    /**
     * Create a new Charged Ability with default charge values
     * @param constructor 0 parameter constructor function for instancing
     * @param id Identifier for ability type
     * @param reqCharge The amount of charge required for one usage of this ability
     * @param maxOverCharge The maximum number of full charges that can be stored at one time
     */
    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, Identifier id, int reqCharge,
            int maxOvercharge) {
        super(constructor, id);
        this.reqCharge = reqCharge;
        this.maxOvercharge = maxOvercharge;
        chargeState = 0;
    }
    
    /**
     * Charge the ability by the default amount
     */
    public void charge() {
        chargeState++;
        if (chargeState > reqCharge * maxOvercharge) {
            chargeState = reqCharge * maxOvercharge;
        }
        markChargeDirty();

    }
    
    /**
     * Charge the ability by the specified amount
     * @param amount Amount to charge the ability by
     */
    public void charge(int amount) {
        chargeState += amount;
        if (chargeState > reqCharge * maxOvercharge) {
            chargeState = reqCharge * maxOvercharge;
        } else if (chargeState < 0) {
            chargeState = 0;
        }
        markChargeDirty();
    }
    
    /**
     * Set the charge state of the ability
     * @param amount Charge state of the ability. Should be between <code>0</code> and <code>getMaxCharge()</code>
     */
    public void setCharge(int amount) {
        chargeState = amount;
        if (chargeState > reqCharge * maxOvercharge) {
            chargeState = reqCharge * maxOvercharge;
        } else if (chargeState < 0) {
            chargeState = 0;
        }
        markChargeDirty();
    }

    /**
     * Get the current charge state of the ability
     * @return Current charge state between <code>0</code> and <code>getMaxCharge()</code>
     */
    public int getCharge() {
        return chargeState;
    }

    /**
     * Get the amount of charge required for one usage of this ability
     * @return Charge required for one usage
     */
    public int getReqCharge() {
        return reqCharge;
    }

    /**
     * Get the maximum charge of this ability. Equal to <code>getReqCharge() * getMaxOvercharge()</code>
     * @return Maximum charge state of this ability
     */
    public int getMaxCharge() {
        return reqCharge * maxOvercharge;
    }

    public int getMaxOvercharge() {
        return maxOvercharge;
    }

    /**
     * Check if there is at least enough charge for one usage of this ability
     * @return
     */
    public boolean isCharged() {
        return chargeState >= reqCharge;
    }

    /**
     * Get the percentage of one use this ability is charged to.
     * @return Percent of charge. Value will be between <code>0</code> and <code>getMaxOvercharge()</code>
     */
    public float getChargePercent() {
        if (chargeState <= 0) {
            return 0f;
        }
        return ((float) chargeState) / ((float) reqCharge);
    }

    /**
     * Use one charge of this ability. Decreases current charge state by <code>getReqCharge()</code>
     */
    protected void useCharge() {
        charge(-reqCharge);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("charge", chargeState);
    }

    @Override
    public void fromNbt(NbtCompound nbt) {
        if (nbt.contains("charge")) {
            chargeState = nbt.getInt("charge");
        }
    }

    /**
     * Mark the charge state as having changed and sends an update to the player if possible. Passes if run on client
     */
    public void markChargeDirty() {
        if (isClient)
            return;
        markDirty();
        AbilityUpdatePayload.sendCharge(this);
    }

}
