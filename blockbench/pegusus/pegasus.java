// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class pegasus extends EntityModel<Entity> {
	private final ModelPart Body;
	private final ModelPart TailA;
	private final ModelPart Leg1A;
	private final ModelPart Leg2A;
	private final ModelPart Leg3A;
	private final ModelPart Leg4A;
	private final ModelPart Head;
	private final ModelPart Ear1;
	private final ModelPart Ear2;
	private final ModelPart MuleEarL;
	private final ModelPart MuleEarR;
	private final ModelPart Neck;
	private final ModelPart Bag1;
	private final ModelPart Bag2;
	private final ModelPart Saddle;
	private final ModelPart SaddleMouthL;
	private final ModelPart SaddleMouthR;
	private final ModelPart SaddleMouthLine;
	private final ModelPart SaddleMouthLineR;
	private final ModelPart HeadSaddle;
	private final ModelPart WingR;
	private final ModelPart WingR2;
	private final ModelPart WingR3;
	private final ModelPart WingL;
	private final ModelPart WingL2;
	private final ModelPart WingL3;
	public pegasus(ModelPart root) {
		this.Body = root.getChild("Body");
		this.TailA = root.getChild("TailA");
		this.Leg1A = root.getChild("Leg1A");
		this.Leg2A = root.getChild("Leg2A");
		this.Leg3A = root.getChild("Leg3A");
		this.Leg4A = root.getChild("Leg4A");
		this.Head = root.getChild("Head");
		this.Ear1 = root.getChild("Ear1");
		this.Ear2 = root.getChild("Ear2");
		this.MuleEarL = root.getChild("MuleEarL");
		this.MuleEarR = root.getChild("MuleEarR");
		this.Neck = root.getChild("Neck");
		this.Bag1 = root.getChild("Bag1");
		this.Bag2 = root.getChild("Bag2");
		this.Saddle = root.getChild("Saddle");
		this.SaddleMouthL = root.getChild("SaddleMouthL");
		this.SaddleMouthR = root.getChild("SaddleMouthR");
		this.SaddleMouthLine = root.getChild("SaddleMouthLine");
		this.SaddleMouthLineR = root.getChild("SaddleMouthLineR");
		this.HeadSaddle = root.getChild("HeadSaddle");
		this.WingR = root.getChild("WingR");
		this.WingL = root.getChild("WingL");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Body = modelPartData.addChild("Body", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.0F, 9.0F));

		ModelPartData TailA = modelPartData.addChild("TailA", ModelPartBuilder.create().uv(42, 36).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.0F, 11.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData Leg1A = modelPartData.addChild("Leg1A", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 13.0F, 9.0F));

		ModelPartData Leg2A = modelPartData.addChild("Leg2A", ModelPartBuilder.create().uv(48, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 13.0F, 9.0F));

		ModelPartData Leg3A = modelPartData.addChild("Leg3A", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 13.0F, -9.0F));

		ModelPartData Leg4A = modelPartData.addChild("Leg4A", ModelPartBuilder.create().uv(48, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 13.0F, -9.0F));

		ModelPartData Head = modelPartData.addChild("Head", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 25).cuboid(-2.0F, -5.0F, -11.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, -11.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData Ear1 = modelPartData.addChild("Ear1", ModelPartBuilder.create().uv(19, 16).mirrored().cuboid(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0873F));

		ModelPartData Ear2 = modelPartData.addChild("Ear2", ModelPartBuilder.create().uv(19, 16).cuboid(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, -0.0873F));

		ModelPartData MuleEarL = modelPartData.addChild("MuleEarL", ModelPartBuilder.create().uv(0, 12).mirrored().cuboid(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.2618F));

		ModelPartData MuleEarR = modelPartData.addChild("MuleEarR", ModelPartBuilder.create().uv(0, 12).cuboid(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, -0.2618F));

		ModelPartData Neck = modelPartData.addChild("Neck", ModelPartBuilder.create().uv(0, 35).cuboid(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, new Dilation(0.0F))
		.uv(56, 36).cuboid(-1.0F, -16.0F, 4.0F, 2.0F, 16.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData Bag1 = modelPartData.addChild("Bag1", ModelPartBuilder.create().uv(26, 21).cuboid(-9.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 3.0F, 11.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData Bag2 = modelPartData.addChild("Bag2", ModelPartBuilder.create().uv(26, 21).mirrored().cuboid(1.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 3.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData Saddle = modelPartData.addChild("Saddle", ModelPartBuilder.create().uv(26, 0).cuboid(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 2.0F, 2.0F));

		ModelPartData SaddleMouthL = modelPartData.addChild("SaddleMouthL", ModelPartBuilder.create().uv(29, 5).cuboid(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData SaddleMouthR = modelPartData.addChild("SaddleMouthR", ModelPartBuilder.create().uv(29, 5).cuboid(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData SaddleMouthLine = modelPartData.addChild("SaddleMouthLine", ModelPartBuilder.create().uv(32, 2).cuboid(3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, -8.0F));

		ModelPartData SaddleMouthLineR = modelPartData.addChild("SaddleMouthLineR", ModelPartBuilder.create().uv(32, 2).cuboid(-3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, -8.0F));

		ModelPartData HeadSaddle = modelPartData.addChild("HeadSaddle", ModelPartBuilder.create().uv(19, 0).cuboid(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, new Dilation(0.25F))
		.uv(0, 0).cuboid(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.25F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData WingR = modelPartData.addChild("WingR", ModelPartBuilder.create().uv(64, 0).cuboid(-9.0F, -1.0F, -7.0F, 9.0F, 1.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 4.0F, 0.0F));

		ModelPartData WingR2 = WingR.addChild("WingR2", ModelPartBuilder.create().uv(64, 17).cuboid(-9.0F, 0.0F, -5.0F, 9.0F, 1.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, -1.0F, 0.0F));

		ModelPartData WingR3 = WingR2.addChild("WingR3", ModelPartBuilder.create().uv(64, 31).cuboid(-7.0F, -1.0F, -4.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 1.0F, 0.0F));

		ModelPartData WingL = modelPartData.addChild("WingL", ModelPartBuilder.create().uv(64, 0).mirrored().cuboid(0.0F, -1.0F, -7.0F, 9.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, 4.0F, 0.0F));

		ModelPartData WingL2 = WingL.addChild("WingL2", ModelPartBuilder.create().uv(64, 17).mirrored().cuboid(28.0F, 0.0F, -5.0F, 9.0F, 1.0F, 13.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-19.0F, -1.0F, 0.0F));

		ModelPartData WingL3 = WingL2.addChild("WingL3", ModelPartBuilder.create().uv(64, 31).mirrored().cuboid(46.0F, -1.0F, -4.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-9.0F, 1.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		TailA.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Leg1A.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Leg2A.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Leg3A.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Leg4A.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Ear1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Ear2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		MuleEarL.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		MuleEarR.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Neck.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Bag1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Bag2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Saddle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		SaddleMouthL.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		SaddleMouthR.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		SaddleMouthLine.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		SaddleMouthLineR.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		HeadSaddle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		WingR.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		WingL.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}