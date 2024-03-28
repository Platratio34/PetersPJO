package com.peter.peterspjo.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CentaurRenderer extends GeoEntityRenderer<Centaur> {

    public static void register() {
		EntityRendererRegistry.register(Centaur.TYPE, (context) -> new CentaurRenderer(context));
    }

    public CentaurRenderer(Context context) {
        super(context, new CentaurGeoModel<Centaur>());
    }
}
