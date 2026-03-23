package net.royling.lunchboxsustenanceproject.ItemValue.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FoodValueCategory implements IRecipeCategory<FoodValueRecipe> {

    public static final int ITEMS_PER_ROW  = 8;
    public static final int ROWS_PER_PAGE  = 5;
    public static final int ITEMS_PER_PAGE = ITEMS_PER_ROW * ROWS_PER_PAGE;
    public static final int SLOT_SIZE = 18;

    // 图标区高度：图标+属性名占用的顶部空间
    private static final int HEADER_HEIGHT = 36;
    // 物品区起始 y（相对 category 内部坐标）
    private static final int ITEMS_START_Y = HEADER_HEIGHT + 4;
    // 图标尺寸
    private static final int ICON_SIZE = 16;
    public static final int WIDTH  = ITEMS_PER_ROW * SLOT_SIZE; // 144
    public static final int HEIGHT = ITEMS_START_Y + ROWS_PER_PAGE * SLOT_SIZE + 12;

    // 缓存：属性 path（如 "egg"）→ 纹理 ResourceLocation
    private static final Map<String, ResourceLocation> ICON_CACHE = new ConcurrentHashMap<>();

    private final IDrawable background;
    private final IDrawable icon;

    @Override
    public int getWidth()  { return WIDTH; }

    @Override
    public int getHeight() { return HEIGHT; }

    public FoodValueCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(
                new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.APPLE)
        );
    }

    @Override
    public mezz.jei.api.recipe.RecipeType<FoodValueRecipe> getRecipeType() {
        return FoodValueCategoryUid.FOOD_VALUE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.lunchboxsustenanceproject.food_values.title");
    }

    @Override
    public IDrawable getBackground() { return background; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FoodValueRecipe recipe, IFocusGroup focuses) {
        // 属性图标 slot
        ItemStack iconStack = recipe.getIconStack();
        if (!iconStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 2, 10)
                    .addItemStack(iconStack)
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        tooltip.clear();
                        String path = recipe.getPropertyName().contains(":")
                                ? recipe.getPropertyName().split(":")[1]
                                : recipe.getPropertyName();
                        tooltip.add(Component.translatable(
                                "tooltip.lunchboxsustenanceproject." + path
                        ).withStyle(s -> s.withColor(0xFFAA00)));
                        tooltip.add(Component.translatable(
                                "jei.lunchboxsustenanceproject.click_to_see_recipes"
                        ).withStyle(s -> s.withColor(0x808080)));
                    });
        }

        // 本页的物品 slots
        List<FoodValueRecipe.Entry> entries = recipe.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            int col = i % ITEMS_PER_ROW;
            int row = i / ITEMS_PER_ROW;
            int x   = col * SLOT_SIZE;
            int y   = ITEMS_START_Y + row * SLOT_SIZE;

            final float value = entries.get(i).value();
            builder.addSlot(RecipeIngredientRole.OUTPUT, x + 1, y + 1)
                    .addItemStack(entries.get(i).stack())
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        tooltip.add(Component.empty());
                        tooltip.add(Component.literal(
                                recipe.getDisplayName() + ": " + value
                        ).withStyle(s -> s.withColor(0xFFAA00)));
                    });
        }
    }


    @Override
    public void draw(FoodValueRecipe recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics graphics, double mouseX, double mouseY) {

        // 属性名（图标右侧）
        graphics.drawString(
                Minecraft.getInstance().font,
                recipe.getDisplayName(),
                2 + ICON_SIZE + 4,
                10 + ICON_SIZE / 2 - 4,
                0xFFFFAA00, false
        );

        // 页码（右上角，仅多页时显示）
        if (recipe.getTotalPages() > 1) {
            String pageStr = recipe.getPage() + " / " + recipe.getTotalPages();
            int pageStrWidth = Minecraft.getInstance().font.width(pageStr);
            graphics.drawString(
                    Minecraft.getInstance().font,
                    pageStr,
                    WIDTH - pageStrWidth,   // 右对齐
                    4,
                    0xFFAAAAAA, false
            );
        }

        // 数值文字
        List<FoodValueRecipe.Entry> entries = recipe.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            int col   = i % ITEMS_PER_ROW;
            int row   = i / ITEMS_PER_ROW;
            int slotX = col * SLOT_SIZE;
            int slotY = ITEMS_START_Y + row * SLOT_SIZE;

            graphics.pose().pushPose();
            float scale = 0.6f;
            graphics.pose().scale(scale, scale, 1.0f);
            graphics.drawString(
                    Minecraft.getInstance().font,
                    formatValue(entries.get(i).value()),
                    (int) ((slotX + 1) / scale),
                    (int) ((slotY + SLOT_SIZE - 4) / scale),
                    0xFFFFFFFF, true
            );
            graphics.pose().popPose();
        }
    }

    @Override
    public List<Component> getTooltipStrings(FoodValueRecipe recipe,
                                             IRecipeSlotsView recipeSlotsView,
                                             double mouseX, double mouseY) {
        List<FoodValueRecipe.Entry> entries = recipe.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            int col = i % ITEMS_PER_ROW;
            int row = i / ITEMS_PER_ROW;
            int x = col * SLOT_SIZE;
            int y = ITEMS_START_Y + row * SLOT_SIZE;
            if (mouseX >= x && mouseX < x + SLOT_SIZE && mouseY >= y && mouseY < y + SLOT_SIZE) {
                return List.of(
                        Component.literal(recipe.getDisplayName() + ": " + entries.get(i).value())
                );
            }
        }
        return List.of();
    }

    // ── 工具方法 ──────────────────────────────────────────────────

    /**
     * 根据属性名查找对应图标纹理。
     * 属性 "egg" → textures/item/egg_icon.png
     * 属性 "lunchboxsustenanceproject:egg" → 同上（取 path 部分）
     */
    private ResourceLocation getIconTexture(FoodValueRecipe recipe) {
        String propKey = recipe.getPropertyName();
        String path = propKey.contains(":") ? propKey.split(":")[1] : propKey;

        return ICON_CACHE.computeIfAbsent(path, p ->
                ResourceLocation.fromNamespaceAndPath(
                        LunchboxSustenanceProject.MODID,
                        "textures/item/" + p + "_icon.png"
                )
        );
    }

    /**
     * 用 Minecraft 资源管理器检查纹理是否实际存在，避免 blit 报错。
     */
    private boolean textureExists(ResourceLocation rl) {
        try {
            var rm = Minecraft.getInstance().getResourceManager();
            return rm.getResource(rl).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    /** 格式化数值：去掉多余的 .0，保留小数 */
    private String formatValue(float v) {
        if (v == (int) v) {
            return String.valueOf((int) v);
        }
        // 最多保留 1 位小数
        return String.format("%.1f", v);
    }
}