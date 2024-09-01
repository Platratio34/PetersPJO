package com.peter.peterspjo.abilities;

/** Empty ability for default in registry */
public final class NoneAbility extends AbstractAbility {

    public NoneAbility() {
        super(NoneAbility::new, "none");
    }

    @Override
    public boolean compatibleWith(AbstractAbility ability) {
        return true;
    }

}
