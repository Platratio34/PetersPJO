package com.peter.peterspjo;

import com.peter.peterspjo.blocks.fluids.StyxWater;
import com.peter.peterspjo.entities.EmpousaiRenderer;
import com.peter.peterspjo.entities.SpearEntity;
import com.peter.peterspjo.items.RiptideItem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class PJOClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(SpearEntity.TYPE, (context) -> new SpearEntityRenderer(context));
		EmpousaiRenderer.register();

		ModelPredicateProviderRegistry.register(RiptideItem.ITEM, new Identifier("is_sword"),
				(itemStack, clientWorld, livingEntity, i) -> {
					NbtCompound nbt = itemStack.getNbt();
					if (livingEntity == null || nbt == null) {
						return 0.0F;
					}
					return nbt.getBoolean("is_sword") ? 1.0f : 0.0f;
				});
		
		FluidRenderHandlerRegistry.INSTANCE.register(StyxWater.STILL, StyxWater.FLOWING, new SimpleFluidRenderHandler(
				new Identifier("minecraft:block/water_still"), new Identifier("minecraft:block/water_flow"), 0xA16e4400));
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), StyxWater.STILL, StyxWater.FLOWING);
	}
}