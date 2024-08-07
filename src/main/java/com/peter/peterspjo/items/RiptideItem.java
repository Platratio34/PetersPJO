package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
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
    public static final Identifier ID = Identifier.of(PJO.NAMESPACE, NAME);

    public static final RiptideItem ITEM = Registry.register(Registries.ITEM, ID, new RiptideItem(
            new Item.Settings().rarity(Rarity.EPIC)));

    public static void register() {
    }

    public boolean isSword = false;

    public RiptideItem(Item.Settings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 8, -2f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip2").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip3").formatted(Formatting.GOLD));
        super.appendTooltip(itemStack, context, tooltip, type);
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
