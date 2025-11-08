package net.royling.lunchboxsustenanceproject.cookingPot;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.royling.lunchboxsustenanceproject.ModMenuTypes;

public class CookingPotMenu extends AbstractContainerMenu {
    private final CookingPotBlockEntity blockEntity;
    private final ContainerData data;
    private boolean wasCooking = false;

    public CookingPotMenu(int containerId, Inventory playerInventory, CookingPotBlockEntity blockEntity) {
        super(ModMenuTypes.COOKING_POT_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.data = blockEntity; // 因为CookingPotBlockEntity实现了ContainerData

        addSlots(playerInventory);
        addDataSlots(data);
    }

    public CookingPotMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, (CookingPotBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        // 检查烹饪状态变化
        boolean isCookingNow = isCooking();
        if (isCookingNow != wasCooking) {
            // 烹饪状态发生变化，可以在这里添加UI更新逻辑
            wasCooking = isCookingNow;

            // 如果需要，可以发送自定义数据包给客户端
            // 例如：更新进度条颜色、播放音效等
        }
    }



    private void addSlots(Inventory playerInventory) {
        // 输入槽 (0-3)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                this.addSlot(new Slot(blockEntity, col + row * 2, 44 + col * 18, 17 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        // 在客户端也检查物品是否有食物值
                        return blockEntity.canPlaceItem(this.getContainerSlot(), stack);
                    }
                });
            }
        }

        // 输出槽 (4) - 修复：使用普通的Slot并重写mayPlace方法
        this.addSlot(new Slot(blockEntity, 4, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // 玩家物品栏
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // 玩家快捷栏
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index == 4) { // 输出槽
                if (!this.moveItemStackTo(itemstack1, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= 5) { // 从玩家物品栏移动到输入槽
                if (!this.moveItemStackTo(itemstack1, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 5, 41, false)) { // 从输入槽移动到玩家物品栏
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public boolean isCooking() {
        return data.get(2) == 1;
    }
}
