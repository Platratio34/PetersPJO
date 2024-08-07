package com.peter.peterspjo.entities;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class EmpousaiModel extends BipedEntityModel<Empousai> {

    public static final EntityModelLayer LAYER = new EntityModelLayer(Empousai.ID, "main");

    @SuppressWarnings("unused")
    private final List<ModelPart> parts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;

    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_pants";
    private static final String RIGHT_PANTS = "right_pants";

    public EmpousaiModel(ModelPart root) {
        super(root);
        this.leftSleeve = root.getChild(LEFT_SLEEVE);
        this.rightSleeve = root.getChild(RIGHT_SLEEVE);
        this.leftPants = root.getChild(LEFT_PANTS);
        this.rightPants = root.getChild(RIGHT_PANTS);
        this.jacket = root.getChild(EntityModelPartNames.JACKET);
        this.parts = root.traverse().filter(part -> !part.isEmpty()).collect(ImmutableList.toImmutableList());
    }

    @Override
    public void setAngles(Empousai livingEntity, float f, float g, float h, float i, float j) {
        super.setAngles(livingEntity, f, g, h, i, j);
        this.leftPants.copyTransform(this.leftLeg);
        this.rightPants.copyTransform(this.rightLeg);
        this.leftSleeve.copyTransform(this.leftArm);
        this.rightSleeve.copyTransform(this.rightArm);
        this.jacket.copyTransform(this.body);
    }

    public static TexturedModelData getTexturedModelData() {
        Dilation dilation = Dilation.NONE;
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0f);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f,
                12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(-3.0f, -2.0f, -2.0f, 4.0f,
                12.0f, 4.0f, dilation.add(0.25f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create().uv(16, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(LEFT_PANTS,
                ModelPartBuilder.create().uv(0, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(RIGHT_PANTS,
                ModelPartBuilder.create().uv(0, 32).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.JACKET,
                ModelPartBuilder.create().uv(16, 32).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
        // ModelData modelData = new ModelData();
        // ModelPartData modelPartData = modelData.getRoot();
        // ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY,
        // ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F,
        // 4.0F),
        // ModelTransform.pivot(0F, 0F, 0F));
        // body.addChild(EntityModelPartNames.HEAD,
        // ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F,
        // 8.0F),
        // ModelTransform.pivot(0F, 24F, 0F));

        // body.addChild(EntityModelPartNames.LEFT_LEG,
        // ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 12.0F,
        // 4.0F),
        // ModelTransform.pivot(0F, 12F, 0F));
        // body.addChild(EntityModelPartNames.RIGHT_LEG,
        // ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F,
        // 4.0F),
        // ModelTransform.pivot(0F, 12F, 0F));

        // body.addChild(EntityModelPartNames.LEFT_ARM,
        // ModelPartBuilder.create().uv(16, 32).cuboid(-1.0F, -23.0F, -2.0F, 3.0F,
        // 12.0F, 4.0F),
        // ModelTransform.pivot(5F, 23F, 0F));
        // body.addChild(EntityModelPartNames.RIGHT_ARM,
        // ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, -23.0F, -2.0F, 3.0F, 12.0F,
        // 4.0F),
        // ModelTransform.pivot(-5F, 23F, 0F));
        // return TexturedModelData.of(modelData, 64, 64);
    }
    
    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.jacket));
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
    }
}
