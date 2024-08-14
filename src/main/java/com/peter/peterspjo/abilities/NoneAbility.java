package com.peter.peterspjo.abilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public final class NoneAbility extends AbstractAbility {

    public NoneAbility() {
        super(NoneAbility::new, "none");
    }

    @Override
    public boolean compatibleWith(AbstractAbility ability) {
        return true;
    }

    @Override
    public void onUseAbility(PlayerEntity player, World world) {
        
    }

    @Override
    protected void onPassiveTick(PlayerEntity player, World world) {
        
    }

}
