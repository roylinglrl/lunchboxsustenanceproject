package net.royling.lunchboxsustenanceproject.recipeClass;

import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;

import java.util.List;

public interface RecipeCondition {
    boolean matches(List<ItemStack> inputs, ItemValues[] inputValues);
    String getType();
}
