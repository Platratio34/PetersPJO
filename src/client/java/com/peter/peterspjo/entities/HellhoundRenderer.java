package com.peter.peterspjo.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;

import com.peter.peterspjo.PJO;

import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class HellhoundRenderer extends MobEntityRenderer<Hellhound, LivingEntityRenderState, HellhoundModel> {

    public static void register() {
		EntityRendererRegistry.register(Hellhound.TYPE, (context) -> new HellhoundRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(HellhoundModel.LAYER, HellhoundModel::getTexturedModelData);
    }

    public HellhoundRenderer(Context context) {
        super(context, new HellhoundModel(context.getPart(HellhoundModel.LAYER), (Hellhound)context.getRenderDispatcher().targetedEntity), 0.5f);
        // this.getModel().setVisible(true);
        
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState renderState) {
        return PJO.id("textures/entity/hellhound/hellhound.png");
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

}
