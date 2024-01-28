package com.peter.peterspjo;

import com.peter.peterspjo.items.CelestialBronzeIngot;
import com.peter.peterspjo.items.CelestialBronzeSpear;
import com.peter.peterspjo.items.CelestialBronzeSword;
import com.peter.peterspjo.items.TooltipedItem;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Items {
    
    public static final Item DRACHMA = new TooltipedItem(new FabricItemSettings(), Text.translatable("item.peterspjo.drachma.tooltip").formatted(Formatting.GOLD));
    public static final Identifier DRACHMA_ID = new Identifier(PJO.NAMESPACE, "drachma");
    

    public static void init() {
        Registry.register(Registries.ITEM, DRACHMA_ID, DRACHMA);
        CelestialBronzeIngot.init();

        CelestialBronzeSword.init();
        CelestialBronzeSpear.init();
    }
}
