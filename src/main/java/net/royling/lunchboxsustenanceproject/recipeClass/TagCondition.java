package net.royling.lunchboxsustenanceproject.recipeClass;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;

import java.util.List;

public class TagCondition implements RecipeCondition{
    private final TagKey<Item> requiredTag;
    private final int minCount;

    public TagCondition(TagKey<Item> requiredTag, int minCount) {
        this.requiredTag = requiredTag;
        this.minCount = minCount;
    }
    public static TagCondition fromJson(JsonObject json) {
        String tagName = json.get("tag").getAsString();
        int minCount = json.has("min_count") ? json.get("min_count").getAsInt() : 1;

        TagKey<Item> tag = TagKey.create(BuiltInRegistries.ITEM.key(),
                ResourceLocation.parse(tagName));
        return new TagCondition(tag, minCount);
    }
    @Override
    public boolean matches(List<ItemStack> inputs, ItemValues[] inputValues) {
        int count = 0;
        for (ItemStack stack : inputs) {
            if (!stack.isEmpty() && stack.is(requiredTag)) {
                count += stack.getCount();
            }
        }
        return count >= minCount;
    }
    @Override
    public String getType() {
        return "tag";
    }
    public TagKey<Item> getRequiredTag() {
        return requiredTag;
    }

    public int getMinCount() {
        return minCount;
    }

}
