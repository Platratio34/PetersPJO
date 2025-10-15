package com.peter.peterspjo.abilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ZeusLightingAbility extends AbstractChargedAbility {

    public static final String NAME = "greek_zeus_lighting";

    private static final int REQ_CHARGE = 10;
    private static final int MAX_OVERCHARGE = 10;

    public ZeusLightingAbility() {
        super(ZeusLightingAbility::new, NAME, REQ_CHARGE, MAX_OVERCHARGE);
    }

    @Override
    public void onUseAbility(PlayerEntity player, World world) {
        if (!isCharged()) {
            return;
        }
        LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        if (spawnEntityAtPlayerLook(world, player, lightningEntity, 50f)) {
            useCharge();
        }
    }

    @Override
    public boolean compatibleWith(AbstractAbility otherAbility) {
        return !(otherAbility instanceof PoseidonWBAbility);
    }

}
