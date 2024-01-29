package com.peter.peterspjo;

import com.peter.peterspjo.blocks.IronBrazier;
import com.peter.peterspjo.blocks.StoneBrazier;
import com.peter.peterspjo.entities.Empousai;
import com.peter.peterspjo.items.CelestialBronzeIngot;
import com.peter.peterspjo.items.CelestialBronzeSpear;
import com.peter.peterspjo.items.CelestialBronzeSword;
import com.peter.peterspjo.items.Items;
import com.peter.peterspjo.items.armor.CelestialBronzeArmorMaterial;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {

    public static final ItemGroup MAIN = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.DRACHMA))
            .displayName(Text.translatable("itemGroup.peterspjo.main"))
            .entries((context, entries) -> {
                entries.add(Items.DRACHMA);
                entries.add(CelestialBronzeIngot.ITEM);
                entries.add(Blocks.CELESTIAL_BRONZE_BLOCK_ITEM);
                entries.add(CelestialBronzeSword.ITEM);
                entries.add(CelestialBronzeSpear.ITEM);
                entries.add(CelestialBronzeArmorMaterial.HELMET);
                entries.add(CelestialBronzeArmorMaterial.CHESTPLATE);
                entries.add(CelestialBronzeArmorMaterial.LEGGINGS);
                entries.add(CelestialBronzeArmorMaterial.BOOTS);
                entries.add(IronBrazier.ITEM);
                entries.add(StoneBrazier.ITEM);
                entries.add(Empousai.EGG);
            })
            .build();
    public static final Identifier MAIN_ID = new Identifier(PJO.NAMESPACE, "main_group");
    
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, MAIN_ID, MAIN);
    }
}
