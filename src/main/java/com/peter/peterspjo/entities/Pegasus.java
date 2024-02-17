package com.peter.peterspjo.entities;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController.State;
import software.bernie.geckolib.core.object.PlayState;

public class Pegasus extends AbstractHorseEntity implements GeoEntity {

    private AnimatableInstanceCache animationCache = new SingletonAnimatableInstanceCache(this);

    public static final String NAME = "pegasus";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);
    public static final EntityType<Pegasus> TYPE = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Pegasus::new)
            .dimensions(EntityDimensions.changing(1.5f, 1.5f)).build();

    public static final Identifier EGG_ID = new Identifier(PJO.NAMESPACE, NAME + "_spawn_egg");
    public static final SpawnEggItem EGG = new SpawnEggItem(TYPE, 0x5b276c, 0xfdff2f, new FabricItemSettings());

    // private static final String ANIMATION_WING_IDLE_GROUND_NAME = "animation." + NAME + ".wing_idle_ground";
    private static final String ANIMATION_WING_IDLE_AIR_NAME = "wing_idle_air";
    private static final String ANIMATION_WING_FOLD_NAME = "wing_fold";
    private static final String ANIMATION_WING_FLAP_NAME = "wing_flap";
    private static final String ANIMATION_LAUNCH_NAME = "launch";
    private static final String ANIMATION_BODY_LAUNCH_NAME = "body_launch";
    private static final String ANIMATION_BODY_IDLE_NAME = "body_idle";
    private static final String ANIMATION_BODY_WALK_NAME = "body_walk";

    // private static final RawAnimation ANIMATION_WING_IDLE_GROUND = RawAnimation.begin()
    //         .thenLoop(ANIMATION_WING_IDLE_GROUND_NAME);
    private static final RawAnimation ANIMATION_WING_IDLE_AIR = RawAnimation.begin()
            .thenLoop(ANIMATION_WING_IDLE_AIR_NAME);
    private static final RawAnimation ANIMATION_WING_FOLD = RawAnimation.begin()
            .thenPlayAndHold(ANIMATION_WING_FOLD_NAME);
    private static final RawAnimation ANIMATION_WING_FLAP = RawAnimation.begin().thenLoop(ANIMATION_WING_FLAP_NAME);
    private static final RawAnimation ANIMATION_WING_FLAP_ONCE = RawAnimation.begin().thenPlay(ANIMATION_WING_FLAP_NAME)
            .thenLoop(ANIMATION_WING_IDLE_AIR_NAME);
    private static final RawAnimation ANIMATION_LAUNCH = RawAnimation.begin().thenPlay(ANIMATION_LAUNCH_NAME)
            .thenLoop(ANIMATION_WING_IDLE_AIR_NAME);
    private static final RawAnimation ANIMATION_BODY_LAUNCH = RawAnimation.begin().thenPlay(ANIMATION_BODY_LAUNCH_NAME)
            .thenLoop(ANIMATION_BODY_WALK_NAME);
    private static final RawAnimation ANIMATION_BODY_IDLE = RawAnimation.begin().thenLoop(ANIMATION_BODY_IDLE_NAME);
    private static final RawAnimation ANIMATION_BODY_WALK = RawAnimation.begin().thenLoop(ANIMATION_BODY_WALK_NAME);

    private AnimationController<Pegasus> animationControllerWing;
    private AnimationController<Pegasus> animationControllerBody;

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, ID, TYPE);
        FabricDefaultAttributeRegistry.register(TYPE, Pegasus.createMobAttributes());
        Registry.register(Registries.ITEM, EGG_ID, EGG);
    }

    public Pegasus(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.HORSE_JUMP_STRENGTH, 1.0);
    }

    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }

    @Override
    public void onDamaged(DamageSource source) {
        if (source.isOf(DamageTypes.FALL))
            return;
        super.onDamaged(source);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isOf(DamageTypes.FALL))
            return false;
        return super.damage(source, amount);
    }

    // private boolean fly = false;
    // @Override
    // public void startJumping(int height) {
    // if (isOnGround()) {
    // super.startJumping(height);
    // } else {
    // fly = true;
    // }
    // }

    private int tSJ = 100000000;

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        tSJ++;
        if (!this.isLogicalSideForUpdatingMovement())
            return;
        if (jumping && !isOnGround()) {
            jumping = false;
            tSJ = 0;
        }
        Vec3d cVel = this.getVelocity();
        float f = MathHelper.sin(this.getYaw() * ((float) Math.PI / 180)) * -1;
        float g = MathHelper.cos(this.getYaw() * ((float) Math.PI / 180));
        float speed = controllingPlayer.forwardSpeed * 100f;
        if (tSJ < 5) {
            cVel.add(f * speed, 0.0, g * speed);
            this.setVelocity(cVel.x, 1, cVel.z);
        } else if (!isOnGround()) {
            cVel.add(f * speed, 0.0, g * speed);
            this.setVelocity(cVel.x, (float) Math.max(cVel.y, -0.2), cVel.z);
        }
    }

    private boolean wasOnGround = false;

    @Override
    public void tick() {
        super.tick();
        wasOnGround = this.isOnGround();
    }

    @Override
    protected float getOffGroundSpeed() {
        return getMovementSpeed() * 0.9f;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        animationControllerWing = new AnimationController<Pegasus>(this, "controller_wing", 5,
                this::animAtionPredicateWing);
        // animationControllerBody = new AnimationController<Pegasus>(this, "controller_body", 5,
        //         this::animAtionPredicateBody);
        // controllers.add(animationControllerBody);
        controllers.add(animationControllerWing);
    }

    private boolean currentlyPlayingWing(String animationName) {
        if (animationControllerWing.getAnimationState() == State.STOPPED)
            return false;
        return animationControllerWing.getCurrentAnimation().animation().name().equals(animationName);
    }

    private boolean currentlyPlayingBody(String animationName) {
        if (animationControllerBody.getAnimationState() == State.STOPPED)
            return false;
        return animationControllerBody.getCurrentAnimation().animation().name().equals(animationName);
    }

    private <T extends GeoAnimatable> PlayState animAtionPredicateWing(AnimationState<T> animationState) {

        if (this.isOnGround()) {
            animationControllerWing.setAnimation(ANIMATION_WING_FOLD);
        } else {
            if (wasOnGround) {
                animationControllerWing.setAnimation(ANIMATION_LAUNCH);
            } else if (!currentlyPlayingWing(ANIMATION_LAUNCH_NAME)) {
                if (Math.abs(this.forwardSpeed) > 0.1) {
                    animationControllerWing.setAnimation(ANIMATION_WING_FLAP);
                } else {
                    animationControllerWing.setAnimation(ANIMATION_WING_IDLE_AIR);
                }
            }
        }
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState animAtionPredicateBody(AnimationState<T> animationState) {

        // if (this.isOnGround()) {
        //     if (Math.abs(this.forwardSpeed) > 0.1) {
        //         animationControllerBody.setAnimation(ANIMATION_BODY_WALK);
        //     } else {
        //         animationControllerBody.setAnimation(ANIMATION_BODY_IDLE);
        //     }
        // } else {
        //     if (wasOnGround) {
        //         animationControllerBody.setAnimation(ANIMATION_BODY_LAUNCH);
        //     } else if (!currentlyPlayingBody(ANIMATION_BODY_LAUNCH_NAME)) {
        //         if (Math.abs(this.forwardSpeed) > 0.1) {
        //             animationControllerBody.setAnimation(ANIMATION_BODY_WALK);
        //         } else {
        //             animationControllerBody.setAnimation(ANIMATION_BODY_IDLE);
        //         }
        //     }
        // }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

}
