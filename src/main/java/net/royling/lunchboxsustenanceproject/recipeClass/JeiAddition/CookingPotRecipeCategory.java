package net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.ModBlocks.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("removal")
public class CookingPotRecipeCategory implements IRecipeCategory<CookingPotRecipeDisplay> {
    public static final RecipeType<CookingPotRecipeDisplay> RECIPE_TYPE =
            RecipeType.create(LunchboxSustenanceProject.MODID, "cooking_pot", CookingPotRecipeDisplay.class);
    public static final ResourceLocation BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("lunchboxsustenanceproject", "textures/gui/jei_cooking_pot_bg.png");
    // 背景尺寸
    // 宽: 4列×18 + 输出区(18+槽右边距) + 左右padding = 4*18 + 30 + 4*2 = 110... 取 155 留余量
    // 高: 上padding4 + 行0(18) + 标签(8) + 行间(4) + 行1(18) + 标签(8) + 下padding4 = 64
    private static final int BG_WIDTH  = 155;
    private static final int BG_HEIGHT = 64;

    // 标签缩放（标签区只有8px高，0.55缩放时字体≈7px）
    private static final float LABEL_SCALE   = 0.55f;
    private static final int   LABEL_COLOR   = 0xFFFFFFFF;
    private static final int   BARRIER_SIZE  = 7; // 小屏障图标 7×7px

    private final IDrawable background;
    private final IDrawable icon;

    // 如果有自定义背景贴图，替换下面这行并使用 createDrawable
    // private static final ResourceLocation BG_TEXTURE =
    //     ResourceLocation.fromNamespaceAndPath("lunchboxsustenanceproject", "textures/gui/jei_cooking_pot_bg.png");

    public CookingPotRecipeCategory(IGuiHelper guiHelper) {
         this.background = guiHelper.createDrawable(BG_TEXTURE, 0, 0, BG_WIDTH, BG_HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.COOKING_POT.get()));
    }

    @Override public @NotNull RecipeType<CookingPotRecipeDisplay> getRecipeType() { return RECIPE_TYPE; }
    @Override public int getWidth()  { return BG_WIDTH; }
    @Override public int getHeight() { return BG_HEIGHT; }
    @Override public @Nullable IDrawable getBackground() { return background; }
    @Override public @NotNull IDrawable getIcon() { return icon; }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.category.lunchboxsustenanceproject.cooking_pot");
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CookingPotRecipeDisplay display, IFocusGroup focuses) {
        display.setRecipe(builder, focuses);
    }

    @Override
    public void draw(CookingPotRecipeDisplay display, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics guiGraphics, double mouseX, double mouseY) {

        Font font = Minecraft.getInstance().font;

        for (CookingPotRecipeDisplay.SlotLabel label : display.getSlotLabels()) {
            // 标签区顶部 = 槽位y + 槽位高(18) + 1px间距
            int labelTopY = label.y() + CookingPotRecipeDisplay.SLOT_SIZE + 1;
            int slotCenterX = label.x() + CookingPotRecipeDisplay.SLOT_SIZE / 2;

            if (label.isBarrier()) {
                // 小屏障图标，居中于槽位下方标签区
                float scale = BARRIER_SIZE / 16.0f;
                int drawX = slotCenterX - BARRIER_SIZE / 2;
                int drawY = labelTopY;

                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(drawX, drawY, 0);
                guiGraphics.pose().scale(scale, scale, 1f);
                guiGraphics.renderFakeItem(new ItemStack(Items.BARRIER), 0, 0);
                guiGraphics.pose().popPose();

            } else if (!label.text().isEmpty()) {
                // 数值文字，缩放后居中
                float textWidth = font.width(label.text()) * LABEL_SCALE;
                float drawX = slotCenterX - textWidth / 2f;
                float drawY = labelTopY;

                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(drawX, drawY, 0);
                guiGraphics.pose().scale(LABEL_SCALE, LABEL_SCALE, 1f);
                guiGraphics.drawString(font, label.text(), 0, 0, LABEL_COLOR, true);
                guiGraphics.pose().popPose();
            }
        }
    }

    public @NotNull ResourceLocation getRegistryName() {
        return ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID, "cooking_pot");
    }
}