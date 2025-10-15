package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class RiptideItem extends SwitchableSword {

    public static final String NAME = "riptide";
    public static final Identifier ID = PJO.id(NAME);

    public static final RiptideItem ITEM = Registry.register(Registries.ITEM, ID, new RiptideItem(
            new Item.Settings().rarity(Rarity.EPIC)));

    public static void register() {
    }

    public boolean isSword = false;

    public RiptideItem(Item.Settings settings) {
        super(PJOMaterials.CELESTIAL_BRONZE_MATERIAL, 8, -2f, settings, ID);
    }

    @Override
    public void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> tooltip) {
        tooltip.add(PJO.tooltip("item", NAME).formatted(Formatting.GOLD));
        tooltip.add(PJO.tooltip("item", NAME, 2).formatted(Formatting.GOLD));
        tooltip.add(PJO.tooltip("item", NAME, 3).formatted(Formatting.GOLD));
        super.addTooltip(itemStack, tooltipContext, tooltipType, tooltip);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 10;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return false;
    }

}
