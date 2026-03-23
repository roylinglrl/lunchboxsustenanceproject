package net.royling.lunchboxsustenanceproject.ItemValue.jei;

import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;

import java.util.List;

public class FoodValueRecipe {
    // 属性名，例如 "fruit", "meat", "lunchboxsustenanceproject:magic"
    private final String propertyName;
    // 显示名（去掉命名空间前缀，首字母大写）
    private final String displayName;
    // 所有包含此属性的物品及其对应数值
    private final List<Entry> entries;
    private final ItemStack iconStack;
    private final int page;
    private final int totalPages;

    public FoodValueRecipe(String propertyName, String displayName, List<Entry> entries, ItemStack iconStack, int page, int totalPages) {
        this.propertyName = propertyName;
        this.displayName = displayName;
        this.entries = entries;
        this.iconStack = iconStack;
        this.page         = page;
        this.totalPages   = totalPages;
    }
    public FoodValueRecipe(String propertyName, String displayName,
                           List<Entry> entries, ItemStack iconStack) {
        this(propertyName, displayName, entries, iconStack, 1, 1);
    }
    public ItemStack getIconStack() { return iconStack; }

    public String getPropertyName() { return propertyName; }
    public String getDisplayName() { return displayName; }
    public List<Entry> getEntries() { return entries; }
    public int getPage()       { return page; }
    public int getTotalPages() { return totalPages; }

    public record Entry(ItemStack stack, float value) {}
}