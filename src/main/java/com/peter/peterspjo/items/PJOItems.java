package com.peter.peterspjo.items;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.armor.CelestialBronzeArmorMaterial;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PJOItems {
    
    public static final Item DRACHMA = new TooltipedItem(new FabricItemSettings(), Text.translatable("item.peterspjo.drachma.tooltip").formatted(Formatting.GOLD));
    public static final Identifier DRACHMA_ID = new Identifier(PJO.NAMESPACE, "drachma");

    public static final CelestialBronzeIngot CELESTIAL_BRONZE_INGOT = CelestialBronzeIngot.ITEM;
    public static final CelestialBronzeSword CELESTIAL_BRONZE_SWORD = CelestialBronzeSword.ITEM;
    public static final CelestialBronzeSpear CELESTIAL_BRONZE_SPEAR = CelestialBronzeSpear.ITEM;
    public static final ArmorItem CELESTIAL_BRONZE_HELMET = CelestialBronzeArmorMaterial.HELMET;
    public static final ArmorItem CELESTIAL_BRONZE_CHESTPLATE = CelestialBronzeArmorMaterial.CHESTPLATE;
    public static final ArmorItem CELESTIAL_BRONZE_LEGGINGS = CelestialBronzeArmorMaterial.LEGGINGS;
    public static final ArmorItem CELESTIAL_BRONZE_BOOTS = CelestialBronzeArmorMaterial.BOOTS;
    
    public static final RiptideItem RIPTIDE = RiptideItem.ITEM;

    public static void init() {
        Registry.register(Registries.ITEM, DRACHMA_ID, DRACHMA);
        CelestialBronzeIngot.init();

        CelestialBronzeSword.init();
        CelestialBronzeSpear.init();

        CelestialBronzeArmorMaterial.register();

        RiptideItem.register();
    }
}
