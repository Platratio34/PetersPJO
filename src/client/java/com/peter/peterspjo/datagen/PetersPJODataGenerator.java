package com.peter.peterspjo.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.entities.PJOEntities;
import com.peter.peterspjo.items.PJOItems;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.advancement.*;
import net.minecraft.advancement.AdvancementRequirements.CriterionMerger;

public class PetersPJODataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(PJOFluidTagGenerator::new);
        pack.addProvider(ItemDataGen::new);
    }

    static class AdvancementProvider extends FabricAdvancementProvider {

        protected AdvancementProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        private static MutableText advancementText(String name) {
            return Text.translatable(String.format("advancement.%s.%s", PJO.NAMESPACE, name));
        }

        @Override
        public void generateAdvancement(WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
            Identifier background = Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png");
            AdvancementEntry root = Advancement.Builder.create()
                    .display(PJOItems.CELESTIAL_BRONZE_INGOT,
                            advancementText("got_cb.title"),
                            advancementText("got_cb.description"),
                            background,
                            AdvancementFrame.TASK, true, true, false)
                    .criteriaMerger(CriterionMerger.OR)
                    .criterion(PJO.NAMESPACE + ":got_cb_ingot",
                            InventoryChangedCriterion.Conditions.items(PJOItems.CELESTIAL_BRONZE_INGOT))
                    .criterion(PJO.NAMESPACE + ":got_cb_block",
                            InventoryChangedCriterion.Conditions.items(PJOBlocks.CELESTIAL_BRONZE_BLOCK))
                    .build(consumer, PJO.NAMESPACE + "/root");
            Advancement.Builder killMonsterBuilder = Advancement.Builder.create().parent(root)
                    .display(PJOItems.CELESTIAL_BRONZE_SWORD,
                            advancementText("kill_monster.title"),
                            advancementText("kill_monster.description"),
                            background,
                            AdvancementFrame.TASK, true, true, false);
            AdvancementEntry killMonster = requireAnyListedMobKilled(killMonsterBuilder).build(consumer,
                    PJO.NAMESPACE + "/kill_monster");
            Advancement.Builder killAllMonsterBuilder = Advancement.Builder.create().parent(killMonster)
                    .display(PJOItems.CELESTIAL_BRONZE_SWORD,
                            advancementText("kill_all_monster.title"),
                            advancementText("kill_all_monster.description"),
                            background,
                            AdvancementFrame.CHALLENGE, true, true, false);
            @SuppressWarnings("unused")
            AdvancementEntry killAllMonster = requireAllListedMobsKilled(killAllMonsterBuilder).build(consumer,
                    PJO.NAMESPACE + "/kill_all_monster");
            @SuppressWarnings("unused")
            AdvancementEntry got_riptide = Advancement.Builder.create().parent(root)
                    .display(PJOItems.RIPTIDE,
                            advancementText("got_riptide.title"),
                            advancementText("got_riptide.description"),
                            background,
                            AdvancementFrame.GOAL, true, true, false)
                    .criterion(PJO.NAMESPACE + ":got_riptide",
                            InventoryChangedCriterion.Conditions.items(PJOItems.RIPTIDE))
                    .build(consumer, PJO.NAMESPACE + "/got_riptide");
        }

        private static Advancement.Builder requireAllListedMobsKilled(Advancement.Builder builder) {
            for (EntityType<?> entityType : PJOEntities.MONSTERS) {
                builder.criterion(
                        PJO.NAMESPACE + ":kill_"
                                + Registries.ENTITY_TYPE.getId(entityType).toString(),
                        OnKilledCriterion.Conditions
                                .createPlayerKilledEntity(
                                        EntityPredicate.Builder.create()
                                                /*.type(entityType)*/));
            }
            return builder;
        }

        private static Advancement.Builder requireAnyListedMobKilled(Advancement.Builder builder) {
            builder.criteriaMerger(CriterionMerger.OR);
            for (EntityType<?> entityType : PJOEntities.MONSTERS) {
                builder.criterion(
                        PJO.NAMESPACE + ":kill_" + Registries.ENTITY_TYPE.getId(entityType).toString(),
                        OnKilledCriterion.Conditions
                                .createPlayerKilledEntity(EntityPredicate.Builder.create()/*.type(entityType)*/));
            }
            return builder;
        }

    }

}
