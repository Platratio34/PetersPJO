package com.peter.peterspjo.entities;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PegasusModel extends HorseEntityModel<Pegasus> {

    public static final EntityModelLayer LAYER = new EntityModelLayer(Pegasus.ID, "main");

    // private final ModelPart Body;
    // private final ModelPart TailA;
    // private final ModelPart Leg1A;
    // private final ModelPart Leg2A;
    // private final ModelPart Leg3A;
    // private final ModelPart Leg4A;
    // private final ModelPart Head;
    // private final ModelPart Ear1;
    // private final ModelPart Ear2;
    // private final ModelPart MuleEarL;
    // private final ModelPart MuleEarR;
    // private final ModelPart Neck;
    // private final ModelPart Bag1;
    // private final ModelPart Bag2;
    // private final ModelPart Saddle;
    // private final ModelPart SaddleMouthL;
    // private final ModelPart SaddleMouthR;
    // private final ModelPart SaddleMouthLine;
    // private final ModelPart SaddleMouthLineR;
    // private final ModelPart HeadSaddle;
    private final ModelPart wingRightBase;
    private final ModelPart wingRightMid;
    private final ModelPart wingRightTip;
    private final ModelPart wingLeftBase;
    private final ModelPart wingLeftMid;
    private final ModelPart wingLeftTip;

    public PegasusModel(ModelPart root) {
        super(root);
        this.wingRightBase = root.getChild(EntityModelPartNames.RIGHT_WING_BASE);
        this.wingRightMid = wingRightBase.getChild(EntityModelPartNames.RIGHT_WING + "_mid");
        this.wingRightTip = wingRightMid.getChild(EntityModelPartNames.RIGHT_WING_TIP);
        this.wingLeftBase = root.getChild(EntityModelPartNames.LEFT_WING_BASE);
        this.wingLeftMid = wingLeftBase.getChild(EntityModelPartNames.LEFT_WING + "_mid");
        this.wingLeftTip = wingLeftMid.getChild(EntityModelPartNames.LEFT_WING_TIP);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = HorseEntityModel.getModelData(new Dilation(0.0F));
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData wingRBase = modelPartData.addChild(EntityModelPartNames.RIGHT_WING_BASE,
                ModelPartBuilder.create().uv(64, 0).cuboid(-9.0F, -1.0F, -7.0F, 9.0F, 1.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-5.0F, 4.0F, 0.0F));

        ModelPartData wingRMid = wingRBase.addChild(EntityModelPartNames.RIGHT_WING + "_mid",
                ModelPartBuilder.create().uv(64, 17).cuboid(-9.0F, -1.0F, -5.0F, 9.0F, 1.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-9.0F, 0.0F, 0.0F));

        @SuppressWarnings("unused")
        ModelPartData wingRTip = wingRMid.addChild(EntityModelPartNames.RIGHT_WING_TIP,
                ModelPartBuilder.create().uv(64, 31).cuboid(-7.0F, 0.0F, -4.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-9.0F, 0.0F, 0.0F));

        ModelPartData wingLBase = modelPartData.addChild(EntityModelPartNames.LEFT_WING_BASE,
                ModelPartBuilder.create().uv(64, 0).mirrored()
                        .cuboid(0.0F, -1.0F, -7.0F, 9.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(5.0F, 4.0F, 0.0F));

        ModelPartData wingLMid = wingLBase.addChild(EntityModelPartNames.LEFT_WING + "_mid",
                ModelPartBuilder.create().uv(64, 17).mirrored()
                        .cuboid(0.0F, -1.0F, -5.0F, 9.0F, 1.0F, 13.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(9.0F, 0.0F, 0.0F));

        @SuppressWarnings("unused")
        ModelPartData wingLTip = wingLMid.addChild(EntityModelPartNames.LEFT_WING_TIP,
                ModelPartBuilder.create().uv(64, 31).mirrored()
                        .cuboid(0.0F, 0.0F, -4.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(9.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(Pegasus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, netHeadYaw, ageInTicks, headPitch);
    }

    private static final double GROUND_TIME_TO_FOLD = 1;
    private static final double PI2 = Math.PI * 2;

    private static final double TIP_LERP_TIME = 1.5;
    private static final double MID_LERP_TIME = 1;
    private static final double BASE_LERP_TIME = 2;

    private double wingTime = 0;
    private double foldTime = 1;
    private double groundTime = GROUND_TIME_TO_FOLD + 10;

    @Override
    public void animateModel(Pegasus entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        double wb = 0;
        double wm = 0;
        double wt = 0;
        // System.out.println(limbAngle + "," + limbDistance + "," + tickDelta);
        double dWT = (tickDelta * 0.02 * PI2);
        if (!entity.isOnGround()) {
            if (wingTime > Math.PI) {
                wingTime += dWT * 2;
            } else {
                wingTime += dWT;
            }
        } else if (wingTime <= Math.PI) {
            wingTime = Math.max(wingTime - (dWT * 0.3), 0);
        } else {
            wingTime = Math.min(wingTime + (dWT * 0.3), PI2);
        }
        wingTime %= PI2;
        wb = Math.sin(wingTime - 0.2) * 20;
        wm = Math.sin(wingTime - 0.3) * 30;
        wt = Math.sin(wingTime - 0.4) * 35 + 5;

        if (entity.isOnGround()) {
            groundTime += (tickDelta * 0.3);
            foldTime = Math.min(Math.max(0, groundTime - GROUND_TIME_TO_FOLD) * 0.5, 2);
        } else if (foldTime > 0) {
            groundTime = 0;
            foldTime = Math.max(foldTime - (tickDelta * 0.5), 0);
        }
        if (foldTime > 0) {
            double timeTip = Math.min(foldTime, TIP_LERP_TIME) / TIP_LERP_TIME;
            double tITip = Math.max(1 - timeTip, 0);
            double timeMid = Math.min(foldTime, MID_LERP_TIME) / MID_LERP_TIME;
            double tIMid = Math.max(1 - timeMid, 0);
            double timeBase = Math.min(foldTime, BASE_LERP_TIME) / BASE_LERP_TIME;
            double tIBase = Math.max(1 - timeBase, 0);
            wb = (-85 * timeBase) + (wb * tIBase);
            wm = (-175 * timeMid) + (wm * tIMid);
            wt = (175 * timeTip) + (wt * tITip);
        }

        wingRightBase.roll = (float) Math.toRadians(wb);
        wingLeftBase.roll = (float) Math.toRadians(-wb);

        wingRightMid.roll = (float) Math.toRadians(wm);
        wingLeftMid.roll = (float) Math.toRadians(-wm);

        wingRightTip.roll = (float) Math.toRadians(wt);
        wingLeftTip.roll = (float) Math.toRadians(-wt);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        super.render(matrices, vertexConsumer, light, overlay, color);
        wingRightBase.render(matrices, vertexConsumer, light, overlay, color);
        // wingRightMid.render(matrices, vertexConsumer, light, overlay, red, green,
        // blue, alpha);
        // wingLeftMid.render(matrices, vertexConsumer, light, overlay, red, green,
        // blue, alpha);
        wingLeftBase.render(matrices, vertexConsumer, light, overlay, color);
        // wingRightTip.render(matrices, vertexConsumer, light, overlay, red, green,
        // blue, alpha);
        // wingLeftTip.render(matrices, vertexConsumer, light, overlay, red, green,
        // blue, alpha);
    }
}
