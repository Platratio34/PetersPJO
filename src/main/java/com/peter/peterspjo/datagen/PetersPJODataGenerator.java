package com.peter.peterspjo.datagen;

import java.util.function.Consumer;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.PJOBlocks;
import com.peter.peterspjo.items.CelestialBronzeIngot;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.advancement.*;

public class PetersPJODataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(AdvancementProvider::new);
    }

    static class AdvancementProvider extends FabricAdvancementProvider {

        protected AdvancementProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
            
            Advancement root = Advancement.Builder.create()
                    .display(CelestialBronzeIngot.ITEM,
                            Text.translatable("advancement." + PJO.NAMESPACE + ".got_cb.title"),
                            Text.translatable("advancement." + PJO.NAMESPACE + ".got_cb.description"),
                            new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementFrame.TASK, true, true, false)
                    .criterion(PJO.NAMESPACE+":got_cb", InventoryChangedCriterion.Conditions.items(CelestialBronzeIngot.ITEM))
                    .build(consumer, PJO.NAMESPACE + "/root");
        }

    }

}
