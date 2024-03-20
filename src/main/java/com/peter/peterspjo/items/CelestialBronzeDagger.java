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

public class CelestialBronzeDagger extends CelestialSword {

    public static final String NAME = "celestial_bronze_dagger";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);

    public static final CelestialBronzeDagger ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeDagger(
            new FabricItemSettings()));

    public static void init() {}

    public CelestialBronzeDagger(FabricItemSettings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 4, 0.3f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // default white text
        tooltip.add(Text.translatable("item."+PJO.NAMESPACE+"."+NAME+".tooltip").formatted(Formatting.GOLD));
    }

}
