package com.peter.peterspjo.data;

import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;
import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class BrazierRecipeData implements SimpleSynchronousResourceReloadListener {

    public static final BrazierRecipeData INSTANCE = new BrazierRecipeData();

    private static final String RESOURCE_FOLDER = "brazier_recipe";

    private static final HashMap<Identifier, BrazierRecipe> recipes = new HashMap<Identifier, BrazierRecipe>();

    @Override
    public Identifier getFabricId() {
        return PJO.id("brazier_recipe");
    }

    @Override
    public void reload(ResourceManager manager) {
        DynamicRegistryManager registryManager = DynamicRegistryManager.of(Registries.REGISTRIES);
        Map<Identifier, Resource> resources = manager.findResources(RESOURCE_FOLDER, path -> true);
        for (Identifier id : resources.keySet()) {
            try (BufferedReader reader = manager.openAsReader(id)) {
                JsonObject json = JsonHelper.deserialize(reader);
                reader.close();
                recipes.put(id, BrazierRecipe.fromJson(id, json, registryManager));
            } catch (Exception e) {
                PJO.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
            }
        }
    }
    
    public BrazierRecipe getMatching(ItemStack stack) {
        for (BrazierRecipe recipe : recipes.values()) {
            if (recipe.match(stack)) {
                return recipe;
            }
        }
        return null;
    }
    public BrazierRecipe getMatching(ItemStack[] stacks) {
        for (BrazierRecipe recipe : recipes.values()) {
            if (recipe.match(stacks)) {
                return recipe;
            }
        }
        return null;
    }
    public BrazierRecipe getMatching(List<ItemStack> stacks) {
        for (BrazierRecipe recipe : recipes.values()) {
            if (recipe.match(stacks)) {
                return recipe;
            }
        }
        return null;
    }

}
