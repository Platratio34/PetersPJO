package com.peter.peterspjo;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;

import com.peter.peterspjo.entities.SpearEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends ProjectileEntityRenderer<SpearEntity> {

    public SpearEntityRenderer(Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SpearEntity var1) {
        return Identifier.of(PJO.NAMESPACE, "textures/entity/"+SpearEntity.NAME+"/"+SpearEntity.NAME+".png");
    }

}
