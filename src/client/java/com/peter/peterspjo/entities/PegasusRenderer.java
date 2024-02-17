package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PegasusRenderer extends GeoEntityRenderer<Pegasus> {

    public static void register() {
		EntityRendererRegistry.register(Pegasus.TYPE, (context) -> new PegasusRenderer(context));
		// EntityModelLayerRegistry.registerModelLayer(PegasusModel.LAYER, PegasusModel::getTexturedModelData);
    }

    public PegasusRenderer(Context context) {
        super(context, new GeoAnimatedModel<Pegasus>("pegasus"));
        // this.getModel().setVisible(true);
    }

    // @Override
    // public Identifier getTexture(Pegasus entity) {
    //     return new Identifier(PJO.NAMESPACE, "textures/entity/pegasus/pegasus_template.png");
    // }
}
