package com.peter.peterspjo.abilities;

public interface ChargedAbility {

    public void charge();

    public void charge(int amount);

    public void setCharge(int amount);

    public int getCharge();

    public boolean isCharged();

    public float getChargePercent();
}
