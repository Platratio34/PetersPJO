package com.peter.peterspjo.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class Sword extends SwordItem {

    public Sword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

}
