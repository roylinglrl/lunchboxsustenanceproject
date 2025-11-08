package net.royling.lunchboxsustenanceproject.cookingPot;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;

public class CookingPotScreen extends AbstractContainerScreen<CookingPotMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID, "textures/gui/cooking_pot.png");
    private boolean wasCooking = false;

    public CookingPotScreen(CookingPotMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        wasCooking = this.menu.isCooking();
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isCooking()) {
            int progress = this.menu.getProgress();
            int maxProgress = this.menu.getMaxProgress();
            int progressWidth = (int) (24.0F * progress / maxProgress);
            guiGraphics.blit(TEXTURE, x + 89, y + 34, 176, 14, progressWidth, 17);
            if (!wasCooking) {
                wasCooking = true;
            }
        } else {
            wasCooking = false;
        }
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        for (int i = 0; i < 4; i++) {
            if (isHovering(44 + (i % 2) * 18, 17 + (i / 2) * 18, 16, 16, mouseX, mouseY)) {
                ItemStack carried = this.menu.getCarried();
                if (!carried.isEmpty() && !hasItemValues(carried)) {
                    guiGraphics.renderTooltip(this.font,
                            Component.translatable("tooltip.cooking_pot.no_food_values"),
                            mouseX, mouseY);
                }
            }
        }
    }
    private boolean hasItemValues(ItemStack stack) {
        return LunchboxSustenanceProject.hasItemValues(stack);
    }
}
