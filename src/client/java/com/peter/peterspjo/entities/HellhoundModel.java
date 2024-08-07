package com.peter.peterspjo.entities;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class HellhoundModel extends EntityModel<Hellhound> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Hellhound.ID, "main");
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leg_rf;
    private final ModelPart leg_lf;
    private final ModelPart leg_rb;
    private final ModelPart leg_lb;

    private float leaningPitch;

    public HellhoundModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leg_rf = root.getChild("leg_rf");
        this.leg_lf = root.getChild("leg_lf");
        this.leg_rb = root.getChild("leg_rb");
        this.leg_lb = root.getChild("leg_lb");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        @SuppressWarnings("unused")
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -28.0F,
                -15.0F, 20.0F, 17.0F, 30.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        @SuppressWarnings("unused")
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 82).cuboid(-6.0F, -5.0F,
                -8.0F, 12.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -15.0F));

        @SuppressWarnings("unused")
        ModelPartData leg_rf = modelPartData.addChild("leg_rf",
                ModelPartBuilder.create().uv(0, 57).cuboid(-3.0F, -3.0F, -3.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-10.0F, 9.0F, -10.0F));

        @SuppressWarnings("unused")
        ModelPartData leg_lf = modelPartData.addChild("leg_lf",
                ModelPartBuilder.create().uv(20, 57).cuboid(-2.0F, -3.0F, -3.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(10.0F, 9.0F, -10.0F));

        @SuppressWarnings("unused")
        ModelPartData leg_rb = modelPartData.addChild("leg_rb",
                ModelPartBuilder.create().uv(60, 57).cuboid(-3.0F, -3.0F, -2.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-10.0F, 9.0F, 10.0F));

        @SuppressWarnings("unused")
        ModelPartData leg_lb = modelPartData.addChild("leg_lb",
                ModelPartBuilder.create().uv(40, 57).cuboid(-2.0F, -3.0F, -2.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(10.0F, 9.0F, 10.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void animateModel(Hellhound entity, float limbAngle, float limbDistance, float tickDelta) {
        this.leaningPitch = entity.getLeaningPitch(tickDelta);
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
    }

    @Override
    public void setAngles(Hellhound entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw,
            float headPitch) {
        boolean rolled = false; // TODO was change from entity.getRoll() > 4 correct?
        this.head.yaw = headYaw * ((float) Math.PI / 180);
        this.head.pitch = rolled ? -0.7853982f
                : (this.leaningPitch > 0.0f
                        ? (this.lerpAngle(this.leaningPitch, this.head.pitch, headPitch * ((float) Math.PI / 180)))
                        : headPitch * ((float) Math.PI / 180));

        float limbSpeed = 1.0f;
        if (rolled) {
            limbSpeed = (float) entity.getVelocity().lengthSquared();
            limbSpeed /= 0.2f;
            limbSpeed *= limbSpeed * limbSpeed;
        }
        if (limbSpeed < 1.0f) {
            limbSpeed = 1.0f;
        }

        this.leg_rf.pitch = 0.5f * MathHelper.cos((float) (limbSwing * 0.6662f)) * 1.4f * limbSwingAmount / limbSpeed;
        this.leg_rb.pitch = 0.5f * MathHelper.cos((float) (limbSwing * 0.6662f + (float) Math.PI)) * 1.4f
                * limbSwingAmount / limbSpeed;
        this.leg_lf.pitch = 0.5f * MathHelper.cos((float) (limbSwing * 0.6662f + (float) Math.PI)) * 1.4f
                * limbSwingAmount / limbSpeed;
        this.leg_lb.pitch = 0.5f * MathHelper.cos((float) (limbSwing * 0.6662f)) * 1.4f * limbSwingAmount / limbSpeed;

        this.leg_rf.yaw = 0.005f;
        this.leg_rb.yaw = -0.005f;
        this.leg_lf.yaw = -0.005f;
        this.leg_lb.yaw = 0.005f;

        this.leg_rf.roll = 0.005f;
        this.leg_rb.roll = -0.005f;
        this.leg_lf.roll = -0.005f;
        this.leg_rb.roll = 0.005f;
    }

    protected float lerpAngle(float angleOne, float angleTwo, float magnitude) {
        float f = (magnitude - angleTwo) % ((float) Math.PI * 2);
        if (f < (float) (-Math.PI)) {
            f += (float) Math.PI * 2;
        }
        if (f >= (float) Math.PI) {
            f -= (float) Math.PI * 2;
        }
        return angleTwo + angleOne * f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        body.render(matrices, vertexConsumer, light, overlay, color);
        head.render(matrices, vertexConsumer, light, overlay, color);
        leg_rf.render(matrices, vertexConsumer, light, overlay, color);
        leg_lf.render(matrices, vertexConsumer, light, overlay, color);
        leg_rb.render(matrices, vertexConsumer, light, overlay, color);
        leg_lb.render(matrices, vertexConsumer, light, overlay, color);
    }
}