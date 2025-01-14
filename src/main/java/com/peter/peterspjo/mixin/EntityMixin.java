package com.peter.peterspjo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.peter.peterspjo.PJODamageTypes;
import com.peter.peterspjo.blocks.fluids.PJOFluidTags;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Accessor
    abstract Object2DoubleMap<TagKey<Fluid>> getFluidHeight();
    @Accessor
    abstract boolean getFirstUpdate();

    @Invoker("updateMovementInFluid")
    abstract boolean invokeUpdateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Invoker("damage")
    abstract boolean invokeDamage(ServerWorld world, DamageSource source, float amount);
    @Invoker("getDamageSources")
    abstract DamageSources invokeGetDamageSources();
    
    private int styxTime = 0;

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void onBaseTick(CallbackInfo info) {
        Entity entity = (Entity) ((Object) this);
        if (entity instanceof BoatEntity
                || (entity.getVehicle() instanceof BoatEntity && !entity.isSubmergedInWater())) {
            return;
        }
        World world = entity.getWorld();
        if (isInStyx() && !world.isClient) {
            styxTime++;
            if (styxTime % 20 != 1)
                return;
            invokeDamage((ServerWorld) world, invokeGetDamageSources().create(PJODamageTypes.STYX), 2.0f);
        } else {
            styxTime = 0;
        }
    }

    @Inject(method = "updateWaterState()Z", at = @At("RETURN"))
    private void onUpdateWaterState(CallbackInfoReturnable<Boolean> info) {
        invokeUpdateMovementInFluid(PJOFluidTags.STYX, 0.014);
    }
    
    private boolean isInStyx() {
        return !getFirstUpdate() && getFluidHeight().getDouble(PJOFluidTags.STYX) > 0.0;
    }
}
