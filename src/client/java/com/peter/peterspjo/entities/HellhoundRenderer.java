package com.peter.peterspjo.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;

import com.peter.peterspjo.PJO;

import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class HellhoundRenderer extends MobEntityRenderer<Hellhound, HellhoundModel> {

    public static void register() {
		EntityRendererRegistry.register(Hellhound.TYPE, (context) -> new HellhoundRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(HellhoundModel.LAYER, HellhoundModel::getTexturedModelData);
    }

    public HellhoundRenderer(Context context) {
        super(context, new HellhoundModel(context.getPart(HellhoundModel.LAYER)), 0.5f);
        // this.getModel().setVisible(true);
    }

    @Override
    public Identifier getTexture(Hellhound entity) {
        return new Identifier(PJO.NAMESPACE, "textures/entity/hellhound/hellhound.png");
    }

}
