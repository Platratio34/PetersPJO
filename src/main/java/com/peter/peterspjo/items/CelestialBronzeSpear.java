package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CelestialBronzeSpear extends Spear {

    public static final String NAME = "celestial_bronze_spear";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);

    public static final CelestialBronzeSpear ITEM = Registry.register(Registries.ITEM, ID, new CelestialBronzeSpear(CelestialBronzeMaterial.INSTNACE, new FabricItemSettings()));

    public static void init() {}

    public CelestialBronzeSpear(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // default white text
        tooltip.add(Text.translatable("item.peterspjo."+NAME+".tooltip").formatted(Formatting.GOLD));
    }

}
