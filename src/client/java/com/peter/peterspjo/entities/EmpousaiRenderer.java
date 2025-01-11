package com.peter.peterspjo.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;

import com.peter.peterspjo.PJO;

import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;

public class EmpousaiRenderer extends MobEntityRenderer<Empousai, BipedEntityRenderState, EmpousaiModel> {

    public static void register() {
		EntityRendererRegistry.register(Empousai.TYPE, (context) -> new EmpousaiRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(EmpousaiModel.LAYER, EmpousaiModel::getTexturedModelData);
    }

    public EmpousaiRenderer(Context context) {
        super(context, new EmpousaiModel(context.getPart(EmpousaiModel.LAYER)), 0.5f);
        this.getModel().setVisible(true);
    }

    @Override
    public Identifier getTexture(BipedEntityRenderState renderState) {
        return PJO.id("textures/entity/empousai/empousai.png");
    }

    @Override
    public BipedEntityRenderState createRenderState() {
        return new BipedEntityRenderState();
    }

}
