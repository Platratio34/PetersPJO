package com.peter.peterspjo.entities;

import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;

public class PegasusGeoModel<T extends Pegasus> extends GeoAnimatedModel<T> {

    private static final String BAG_LEFT = "bag_left";
    private static final String BAG_RIGHT = "bag_right";
    private static final String SADDLE = "saddle";
    private static final String SADDLE_MOUTH_LEFT = "saddle_mouth_left";
    private static final String SADDLE_MOUTH_RIGHT = "saddle_mouth_right";
    private static final String SADDLE_LINE_LEFT = "saddle_line_left";
    private static final String SADDLE_LINE_RIGHT = "saddle_line_right";
    private static final String SADDLE_HEAD = "saddle_head";

    private GeoBone bagLeft;
    private GeoBone bagRight;
    private GeoBone saddle;
    private GeoBone[] saddleParts;

    public final Pegasus entity;

    public PegasusGeoModel(Pegasus entity) {
        super(Pegasus.NAME);
        this.entity = entity;
    }

    private void getBones() {
        if (bagLeft != null)
            return;
        bagLeft = getBone(BAG_LEFT).get();
        bagRight = getBone(BAG_RIGHT).get();
        saddle = getBone(SADDLE).get();
        saddleParts = new GeoBone[] {
                getBone(SADDLE_MOUTH_LEFT).get(),
                getBone(SADDLE_MOUTH_RIGHT).get(),
                getBone(SADDLE_LINE_LEFT).get(),
                getBone(SADDLE_LINE_RIGHT).get(),
                getBone(SADDLE_HEAD).get()
        };
    }

    @Override
    public void setCustomAnimations(T entity, long instanceId, AnimationState<T> animationState) {
        getBones();
        bagLeft.setHidden(!entity.hasChest());
        bagRight.setHidden(!entity.hasChest());
        saddle.setHidden(!entity.isSaddled());
        for (int i = 0; i < saddleParts.length; i++) {
            saddleParts[i].setHidden(true);
        }
        super.setCustomAnimations(entity, instanceId, animationState);
    }
}
