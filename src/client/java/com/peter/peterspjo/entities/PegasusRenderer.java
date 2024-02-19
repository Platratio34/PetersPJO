package com.peter.peterspjo.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PegasusRenderer extends GeoEntityRenderer<Pegasus> {

    public static void register() {
		EntityRendererRegistry.register(Pegasus.TYPE, (context) -> new PegasusRenderer(context));
    }

    public PegasusRenderer(Context context) {
        super(context, new PegasusGeoModel<Pegasus>());
    }
}
