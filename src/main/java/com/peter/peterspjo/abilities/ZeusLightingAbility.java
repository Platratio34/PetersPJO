package com.peter.peterspjo.abilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ZeusLightingAbility extends AbstractAbility implements ChargedAbility {

    public static final String NAME = "greek_zeus_lighting";

    private int chargeState = 0;
    private static final int MAX_CHARGE = 10;

    public ZeusLightingAbility() {
        super(ZeusLightingAbility::new, NAME);
        chargeState = 0;
    }

    @Override
    public void charge() {
        chargeState++;
    }

    @Override
    public void charge(int amount) {
        chargeState += amount;
    }

    @Override
    public boolean isCharged() {
        return chargeState >= MAX_CHARGE;
    }

    @Override
    public float getChargePercent() {
        if (chargeState <= 0)
            return 0f;
        else if (chargeState >= MAX_CHARGE)
            return 1f;
        else
            return (float)chargeState / (float)MAX_CHARGE;
    }

    @Override
    public void onUseAbility(PlayerEntity player, World world) {
        // if (!isCharged()) {
        //     return;
        // }
        LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        if (spawnEntityAtPlayerLook(world, player, lightningEntity, 50f)) {
            chargeState = 0;   
        }
    }

    @Override
    public boolean compatibleWith(AbstractAbility otherAbility) {
        if (otherAbility instanceof PoseidonWBAbility)
            return false;
        return true;
    }

}
