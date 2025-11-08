package net.royling.lunchboxsustenanceproject.recipeClass;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;

import java.util.List;

public class ItemCondition implements RecipeCondition{
    private final Item requiredItem;
    private final int minCount;

    public ItemCondition(Item requiredItem, int minCount) {
        this.requiredItem = requiredItem;
        this.minCount = minCount;
    }
    public static ItemCondition fromJson(JsonObject json) {
        String itemName = json.get("item").getAsString();
        int minCount = json.has("min_count") ? json.get("min_count").getAsInt() : 1;

        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName));
        if (item == net.minecraft.world.item.Items.AIR) {
            throw new IllegalArgumentException("Unknown item: " + itemName);
        }
        return new ItemCondition(item, minCount);
    }

    @Override
    public boolean matches(List<ItemStack> inputs, ItemValues[] inputValues) {
        int count = 0;
        for (ItemStack stack : inputs) {
            if (!stack.isEmpty() && stack.getItem() == requiredItem) {
                count += stack.getCount();
            }
        }
        return count >= minCount;
    }

    @Override
    public String getType() {
        return "item";
    }

    public Item getRequiredItem() {
        return requiredItem;
    }

    public int getMinCount() {
        return minCount;
    }
}
