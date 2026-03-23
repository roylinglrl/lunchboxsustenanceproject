package net.royling.lunchboxsustenanceproject.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface LunchboxEvents {

    EventGroup GROUP = EventGroup.of("LunchboxEvents");

    // server 阶段：每次 /reload 都会重新触发，适合配方和属性值
    EventHandler FOOD_VALUES       = GROUP.server("foodValues",
            () -> FoodValueKubeEvent.class);

    EventHandler COOKING_POT_RECIPES = GROUP.server("cookingPotRecipes",
            () -> CookingPotRecipeKubeEvent.class);
}