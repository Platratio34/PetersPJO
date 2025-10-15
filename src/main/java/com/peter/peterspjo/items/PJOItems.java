package com.peter.peterspjo.items;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.blocks.IronBrazier;
import com.peter.peterspjo.blocks.LabyrinthDoor;
import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.blocks.StoneBrazier;
import com.peter.peterspjo.blocks.UnderworldSandDarkBlock;
import com.peter.peterspjo.blocks.fluids.StyxWater;
import com.peter.peterspjo.entities.Centaur;
import com.peter.peterspjo.entities.Empousai;
import com.peter.peterspjo.entities.Hellhound;
import com.peter.peterspjo.entities.Pegasus;
import com.peter.peterspjo.items.armor.CelestialBronzeArmorMaterial;

import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PJOItems {
    
    public static final Identifier DRACHMA_ID = PJO.id("drachma");
    public static final Item DRACHMA = Registry.register(Registries.ITEM, DRACHMA_ID, new TooltipedItem(new Item.Settings(), PJO.tooltip("item", "drachma").formatted(Formatting.GOLD), DRACHMA_ID));

    public static final CelestialBronzeIngot CELESTIAL_BRONZE_INGOT = CelestialBronzeIngot.ITEM;
    public static final CelestialBronzeSword CELESTIAL_BRONZE_SWORD = CelestialBronzeSword.ITEM;
    public static final CelestialBronzeSpear CELESTIAL_BRONZE_SPEAR = CelestialBronzeSpear.ITEM;
    public static final CelestialBronzeDagger CELESTIAL_BRONZE_DAGGER = CelestialBronzeDagger.ITEM;
    public static final Item CELESTIAL_BRONZE_HELMET = CelestialBronzeArmorMaterial.HELMET;
    public static final Item CELESTIAL_BRONZE_CHESTPLATE = CelestialBronzeArmorMaterial.CHESTPLATE;
    public static final Item CELESTIAL_BRONZE_LEGGINGS = CelestialBronzeArmorMaterial.LEGGINGS;
    public static final Item CELESTIAL_BRONZE_BOOTS = CelestialBronzeArmorMaterial.BOOTS;
    
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
        CelestialBronzeIngot.init();

        CelestialBronzeSword.init();
        CelestialBronzeSpear.init();

        RiptideItem.register();
    }

    public static RegistryKey<Item> registryKey(Identifier id) {
        return RegistryKey.of(RegistryKeys.ITEM, id);
    }
    public static <T extends Item> T registerItem(Identifier id, T item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
