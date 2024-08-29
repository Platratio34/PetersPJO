package com.peter.peterspjo.abilities;

public interface ChargedAbility {

    public void charge();

    public void charge(int amount);

    public boolean isCharged();

    public float getChargePercent();
}
