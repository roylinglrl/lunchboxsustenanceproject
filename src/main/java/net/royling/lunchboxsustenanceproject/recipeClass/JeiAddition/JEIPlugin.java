package net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.recipeClass.CookingPotRecipe;
import net.royling.lunchboxsustenanceproject.recipeClass.CookingPotRecipeLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation PLUGIN_ID =
            ResourceLocation.fromNamespaceAndPath("lunchboxsustenanceproject", "jei_plugin");
    private JEIIconManager iconManager;

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CookingPotRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        iconManager = new JEIIconManager(resourceManager);
        // 测试图标加载
        ItemStack testIcon = iconManager.getIconForProperty("nutrition");

        CookingPotRecipeLoader recipeLoader = LunchboxSustenanceProject.getRecipeLoader();
        if (recipeLoader != null && iconManager != null) { // 添加 iconLoader != null 检查
            List<CookingPotRecipe> recipes = recipeLoader.getRecipes();
            List<CookingPotRecipeDisplay> displays = recipes.stream()
                    // 3. 将 iconManager 替换为 iconLoader
                    .map(recipe -> new CookingPotRecipeDisplay(recipe, iconManager))
                    .collect(Collectors.toList());
            registration.addRecipes(CookingPotRecipeCategory.RECIPE_TYPE, displays);
        }
    }

}
