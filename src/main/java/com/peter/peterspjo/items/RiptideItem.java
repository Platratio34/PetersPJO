package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

public class RiptideItem extends SwitchableSword {

    public static final String NAME = "riptide";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);

    public static final RiptideItem ITEM = Registry.register(Registries.ITEM, ID, new RiptideItem(
            new FabricItemSettings()));

    public static void register() {
    }

    public boolean isSword = false;

    public RiptideItem(FabricItemSettings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 8, -2f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip2").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip3").formatted(Formatting.GOLD));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 10;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return false;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

}
