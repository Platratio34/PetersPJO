package com.peter.peterspjo.items;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.blocks.*;
import com.peter.peterspjo.blocks.fluids.*;
import com.peter.peterspjo.entities.*;
import com.peter.peterspjo.items.armor.*;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
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
    public static final CelestialBronzeDagger CELESTIAL_BRONZE_DAGGER = CelestialBronzeDagger.ITEM;
    public static final ArmorItem CELESTIAL_BRONZE_HELMET = CelestialBronzeArmorMaterial.HELMET;
    public static final ArmorItem CELESTIAL_BRONZE_CHESTPLATE = CelestialBronzeArmorMaterial.CHESTPLATE;
    public static final ArmorItem CELESTIAL_BRONZE_LEGGINGS = CelestialBronzeArmorMaterial.LEGGINGS;
    public static final ArmorItem CELESTIAL_BRONZE_BOOTS = CelestialBronzeArmorMaterial.BOOTS;
    
    public static final RiptideItem RIPTIDE = RiptideItem.ITEM;

    public static final BlockItem CELESTIAL_BRONZE_BLOCK = PJOBlocks.CELESTIAL_BRONZE_BLOCK_ITEM;
    public static final BlockItem UNDERWORLD_SAND_DARK = UnderworldSandDarkBlock.ITEM;
    public static final BlockItem IRON_BRAZIER = IronBrazier.ITEM;
    public static final BlockItem STONE_BRAZIER = StoneBrazier.ITEM;

    public static final BlockItem LABYRINTH_DOOR = LabyrinthDoor.ITEM;

    public static final SpawnEggItem EMPOUSAI_SPAWN_EGG = Empousai.EGG;
    public static final SpawnEggItem HELLHOUND_SPAWN_EGG = Hellhound.EGG;
    public static final SpawnEggItem PEGASUS_SPAWN_EGG = Pegasus.EGG;
    public static final SpawnEggItem CENTAUR_SPAWN_EGG = Centaur.EGG;

    public static final BucketItem STYX_BUCKET = StyxWater.BUCKET;

    public static void init() {
        Registry.register(Registries.ITEM, DRACHMA_ID, DRACHMA);
        CelestialBronzeIngot.init();

        CelestialBronzeSword.init();
        CelestialBronzeSpear.init();

        CelestialBronzeArmorMaterial.register();

        RiptideItem.register();
    }
}
