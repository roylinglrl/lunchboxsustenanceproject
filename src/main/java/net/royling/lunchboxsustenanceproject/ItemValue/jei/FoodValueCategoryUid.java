package net.royling.lunchboxsustenanceproject.ItemValue.jei;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.royling.lunchboxsustenanceproject.ItemValue.jei.FoodValueRecipe;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;

public class FoodValueCategoryUid {
    public static final ResourceLocation FOOD_VALUE_ID =
            ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID, "food_values");

    public static final RecipeType<FoodValueRecipe> FOOD_VALUE_TYPE =
            RecipeType.create(
                    LunchboxSustenanceProject.MODID,
                    "food_values",
                    FoodValueRecipe.class
            );
}