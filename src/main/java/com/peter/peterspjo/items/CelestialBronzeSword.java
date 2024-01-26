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
import net.minecraft.world.World;

public class CelestialBronzeSword extends Sword {

    public static final Identifier ID = new Identifier(PJO.NAMESPACE, "celestial_bronze_sword");

    public static final CelestialBronzeSword ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeSword(
            new FabricItemSettings()));

    public static void init() {}
    
    public CelestialBronzeSword(Settings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 6, 0.3f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // default white text
        tooltip.add(Text.translatable("item.peterspjo.celestial_bronze_sword.tooltip").formatted(Formatting.GOLD));
    }

}
