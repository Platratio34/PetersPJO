package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PegasusRenderer extends MobEntityRenderer<Pegasus, PegasusModel> {

    public static void register() {
		EntityRendererRegistry.register(Pegasus.TYPE, (context) -> new PegasusRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(PegasusModel.LAYER, PegasusModel::getTexturedModelData);
    }

    public PegasusRenderer(Context context) {
        super(context, new PegasusModel(context.getPart(PegasusModel.LAYER)), 0.5f);
        // this.getModel().setVisible(true);
    }

    @Override
    public Identifier getTexture(Pegasus entity) {
        return new Identifier(PJO.NAMESPACE, "textures/entity/pegasus/pegasus_template.png");
    }
}
