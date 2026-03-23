package net.royling.lunchboxsustenanceproject.kubejs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.resources.ResourceLocation;
import net.royling.lunchboxsustenanceproject.recipeClass.*;


import java.util.ArrayList;
import java.util.List;

public class CookingPotRecipeKubeEvent implements KubeEvent {
    public final List<CookingPotRecipe> added   = new ArrayList<>();
    public final List<ResourceLocation> removed = new ArrayList<>();
    /**
     * LunchboxEvents.cookingPotRecipes(event => {
     *     event.addRecipe('mypack:fish_stew')
     *          .requireProperty('fish', 2.0)
     *          .output('minecraft:cooked_cod')
     *          .register()
     * })
     */
    public Builder addRecipe(String recipeId) {
        return new Builder(ResourceLocation.parse(recipeId), this);
    }
    /**
     * event.removeRecipe('lunchboxsustenanceproject:some_recipe')
     */
    public void removeRecipe(String recipeId) {
        removed.add(ResourceLocation.parse(recipeId));
    }
    void submitRecipe(CookingPotRecipe recipe) {
        added.add(recipe);
    }
    public static class Builder {
        private final ResourceLocation          id;
        private final CookingPotRecipeKubeEvent event;
        private final JsonArray                 conditions = new JsonArray();
        private String output      = "minecraft:air";
        private int    outputCount = 1;
        private int    priority    = 0;

        Builder(ResourceLocation id, CookingPotRecipeKubeEvent event) {
            this.id    = id;
            this.event = event;
        }
        /**
         * 只限制最大值，不限制最小值
         * 用于"不能含有某属性"的场景：.requirePropertyMax('monster', 0.0)
         */
        public Builder requirePropertyMax(String property, float max) {
            JsonObject o = new JsonObject();
            o.addProperty("type",     "property");
            o.addProperty("property", property);
            o.addProperty("max",      max);
            conditions.add(o);
            return this;
        }

        /** 需要某属性达到最小值：.requireProperty('fruit', 2.0) */
        public Builder requirePropertyMin(String property, float min) {
            JsonObject o = new JsonObject();
            o.addProperty("type",     "property");
            o.addProperty("property", property);
            o.addProperty("min",      min);
            conditions.add(o);
            return this;
        }

        /** 属性区间：.requireProperty('fruit', 1.0, 3.0) */
        public Builder requireProperty(String property, float min, float max) {
            JsonObject o = new JsonObject();
            o.addProperty("type",     "property");
            o.addProperty("property", property);
            o.addProperty("min",      min);
            o.addProperty("max",      max);
            conditions.add(o);
            return this;
        }

        /** 需要标签物品：.requireTag('minecraft:fishes', 2) */
        public Builder requireTag(String tag, int minCount) {
            JsonObject o = new JsonObject();
            o.addProperty("type",      "tag");
            o.addProperty("tag",       tag);
            o.addProperty("min_count", minCount);
            conditions.add(o);
            return this;
        }

        public Builder requireTag(String tag) {
            return requireTag(tag, 1);
        }

        /** 需要具体物品：.requireItem('minecraft:water_bucket') */
        public Builder requireItem(String itemId, int minCount) {
            JsonObject o = new JsonObject();
            o.addProperty("type",      "item");
            o.addProperty("item",      itemId);
            o.addProperty("min_count", minCount);
            conditions.add(o);
            return this;
        }

        public Builder requireItem(String itemId) {
            return requireItem(itemId, 1);
        }

        /** 输出物品：.output('minecraft:cooked_beef') */
        public Builder output(String itemId) {
            this.output = itemId;
            return this;
        }

        public Builder output(String itemId, int count) {
            this.output      = itemId;
            this.outputCount = count;
            return this;
        }

        /** 优先级：.priority(10) */
        public Builder priority(int p) {
            this.priority = p;
            return this;
        }

        /** 提交配方，必须调用 */
        public void register() {
            JsonObject json = new JsonObject();
            json.addProperty("output",       output);
            json.addProperty("output_count", outputCount);
            json.addProperty("priority",     priority);
            json.add("conditions",           conditions);
            event.submitRecipe(CookingPotRecipe.fromJson(id, json));
        }
    }

}
