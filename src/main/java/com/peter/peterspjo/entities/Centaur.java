package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController.State;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class Centaur extends AbstractHorseEntity implements GeoEntity, VariantHolder<HorseColor> {

    private AnimatableInstanceCache animationCache = new SingletonAnimatableInstanceCache(this);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(Centaur.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> VARIANT_UPPER = DataTracker.registerData(Centaur.class, TrackedDataHandlerRegistry.INTEGER);

    public static final String NAME = "centaur";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final EntityType<Centaur> TYPE = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Centaur::new)
            .dimensions(EntityDimensions.changing(1.5f, 1.5f)).build();

    public static final Identifier EGG_ID = new Identifier(PJO.NAMESPACE, NAME + "_spawn_egg");
    public static final SpawnEggItem EGG = new SpawnEggItem(TYPE, 0x72460d, 0xFFFF00, new FabricItemSettings());
    
    private static final String ANIMATION_WALK_STAGE = "walking";
    private static final RawAnimation ANIMATION_WALK = RawAnimation.begin()
            .thenLoop(ANIMATION_WALK_STAGE);

    private AnimationController<Centaur> animationController;

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
        FabricDefaultAttributeRegistry.register(TYPE, Centaur.createMobAttributes());
        Registry.register(Registries.ITEM, EGG_ID, EGG);
    }

    protected Centaur(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);

        // if (getWorld().isClient()) {
        //     dynamicImage = new NativeImage(32, 32, false);
        //     dynamicTexture = new NativeImageBackedTexture(dynamicImage);
        //     dynamicId = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture(PJO.NAMESPACE,
        //             dynamicTexture);
        // }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(VARIANT_UPPER, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getHorseVariant());
        nbt.putInt("VariantUpper", this.getHorseVariant());
        if (!this.items.getStack(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
        }
    }

    private int getHorseVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setHorseVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        ItemStack itemStack;
        super.readCustomDataFromNbt(nbt);
        this.setHorseVariant(nbt.getInt("Variant"));
        this.setUpperVarient(nbt.getInt("VariantUpper"));
        if (nbt.contains("ArmorItem", NbtElement.COMPOUND_TYPE) && !(itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"))).isEmpty() && this.isHorseArmor(itemStack)) {
            this.items.setStack(1, itemStack);
        }
        this.updateSaddle();
    }

    @Override
    public HorseColor getVariant() {
        return HorseColor.byId(this.getHorseVariant() & 0xFF);
    }

    @Override
    public void setVariant(HorseColor horseColor) {
        this.setHorseVariant(horseColor.getId() & 0xFF | this.getHorseVariant() & 0xFFFFFF00);
    }

    public int getUpperVariant() {
        return dataTracker.get(VARIANT_UPPER) % 8;
    }

    private void setUpperVarient(int variant) {
        dataTracker.set(VARIANT_UPPER, variant);
    }

    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return createBaseHorseAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.HORSE_JUMP_STRENGTH, 1.0);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        animationController = new AnimationController<Centaur>(this, "controller", this::animAtionPredicate);
        animationController.transitionLength(3);
        controllers.add(animationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    private <T extends GeoAnimatable> PlayState animAtionPredicate(AnimationState<T> animationState) {
        if (animationState.isMoving()) {
            if (animationController.getAnimationState() == State.STOPPED)
                animationController.forceAnimationReset();
            animationController.setAnimation(ANIMATION_WALK);
        } else {
            if (animationController.getAnimationState() == State.RUNNING) {
                animationController.stop();
            }
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

}
