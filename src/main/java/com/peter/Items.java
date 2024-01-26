package com.peter;

import com.peter.items.CelestialBronzeSword;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    
    public static final Item DRACHMA = new Item(new FabricItemSettings());
    public static final Identifier DRACHMA_ID = new Identifier(PJO.NAMESPACE, "drachma");

    public static final CelestialBronzeSword C_BRONZE_SWORD = new CelestialBronzeSword(
            new FabricItemSettings().maxDamage(6));

    public static void init() {
        Registry.register(Registries.ITEM, DRACHMA_ID, DRACHMA);
        Registry.register(Registries.ITEM, CelestialBronzeSword.ID, C_BRONZE_SWORD);
    }
}
