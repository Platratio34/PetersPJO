package com.peter.peterspjo;

import com.peter.peterspjo.items.CelestialBronzeIngot;
import com.peter.peterspjo.items.CelestialBronzeSpear;
import com.peter.peterspjo.items.CelestialBronzeSword;

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
                entries.add(CelestialBronzeSword.ITEM);
                entries.add(CelestialBronzeSpear.ITEM);
            })
            .build();
    public static final Identifier MAIN_ID = new Identifier(PJO.NAMESPACE, "main_group");
    
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, MAIN_ID, MAIN);
    }
}
