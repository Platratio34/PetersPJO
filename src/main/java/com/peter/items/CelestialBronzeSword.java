package com.peter.items;

import java.util.List;

import com.peter.PJO;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CelestialBronzeSword extends SwordItem {

    public static final Identifier ID = new Identifier(PJO.NAMESPACE, "celestial_bronze_sword");

    public CelestialBronzeSword(Settings settings) {
        super(CelestialBronzeMaterial.INSTNACE, 6, 0.3f, settings);
    }

    @Override
public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
 
    // default white text
    tooltip.add(Text.translatable("item.peterspjo.celestial_bronze_sword.tooltip").formatted(Formatting.GOLD));
}

}
