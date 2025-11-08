package net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.royling.lunchboxsustenanceproject.recipeClass.*;

import java.util.ArrayList;
import java.util.List;

public class CookingPotRecipeDisplay {
    private final CookingPotRecipe recipe;
    private final JEIIconManager iconManager;

    public CookingPotRecipeDisplay(CookingPotRecipe recipe, JEIIconManager iconManager) {
        this.recipe = recipe;
        this.iconManager = iconManager;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, IFocusGroup focuses) {
        List<Component> tooltips = new ArrayList<>();
        int x = 5;
        int y = 5;
        int slotIndex = 0;

        for (RecipeCondition condition : recipe.getConditions()) {
            if (condition instanceof PropertyCondition propertyCondition) {
                // 处理属性条件 - 显示对应的物品图标
                ItemStack iconStack = iconManager.getIconForProperty(propertyCondition.getPropertyName());
                if (!iconStack.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                            .addItemStack(iconStack)
                            .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                                // 清除默认的物品tooltip
                                tooltip.clear();

                                // 处理属性名（移除命名空间部分）
                                String rawPropertyName = propertyCondition.getPropertyName();
                                String displayPropertyName = rawPropertyName;
                                if (rawPropertyName.contains(":")) {
                                    displayPropertyName = rawPropertyName.split(":")[1];
                                }

                                // 添加属性名称的本地化翻译
                                String propertyKey = "tooltip.lunchboxsustenanceproject." + displayPropertyName;
                                Component propertyName = Component.translatable(propertyKey);

                                // 生成数值范围描述
                                Component rangeDescription = generateRangeDescription(
                                        propertyCondition.getMinValue(),
                                        propertyCondition.getMaxValue()
                                );

                                // 第一行：属性名称
                                tooltip.add(propertyName);
                                // 第二行：数值范围
                                if (rangeDescription != null) {
                                    tooltip.add(rangeDescription);
                                }
                            });
                }
                x += 18;
                slotIndex++;

            } else if (condition instanceof TagCondition tagCondition) {
                // 处理标签条件 - 显示标签中的物品
                List<ItemStack> tagItems = iconManager.getItemsForTag(tagCondition.getRequiredTag());
                if (!tagItems.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                            .addIngredients(Ingredient.of(tagItems.stream()))
                            .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                                // 对于标签条件，我们不清除默认tooltip，但添加额外信息
                                tooltip.add(Component.translatable("jei.tooltip.tag_min_count",
                                        tagCondition.getMinCount()));
                            });
                }
                x += 18;
                slotIndex++;
            } else if(condition instanceof ItemCondition itemCondition){
                builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                        .addItemStack(new ItemStack(itemCondition.getRequiredItem()))
                        .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                            // 添加一个提示，显示所需的最小数量
                            tooltip.add(Component.translatable("jei.tooltip.item_min_count",
                                    itemCondition.getMinCount()));
                        });
                x += 18;
                slotIndex++;
            }

            // 每行显示4个，然后换行
            if (slotIndex % 4 == 0) {
                x = 5;
                y += 18;
            }
        }

        // 输出物品 - 保持默认tooltip，只添加额外信息
        builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 22)
                .addItemStack(recipe.getOutput())
                .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                    if (recipe.getPriority() != 0) {
                        tooltip.add(Component.translatable("jei.tooltip.priority", recipe.getPriority()));
                    }
                });
    }

    /**
     * 生成数值范围描述的辅助方法
     */
    private Component generateRangeDescription(Float minValue, Float maxValue) {
        if (minValue != null && maxValue != null) {
            if (minValue.equals(maxValue)) {
                // 精确值
                return Component.translatable("jei.tooltip.property_exact", minValue);
            } else {
                // 区间范围
                return Component.translatable("jei.tooltip.property_range", minValue, maxValue);
            }
        } else if (minValue != null) {
            // 只有最小值（大于等于）
            return Component.translatable("jei.tooltip.property_min", minValue);
        } else if (maxValue != null) {
            // 只有最大值（小于等于）
            return Component.translatable("jei.tooltip.property_max", maxValue);
        } else {
            // 没有范围限制（任意值）
            return Component.translatable("jei.tooltip.property_any");
        }
    }

    public CookingPotRecipe getRecipe() {
        return recipe;
    }
}