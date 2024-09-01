package com.peter.peterspjo.gui;

import java.util.HashMap;

import com.peter.peterspjo.abilities.AbilityManager;
import com.peter.peterspjo.abilities.AbstractAbility;
import com.peter.peterspjo.abilities.AbstractChargedAbility;
import com.peter.peterspjo.abilities.PJOAbilities;
import com.peter.peterspjo.networking.AbilityUpdatePayload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.MutableText;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class AbilityHUD implements HudRenderCallback {

    private final HashMap<Identifier, AbstractAbility> abilities;

    public AbilityHUD() {
        abilities = new HashMap<Identifier, AbstractAbility>();
        ClientPlayNetworking.registerGlobalReceiver(AbilityUpdatePayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                switch (payload.action) {
                    case AbilityUpdatePayload.Action.ADD:
                        if (!abilities.containsKey(payload.ability)) {
                            abilities.put(payload.ability, PJOAbilities.getAbility(payload.ability).instance().markClient());
                        }
                        break;
                    case AbilityUpdatePayload.Action.REMOVE:
                        if (abilities.containsKey(payload.ability)) {
                            abilities.remove(payload.ability);
                        }
                        break;
                    case AbilityUpdatePayload.Action.CHARGE:
                        if (abilities.containsKey(payload.ability)) {
                            AbstractAbility ability = abilities.get(payload.ability);
                            if (ability instanceof AbstractChargedAbility) {
                                ((AbstractChargedAbility)ability).setCharge(payload.charge);
                            }
                        }
                        break;
                
                    default:
                        break;
                }
            });
        });
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        int x = 2;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null)
            return;

        // int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int lineHeight = client.textRenderer.fontHeight;
        y = (screenHeight / 2) - lineHeight;

        for (Identifier abilityId : abilities.keySet()) {
            AbstractAbility ability = abilities.get(abilityId);
            MutableText text = AbilityManager.getAbilityNameTranslated(abilityId);
            if (ability instanceof AbstractChargedAbility) {
                text.append(String.format(" - %.2f%%", ((AbstractChargedAbility) ability).getChargePercent()));
            }
            drawContext.drawText(client.textRenderer, text, x, y,
                    Colors.WHITE, true);
            y += lineHeight + 2;
        }
    }
    
    public void clearAbilities() {
        abilities.clear();
    }

}
