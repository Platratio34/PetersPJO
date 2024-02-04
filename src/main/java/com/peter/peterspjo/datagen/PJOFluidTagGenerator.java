package com.peter.peterspjo.datagen;

import java.util.concurrent.CompletableFuture;

import com.peter.peterspjo.blocks.fluids.PJOFluidTags;
import com.peter.peterspjo.blocks.fluids.StyxWater;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.FluidTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class PJOFluidTagGenerator extends FluidTagProvider {

    public PJOFluidTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(WrapperLookup arg) {
        getOrCreateTagBuilder(PJOFluidTags.STYX)
                .add(StyxWater.STILL)
                .add(StyxWater.FLOWING);
    }


}
