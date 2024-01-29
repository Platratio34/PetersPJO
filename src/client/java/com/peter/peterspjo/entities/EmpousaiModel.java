package com.peter.peterspjo.entities;

import org.spongepowered.include.com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

public class EmpousaiModel extends EntityModel<Empousai> {

    public static final EntityModelLayer LAYER = new EntityModelLayer(Empousai.ID, "main");

    private final ModelPart base;
    private final ModelPart head;

    public EmpousaiModel(ModelPart modelPart) {
        this.base = modelPart.getChild(EntityModelPartNames.BODY);
        head = base.getChild(EntityModelPartNames.HEAD);
    }

    @Override
    public void setAngles(Empousai entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        head.pivotX = headPitch;
        head.pivotY = headYaw;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
            float blue,
            float alpha) {
        ImmutableList.of(this.base).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        // modelPartData.addChild(EntityModelPartNames.CUBE,
        // ModelPartBuilder.create().uv(0, 0).cuboid(-6F, 12F, -6F, 12F, 12F, 12F),
        // ModelTransform.pivot(0F, 0F, 0F));
        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
                ModelTransform.pivot(0F, 0F, 0F));
        body.addChild(EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                ModelTransform.pivot(0F, 24F, 0F));

        body.addChild(EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(0F, 12F, 0F));
        body.addChild(EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(0F, 12F, 0F));

        body.addChild(EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(16, 32).cuboid(-1.0F, -23.0F, -2.0F, 3.0F, 12.0F, 4.0F),
                ModelTransform.pivot(5F, 23F, 0F));
        body.addChild(EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, -23.0F, -2.0F, 3.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-5F, 23F, 0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

}
