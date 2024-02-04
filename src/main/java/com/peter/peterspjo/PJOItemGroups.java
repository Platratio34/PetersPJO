package com.peter.peterspjo;

import com.peter.peterspjo.blocks.IronBrazier;
import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.blocks.StoneBrazier;
import com.peter.peterspjo.entities.Empousai;
import com.peter.peterspjo.entities.Hellhound;
import com.peter.peterspjo.items.CelestialBronzeIngot;
import com.peter.peterspjo.items.CelestialBronzeSpear;
import com.peter.peterspjo.items.CelestialBronzeSword;
import com.peter.peterspjo.items.PJOItems;
import com.peter.peterspjo.items.RiptideItem;
import com.peter.peterspjo.items.armor.CelestialBronzeArmorMaterial;

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
                entries.add(CelestialBronzeIngot.ITEM);
                entries.add(PJOBlocks.CELESTIAL_BRONZE_BLOCK_ITEM);
                entries.add(CelestialBronzeSword.ITEM);
                entries.add(CelestialBronzeSpear.ITEM);
                entries.add(CelestialBronzeArmorMaterial.HELMET);
                entries.add(CelestialBronzeArmorMaterial.CHESTPLATE);
                entries.add(CelestialBronzeArmorMaterial.LEGGINGS);
                entries.add(CelestialBronzeArmorMaterial.BOOTS);
                entries.add(IronBrazier.ITEM);
                entries.add(StoneBrazier.ITEM);
                entries.add(Empousai.EGG);
                entries.add(Hellhound.EGG);
            })
            .build();
    public static final Identifier MAIN_ID = new Identifier(PJO.NAMESPACE, "main_group");
    
    public static final ItemGroup SPECIAL_WEAPONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RiptideItem.ITEM))
            .displayName(Text.translatable("itemGroup.peterspjo.special_weapons"))
            .entries((context, entries) -> {
                entries.add(RiptideItem.ITEM);
            })
            .build();
    public static final Identifier SPECIAL_WEAPONS_ID = new Identifier(PJO.NAMESPACE, "special_weapons");
    
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, MAIN_ID, MAIN);
        Registry.register(Registries.ITEM_GROUP, SPECIAL_WEAPONS_ID, SPECIAL_WEAPONS);
    }
}
