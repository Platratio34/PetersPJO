// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class hellhound extends EntityModel<Entity> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leg_rf;
	private final ModelPart leg_lf;
	private final ModelPart leg_rb;
	private final ModelPart leg_lb;
	public hellhound(ModelPart root) {
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
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -28.0F, -15.0F, 20.0F, 17.0F, 30.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 82).cuboid(-6.0F, -5.0F, -8.0F, 12.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -15.0F));

		ModelPartData leg_rf = modelPartData.addChild("leg_rf", ModelPartBuilder.create().uv(0, 57).cuboid(-3.0F, -3.0F, -3.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-10.0F, 9.0F, -10.0F));

		ModelPartData leg_lf = modelPartData.addChild("leg_lf", ModelPartBuilder.create().uv(20, 57).cuboid(-2.0F, -3.0F, -3.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(10.0F, 9.0F, -10.0F));

		ModelPartData leg_rb = modelPartData.addChild("leg_rb", ModelPartBuilder.create().uv(60, 57).cuboid(-3.0F, -3.0F, -2.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-10.0F, 9.0F, 10.0F));

		ModelPartData leg_lb = modelPartData.addChild("leg_lb", ModelPartBuilder.create().uv(40, 57).cuboid(-2.0F, -3.0F, -2.0F, 5.0F, 18.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(10.0F, 9.0F, 10.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leg_rf.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leg_lf.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leg_rb.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leg_lb.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}