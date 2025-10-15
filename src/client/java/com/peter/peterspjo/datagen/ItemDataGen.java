package com.peter.peterspjo.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.render.item.model.ItemModel;
import com.peter.peterspjo.PJO;
import com.peter.peterspjo.items.PJOItems;

public class ItemDataGen extends FabricModelProvider {

    public ItemDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        // ItemModelOutput riptideModel;
        // // ItemModelGenerator riptideModelGenerator = new ItemModelGenerator(riptideModel, null);
		// ItemModel.Unbaked unbaked = ItemModels.basic(itemModelGenerator.upload(RiptideItem.ITEM, Models.GENERATED));
		// ItemModel.Unbaked unbaked2 = ItemModels.basic(itemModelGenerator.registerSubModel(RiptideItem.ITEM, "_sword", Models.GENERATED));
        // itemModelGenerator.registerCondition(RiptideItem.ITEM, SwitchableSword.IS_SWORD_COMPONENT, unbaked, unbaked2);

        // itemModelGenerator.registerSpawnEgg(Centaur.EGG, 0x72460d, 0xFFFF00);
        // itemModelGenerator.registerSpawnEgg(Empousai.EGG, 0x5b276c, 0xfdff2f);
        // itemModelGenerator.registerSpawnEgg(Hellhound.EGG, 0x2c2c2c, 0x232323);
        // itemModelGenerator.registerSpawnEgg(Pegasus.EGG, 0xC09E7D, 0xEEE500);
        
        
        // ItemModel.Unbaked unbacked = ItemModels.basic(PJO.id("item/labyrinth_door"));
        // unbacked.
        itemModelGenerator.register(PJOItems.LABYRINTH_DOOR);
        // itemModelGenerator.register(PJOItems.LABYRINTH_DOOR, unbacked.bake(null));
    }


}
