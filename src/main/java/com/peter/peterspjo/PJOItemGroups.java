package com.peter.peterspjo;

import com.peter.peterspjo.items.PJOItems;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PJOItemGroups {

    public static final ItemGroup MAIN = FabricItemGroup.builder()
            .icon(() -> new ItemStack(PJOItems.DRACHMA))
            .displayName(Text.translatable("itemGroup.peterspjo.main"))
            .entries((context, entries) -> {
                entries.add(PJOItems.DRACHMA);
                entries.add(PJOItems.CELESTIAL_BRONZE_INGOT);
                entries.add(PJOItems.CELESTIAL_BRONZE_BLOCK);
                entries.add(PJOItems.CELESTIAL_BRONZE_SWORD);
                entries.add(PJOItems.CELESTIAL_BRONZE_SPEAR);
                entries.add(PJOItems.CELESTIAL_BRONZE_HELMET);
                entries.add(PJOItems.CELESTIAL_BRONZE_CHESTPLATE);
                entries.add(PJOItems.CELESTIAL_BRONZE_LEGGINGS);
                entries.add(PJOItems.CELESTIAL_BRONZE_BOOTS);
                entries.add(PJOItems.IRON_BRAZIER);
                entries.add(PJOItems.STONE_BRAZIER);
                entries.add(PJOItems.EMPOUSAI_SPAWN_EGG);
                entries.add(PJOItems.HELLHOUND_SPAWN_EGG);
            })
            .build();
    public static final Identifier MAIN_ID = new Identifier(PJO.NAMESPACE, "main_group");
    
    public static final ItemGroup SPECIAL_WEAPONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(PJOItems.RIPTIDE))
            .displayName(Text.translatable("itemGroup.peterspjo.special_weapons"))
            .entries((context, entries) -> {
                entries.add(PJOItems.RIPTIDE);
            })
            .build();
    public static final Identifier SPECIAL_WEAPONS_ID = new Identifier(PJO.NAMESPACE, "special_weapons");
    
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, MAIN_ID, MAIN);
        Registry.register(Registries.ITEM_GROUP, SPECIAL_WEAPONS_ID, SPECIAL_WEAPONS);
    }
}
