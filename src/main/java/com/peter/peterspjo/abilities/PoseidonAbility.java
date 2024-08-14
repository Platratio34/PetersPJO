package com.peter.peterspjo.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PoseidonAbility extends AbstractAbility {

    public static final String NAME = "greek_poseidon_water_breathing";

    public PoseidonAbility() {
        super(PoseidonAbility::new, NAME);
        setPassiveTickRate(ONCE_PER_SECOND * 5);
    }
    
    @Override
    protected void onPassiveTick(PlayerEntity player, World world) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 300, 1, true, false));
    }

    @Override
    public boolean compatibleWith(AbstractAbility otherAbility) {
        return true;
    }

    @Override
    public void onUseAbility(PlayerEntity player, World world) {
        
    }

}
