package com.peter.peterspjo;

import org.lwjgl.glfw.GLFW;

import com.peter.peterspjo.blocks.fluids.StyxWater;
import com.peter.peterspjo.entities.CentaurRenderer;
import com.peter.peterspjo.entities.EmpousaiRenderer;
import com.peter.peterspjo.entities.HellhoundRenderer;
import com.peter.peterspjo.entities.PegasusRenderer;
import com.peter.peterspjo.gui.AbilityHUD;
import com.peter.peterspjo.items.RiptideItem;
import com.peter.peterspjo.items.SwitchableSword;
import com.peter.peterspjo.networking.PJOClientNetworking;
import com.peter.peterspjo.worldgen.UnderworldDimensionEffects;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class PJOClient implements ClientModInitializer {

    public static KeyBinding keyBinding;

    private static AbilityHUD abilityHUD;

	@Override
	public void onInitializeClient() {
        SpearEntityRenderer.register();
		EmpousaiRenderer.register();
		HellhoundRenderer.register();
		PegasusRenderer.register();
		CentaurRenderer.register();

		ModelPredicateProviderRegistry.register(RiptideItem.ITEM, PJO.id("is_sword"),
				(itemStack, clientWorld, livingEntity, i) -> {
                    return itemStack.getOrDefault(SwitchableSword.IS_SWORD_COMPONENT, false) ? 1.0f : 0.0f;
				});
		
		FluidRenderHandlerRegistry.INSTANCE.register(StyxWater.STILL, StyxWater.FLOWING, new SimpleFluidRenderHandler(
				Identifier.of("minecraft", "block/water_still"), Identifier.of("minecraft", "block/water_flow"), 0xA16e4400));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), StyxWater.STILL, StyxWater.FLOWING);
        
        UnderworldDimensionEffects.register();

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.peterspjo.useAbility",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.peterspjo.binds"));
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (keyBinding.wasPressed()) {
                PJOClientNetworking.sendAbilityUsePacket(client.world, client.player);
            }
        });
        
        PJOClientNetworking.registerClient();
        
        abilityHUD = new AbilityHUD();
        HudRenderCallback.EVENT.register(abilityHUD);

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            abilityHUD.clearAbilities();
        });
	}
}