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

    // 布局常量
    // 每个槽位 18×18，标签区 8px，行间距 4px
    // 行0: slot y=4,        label y=23
    // 行1: slot y=4+26=30,  label y=49
    public static final int SLOT_SIZE    = 18;
    public static final int LABEL_HEIGHT = 8;
    public static final int ROW_STRIDE   = SLOT_SIZE + LABEL_HEIGHT + 4; // 30
    public static final int START_X      = 4;
    public static final int START_Y      = 4;
    public static final int COLS         = 4;
    public static final int MAX_SLOTS    = 8; // 最多显示8个条件

    // 输出槽：右侧垂直居中，背景高64px，居中 y=(64-18)/2=23
    public static final int OUTPUT_X = 128;
    public static final int OUTPUT_Y = 23;

    public record SlotLabel(int x, int y, String text, boolean isBarrier) {}
    private final List<SlotLabel> slotLabels = new ArrayList<>();

    public CookingPotRecipeDisplay(CookingPotRecipe recipe, JEIIconManager iconManager) {
        this.recipe = recipe;
        this.iconManager = iconManager;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, IFocusGroup focuses) {
        slotLabels.clear();

        int slotIndex = 0;

        for (RecipeCondition condition : recipe.getConditions()) {
            if (slotIndex >= MAX_SLOTS) break;

            int col = slotIndex % COLS;
            int row = slotIndex / COLS;
            int x = START_X + col * SLOT_SIZE;
            int y = START_Y + row * ROW_STRIDE;

            if (condition instanceof PropertyCondition propertyCondition) {
                ItemStack iconStack = iconManager.getIconForProperty(propertyCondition.getPropertyName());

                boolean isBarrier = propertyCondition.getMaxValue() != null
                        && propertyCondition.getMaxValue() == 0.0f
                        && propertyCondition.getMinValue() == null;

                if (!iconStack.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                            .addItemStack(iconStack)
                            .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                                tooltip.clear();
                                String rawName = propertyCondition.getPropertyName();
                                String displayName = rawName.contains(":") ? rawName.split(":")[1] : rawName;
                                tooltip.add(Component.translatable("tooltip.lunchboxsustenanceproject." + displayName));
                                Component rangeDesc = generateRangeDescription(
                                        propertyCondition.getMinValue(),
                                        propertyCondition.getMaxValue()
                                );
                                if (rangeDesc != null) tooltip.add(rangeDesc);
                            });

                    String labelText = isBarrier ? "" : buildLabelText(
                            propertyCondition.getMinValue(), propertyCondition.getMaxValue());
                    slotLabels.add(new SlotLabel(x, y, labelText, isBarrier));
                    slotIndex++;
                }

            } else if (condition instanceof TagCondition tagCondition) {
                List<ItemStack> tagItems = iconManager.getItemsForTag(tagCondition.getRequiredTag());
                if (!tagItems.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                            .addIngredients(Ingredient.of(tagItems.stream()))
                            .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                                tooltip.add(Component.translatable("jei.tooltip.tag_min_count",
                                        tagCondition.getMinCount()));
                            });
                    slotLabels.add(new SlotLabel(x, y, "×" + tagCondition.getMinCount(), false));
                    slotIndex++;
                }

            } else if (condition instanceof ItemCondition itemCondition) {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                        .addItemStack(new ItemStack(itemCondition.getRequiredItem()))
                        .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                            tooltip.add(Component.translatable("jei.tooltip.item_min_count",
                                    itemCondition.getMinCount()));
                        });
                slotLabels.add(new SlotLabel(x, y, "×" + itemCondition.getMinCount(), false));
                slotIndex++;
            }
        }

        // 输出槽
        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X, OUTPUT_Y)
                .addItemStack(recipe.getOutput())
                .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                    if (recipe.getPriority() != 0) {
                        tooltip.add(Component.translatable("jei.tooltip.priority", recipe.getPriority()));
                    }
                });
    }

    // min only  → "2.0+"
    // max only  → "1.0-"
    // range     → "1~2"
    // exact     → "=2"
    private String buildLabelText(Float min, Float max) {
        if (min != null && max != null) {
            return min.equals(max) ? "=" + fmt(min) : fmt(min) + "~" + fmt(max);
        } else if (min != null) {
            return fmt(min) + "+";
        } else if (max != null) {
            return fmt(max) + "-";
        }
        return "";
    }

    private String fmt(float v) {
        return (v == (int) v) ? String.valueOf((int) v) : String.valueOf(v);
    }

    private Component generateRangeDescription(Float minValue, Float maxValue) {
        if (minValue != null && maxValue != null) {
            if (minValue.equals(maxValue)) {
                return Component.translatable("jei.tooltip.property_exact", minValue);
            } else {
                return Component.translatable("jei.tooltip.property_range", minValue, maxValue);
            }
        } else if (minValue != null) {
            return Component.translatable("jei.tooltip.property_min", minValue);
        } else if (maxValue != null) {
            return Component.translatable("jei.tooltip.property_max", maxValue);
        } else {
            return Component.translatable("jei.tooltip.property_any");
        }
    }

    public List<SlotLabel> getSlotLabels() { return slotLabels; }
    public CookingPotRecipe getRecipe() { return recipe; }
}