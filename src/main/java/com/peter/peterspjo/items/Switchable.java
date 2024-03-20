package com.peter.peterspjo.items;

import net.minecraft.item.ItemStack;

public interface Switchable {
    
    public ItemStack getDefaultStateStack(ItemStack stack);

    public boolean isWeapon(ItemStack stack);
}
