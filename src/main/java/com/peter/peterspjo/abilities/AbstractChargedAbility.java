package com.peter.peterspjo.abilities;

import com.google.common.base.Supplier;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.networking.AbilityUpdatePayload;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public abstract class AbstractChargedAbility extends AbstractAbility {

    protected int chargeState = 0;
    protected int reqCharge = 10;
    protected int maxOvercharge = 10;

    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, Identifier id) {
        this(constructor, id, 10, 10);
    }

    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, String id) {
        this(constructor, PJO.id(id), 10, 10);
    }
    
    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, String id, int reqCharge,
            int maxOvercharge) {
        this(constructor, PJO.id(id), reqCharge, maxOvercharge);
    }

    public AbstractChargedAbility(Supplier<AbstractAbility> constructor, Identifier id, int reqCharge,
            int maxOvercharge) {
        super(constructor, id);
        this.reqCharge = reqCharge;
        this.maxOvercharge = maxOvercharge;
        chargeState = 0;
    }
    
    public void charge() {
        chargeState++;
        if (chargeState > reqCharge * maxOvercharge) {
            chargeState = reqCharge * maxOvercharge;
        }
        markChargeDirty();

    }
    
    public void charge(int amount) {
        chargeState += amount;
        if (chargeState > reqCharge * maxOvercharge) {
            chargeState = reqCharge * maxOvercharge;
        } else if (chargeState < 0) {
            chargeState = 0;
        }
        markChargeDirty();
    }
    
    public void setCharge(int amount) {
        chargeState = amount;
        if (chargeState > reqCharge * maxOvercharge) {
            chargeState = reqCharge * maxOvercharge;
        } else if (chargeState < 0) {
            chargeState = 0;
        }
        markChargeDirty();
    }

    public int getCharge() {
        return chargeState;
    }

    public int getReqCharge() {
        return reqCharge;
    }

    public int getMaxCharge() {
        return reqCharge * maxOvercharge;
    }

    public boolean isCharged() {
        return chargeState >= reqCharge;
    }

    public float getChargePercent() {
        if (chargeState <= 0) {
            return 0f;
        }
        return ((float) chargeState) / ((float) reqCharge);
    }

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

    public void markChargeDirty() {
        AbilityManager.INSTANCE.markDirty();
        AbilityUpdatePayload.sendCharge(this);
    }

}
