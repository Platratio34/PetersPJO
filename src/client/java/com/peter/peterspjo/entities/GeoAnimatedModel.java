package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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

		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

    @Override
    public Identifier getModelResource(T animatable) {
        return new Identifier(PJO.NAMESPACE, "geo/" + modelName + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(T animatable) {
        return new Identifier(PJO.NAMESPACE, "textures/entity/" + modelName + "/" + modelName + ".png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return new Identifier(PJO.NAMESPACE, "animations/" + modelName + ".animation.json");
    }

}
