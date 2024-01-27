package com.peter.peterspjo;

import com.peter.peterspjo.entities.SpearEntity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PJOClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(SpearEntity.TYPE,
				(context) -> new SpearEntityRenderer(context));
	}
}