package com.peter.peterspjo.data;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BrazierRecipe {
    
    public final Identifier id;
    public final BrazierRecipeType type;

    public Item result;
    public Identifier resultId;
    public Item[] input;
    public int amount;

    public BrazierRecipe(Identifier id, BrazierRecipeType type) {
        this.id = id;
        this.type = type;
        input = new Item[0];
    }

    public boolean match(ItemStack stack) {
        if (input.length != 1) {
            return false;
        }
        return stack.isOf(input[0]);
    }

    public boolean match(ItemStack[] stacks) {
        if (input.length != stacks.length) {
            return false;
        }
        int correct = 0;
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (stacks[i].isOf(input[i])) {
                    correct++;
                }
            }
        }
        return correct == input.length;
    }

    public boolean match(List<ItemStack> stacks) {
        if (input.length != stacks.size()) {
            return false;
        }
        int correct = 0;
        for (int i = 0; i < stacks.size(); i++) {
            for (int j = 0; j < input.length; j++) {
                if (stacks.get(i).isOf(input[i])) {
                    correct++;
                }
            }
        }
        return correct == input.length;
    }

    public static BrazierRecipe fromJson(Identifier id, JsonObject json) {
        BrazierRecipeType type = BrazierRecipeType.fromString(json.get("type").getAsString());
        BrazierRecipe recipe = new BrazierRecipe(id, type);

        if (type == BrazierRecipeType.GRANT_ABILITY || type == BrazierRecipeType.CHARGE_ABILITY) {
            recipe.resultId = Identifier.of(json.get("ability").getAsString());
            JsonElement inputEl = json.get("input");
            if (inputEl.isJsonArray()) {
                JsonArray inputArr = inputEl.getAsJsonArray();
                recipe.input = new Item[inputArr.size()];
                for (int i = 0; i < inputArr.size(); i++) {
                    recipe.input[i] = getItemFromID(inputArr.get(i));
                }
            } else {
                recipe.input = new Item[] { getItemFromID(inputEl) };
            }
            if (type == BrazierRecipeType.CHARGE_ABILITY) {
                if (json.has("amount")) {
                    recipe.amount = json.get("amount").getAsInt();
                } else {
                    recipe.amount = 1;
                }
            }
        }

        return recipe;
    }

    private static final Item getItemFromID(JsonElement idElement) {
        return Registries.ITEM.get(Identifier.of(idElement.getAsString()));
    }

    private static final HashMap<String, BrazierRecipeType> recipeTypesByName = new HashMap<String, BrazierRecipeType>();
    
    public enum BrazierRecipeType {

        GRANT_ABILITY("grant_ability"),
        CHARGE_ABILITY("charge_ability");

        public final String name;

        private BrazierRecipeType(String name) {
            this.name = name;
            recipeTypesByName.put(name, this);
        }

        public static BrazierRecipeType fromString(String name) {
            return recipeTypesByName.get(name);
        }
    }
}
