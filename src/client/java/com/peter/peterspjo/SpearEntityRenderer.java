package com.peter.peterspjo;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;

import com.peter.peterspjo.entities.SpearEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends ProjectileEntityRenderer<SpearEntity> {

    public static void register() {
        EntityRendererRegistry.register(SpearEntity.TYPE, (context) -> new SpearEntityRenderer(context));
    }

    public SpearEntityRenderer(Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SpearEntity var1) {
        return Identifier.of("textures/entity/"+SpearEntity.NAME+"/"+SpearEntity.NAME+".png");
    }

}
