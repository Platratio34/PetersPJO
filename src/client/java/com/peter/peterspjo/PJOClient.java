package com.peter.peterspjo;

import com.peter.peterspjo.entities.EmpousaiRenderer;
import com.peter.peterspjo.entities.SpearEntity;
import com.peter.peterspjo.items.RiptideItem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
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
	}
}