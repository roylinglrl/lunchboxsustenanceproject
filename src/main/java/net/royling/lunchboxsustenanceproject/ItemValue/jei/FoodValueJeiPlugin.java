package net.royling.lunchboxsustenanceproject.ItemValue.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValuesLoader;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition.CookingPotRecipeCategory;
import net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition.JEIIconManager;

import java.util.*;

@JeiPlugin
public class FoodValueJeiPlugin implements IModPlugin {

    // 缓存：propertyName -> 该属性对应的所有烹饪锅输出物品
    // 用于建立 focus 关联
    private static Map<String, List<ItemStack>> propertyCraftableOutputs = new HashMap<>();

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(
                LunchboxSustenanceProject.MODID, "jei_plugin"
        );
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new FoodValueCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // 把每个属性图标注册为烹饪锅 category 的催化剂
        // 这样点击图标时，JEI 会自动显示该图标物品相关的所有配方
        JEIIconManager iconManager = getIconManager();
        if (iconManager == null) return;

        // 获取所有出现过的属性名
        ItemValuesLoader loader = LunchboxSustenanceProject.getItemValuesLoader();
        Set<String> allProperties = collectAllProperties(loader);

        for (String property : allProperties) {
            ItemStack iconStack = iconManager.getIconForProperty(property);
            if (!iconStack.isEmpty()) {
                // 将图标物品注册为 CookingPot 类别的催化剂
                registration.addRecipeCatalyst(iconStack, CookingPotRecipeCategory.RECIPE_TYPE);
            }
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ItemValuesLoader loader   = LunchboxSustenanceProject.getItemValuesLoader();
        JEIIconManager iconManager = getIconManager();

        // 按属性分组
        Map<String, List<FoodValueRecipe.Entry>> propertyMap = new LinkedHashMap<>();
        for (ResourceLocation itemId : loader.getItemsWithValues()) {
            Item item = BuiltInRegistries.ITEM.get(itemId);
            ItemValues values = loader.getValues(item);
            for (Map.Entry<ResourceLocation, Float> valEntry : values.getValues().entrySet()) {
                String propKey = valEntry.getKey().toString();
                propertyMap.computeIfAbsent(propKey, k -> new ArrayList<>())
                        .add(new FoodValueRecipe.Entry(new ItemStack(item), valEntry.getValue()));
            }
        }

        List<FoodValueRecipe> recipes = new ArrayList<>();

        for (Map.Entry<String, List<FoodValueRecipe.Entry>> entry : propertyMap.entrySet()) {
            String propKey = entry.getKey();
            String path    = propKey.contains(":") ? propKey.split(":")[1] : propKey;
            String displayName = Character.toUpperCase(path.charAt(0)) + path.substring(1);

            ItemStack iconStack = iconManager != null
                    ? iconManager.getIconForProperty(propKey)
                    : ItemStack.EMPTY;

            // 按数值降序排列
            List<FoodValueRecipe.Entry> sorted = entry.getValue().stream()
                    .sorted(Comparator.comparingDouble(FoodValueRecipe.Entry::value).reversed())
                    .toList();

            // ── 分页切割 ──────────────────────────────────────────
            int total      = sorted.size();
            int totalPages = (int) Math.ceil((double) total / FoodValueCategory.ITEMS_PER_PAGE);

            for (int page = 1; page <= totalPages; page++) {
                int from = (page - 1) * FoodValueCategory.ITEMS_PER_PAGE;
                int to   = Math.min(from + FoodValueCategory.ITEMS_PER_PAGE, total);
                List<FoodValueRecipe.Entry> pageEntries = sorted.subList(from, to);

                recipes.add(new FoodValueRecipe(
                        propKey, displayName, pageEntries, iconStack, page, totalPages
                ));
            }
        }

        registration.addRecipes(FoodValueCategoryUid.FOOD_VALUE_TYPE, recipes);
    }

    // ── 工具方法 ──────────────────────────────────────────────────

    private JEIIconManager getIconManager() {
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        return new JEIIconManager(rm);
    }

    private Set<String> collectAllProperties(ItemValuesLoader loader) {
        Set<String> properties = new HashSet<>();
        for (ResourceLocation itemId : loader.getItemsWithValues()) {
            Item item = BuiltInRegistries.ITEM.get(itemId);
            ItemValues values = loader.getValues(item);
            for (ResourceLocation key : values.getValues().keySet()) {
                properties.add(key.toString());
                properties.add(key.getPath()); // 同时加不带命名空间的版本
            }
        }
        return properties;
    }
}