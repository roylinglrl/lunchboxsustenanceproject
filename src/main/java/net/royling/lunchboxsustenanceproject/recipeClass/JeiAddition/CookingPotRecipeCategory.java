package net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.ModBlocks.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("removal")
public class CookingPotRecipeCategory implements IRecipeCategory<CookingPotRecipeDisplay> {
    public static final RecipeType<CookingPotRecipeDisplay> RECIPE_TYPE =
            RecipeType.create(LunchboxSustenanceProject.MODID, "cooking_pot", CookingPotRecipeDisplay.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public CookingPotRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.background = guiHelper.createBlankDrawable(150, 60);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.COOKING_POT.get()));
    }

    @Override
    public @NotNull RecipeType<CookingPotRecipeDisplay> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public int getHeight() {
        return IRecipeCategory.super.getHeight();
    }

    @Override
    public int getWidth() {
        return IRecipeCategory.super.getWidth();
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.category.lunchboxsustenanceproject.cooking_pot");
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CookingPotRecipeDisplay display, IFocusGroup focuses) {
        display.setRecipe(builder, focuses);
    }
    public @NotNull ResourceLocation getRegistryName() {
        return ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID, "cooking_pot");
    }
}
