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
                ModelPartBuilder.create().uv(64, 17).cuboid(-9.0F, 0.0F, -5.0F, 9.0F, 1.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-9.0F, -1.0F, 0.0F));

        ModelPartData wingRTip = wingRMid.addChild(EntityModelPartNames.RIGHT_WING_TIP,
                ModelPartBuilder.create().uv(64, 31).cuboid(-7.0F, -1.0F, -4.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-9.0F, 1.0F, 0.0F));

        ModelPartData wingLBase = modelPartData.addChild(EntityModelPartNames.LEFT_WING_BASE,
                ModelPartBuilder.create().uv(64, 0).mirrored()
                        .cuboid(0.0F, -1.0F, -7.0F, 9.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(5.0F, 4.0F, 0.0F));

        ModelPartData wingLMid = wingLBase.addChild(EntityModelPartNames.LEFT_WING + "_mid",
                ModelPartBuilder.create().uv(64, 17).mirrored()
                        .cuboid(0.0F, 0.0F, -5.0F, 9.0F, 1.0F, 13.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(9.0F, -1.0F, 0.0F));

        ModelPartData wingLTip = wingLMid.addChild(EntityModelPartNames.LEFT_WING_TIP,
                ModelPartBuilder.create().uv(64, 31).mirrored()
                        .cuboid(0.0F, -1.0F, -4.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(9.0F, 1.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(Pegasus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, netHeadYaw, ageInTicks, headPitch);
        wingRightBase.roll = (float)Math.toRadians(-85);
        wingLeftBase.roll = (float)Math.toRadians(85);
        
        wingRightMid.roll = (float)Math.toRadians(175);
        wingLeftMid.roll = (float)Math.toRadians(-175);
        
        wingRightTip.roll = (float)Math.toRadians(-175);
        wingLeftTip.roll = (float)Math.toRadians(175);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        super.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        wingRightBase.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        // wingRightMid.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        // wingLeftMid.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        wingLeftBase.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        // wingRightTip.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        // wingLeftTip.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
