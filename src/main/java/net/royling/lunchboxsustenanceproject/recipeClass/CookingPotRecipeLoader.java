package net.royling.lunchboxsustenanceproject.recipeClass;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CookingPotRecipeLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String FOLDER_NAME = "cooking_pot";

    private List<CookingPotRecipe> recipes = new ArrayList<>();

    public CookingPotRecipeLoader() {
        super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), FOLDER_NAME);
    }
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        List<CookingPotRecipe> loadedRecipes = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation recipeId = entry.getKey();
            try {
                CookingPotRecipe recipe = CookingPotRecipe.fromJson(recipeId, entry.getValue().getAsJsonObject());
                loadedRecipes.add(recipe);
                LOGGER.info("Loaded cooking pot recipe: {}", recipeId);
            } catch (Exception e) {
                LOGGER.error("Failed to load cooking pot recipe: {}", recipeId, e);
            }
        }

        loadedRecipes.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
        this.recipes = loadedRecipes;
        LOGGER.info("Loaded {} cooking pot recipes", recipes.size());
    }

    public List<CookingPotRecipe> getRecipes() {
        return recipes;
    }

    public CookingPotRecipe findMatchingRecipe(List<ItemStack> inputs, ItemValues[] inputValues) {
        List<CookingPotRecipe> matchingRecipes = new ArrayList<>();

        for (CookingPotRecipe recipe : recipes) {
            if (recipe.matches(inputs, inputValues)) {
                matchingRecipes.add(recipe);
            }
        }

        if (matchingRecipes.isEmpty()) {
            return null;
        }

        int highestPriority = matchingRecipes.get(0).getPriority();
        List<CookingPotRecipe> highestPriorityRecipes = new ArrayList<>();

        for (CookingPotRecipe recipe : matchingRecipes) {
            if (recipe.getPriority() == highestPriority) {
                highestPriorityRecipes.add(recipe);
            } else {
                break;
            }
        }

        if (highestPriorityRecipes.size() == 1) {
            return highestPriorityRecipes.get(0);
        } else {
            return highestPriorityRecipes.get(
                    (int) (Math.random() * highestPriorityRecipes.size())
            );
        }
    }
}
