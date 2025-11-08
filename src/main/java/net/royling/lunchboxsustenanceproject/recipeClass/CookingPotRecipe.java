package net.royling.lunchboxsustenanceproject.recipeClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;

import java.util.ArrayList;
import java.util.List;

public class CookingPotRecipe {
    private final ResourceLocation id;
    private final List<RecipeCondition> conditions;
    private final ItemStack output;
    private final int priority;

    public CookingPotRecipe(ResourceLocation id, List<RecipeCondition> conditions, ItemStack output, int priority) {
        this.id = id;
        this.conditions = conditions;
        this.output = output;
        this.priority = priority;
    }

    public static CookingPotRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        // 解析输出物品
        String outputItem = json.get("output").getAsString();
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(outputItem));
        int count = json.has("output_count") ? json.get("output_count").getAsInt() : 1;
        ItemStack output = new ItemStack(item, count);

        // 解析优先度
        int priority = json.has("priority") ? json.get("priority").getAsInt() : 0;

        // 解析条件
        List<RecipeCondition> conditions = new ArrayList<>();
        JsonArray conditionsArray = json.getAsJsonArray("conditions");

        for (JsonElement conditionElement : conditionsArray) {
            JsonObject conditionObj = conditionElement.getAsJsonObject();
            String type = conditionObj.get("type").getAsString();

            RecipeCondition condition = switch (type) {
                case "property" -> PropertyCondition.fromJson(conditionObj);
                case "tag" -> TagCondition.fromJson(conditionObj);
                case "item" -> ItemCondition.fromJson(conditionObj);
                default -> throw new IllegalArgumentException("Unknown condition type: " + type);
            };
            conditions.add(condition);
        }
        return new CookingPotRecipe(recipeId, conditions, output, priority);
    }
    public boolean matches(List<ItemStack> inputs, ItemValues[] inputValues) {
        if (conditions.isEmpty()) {
            return false; // 至少需要一个条件
        }

        for (RecipeCondition condition : conditions) {
            if (!condition.matches(inputs, inputValues)) {
                return false;
            }
        }
        return true;
    }
    public ResourceLocation getId() { return id; }
    public List<RecipeCondition> getConditions() { return conditions; }
    public ItemStack getOutput() { return output; }
    public int getPriority() { return priority; }
}
