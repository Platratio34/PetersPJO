package com.peter.peterspjo.entities;

import org.jetbrains.annotations.Nullable;

import com.peter.peterspjo.PJO;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GeoAnimatedModel<T extends GeoAnimatable> extends GeoModel<T> {

    private final String modelName;
    private boolean turnsHead;

    public GeoAnimatedModel(String name) {
        modelName = name;
    }

    public GeoAnimatedModel(String name, boolean turnsHead) {
        modelName = name;
        this.turnsHead = turnsHead;
    }

	@Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        if (!this.turnsHead)
            return;

        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
    
    @Override
    public Identifier getModelResource(T animatable, @Nullable GeoRenderer<T> renderer) {
        return PJO.id("geo/" + modelName + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(T animatable, @Nullable GeoRenderer<T> renderer) {
        return PJO.id("textures/entity/" + modelName + "/" + modelName + ".png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return PJO.id("animations/" + modelName + ".animation.json");
    }

}
