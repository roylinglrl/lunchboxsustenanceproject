package net.royling.lunchboxsustenanceproject.cookingPot;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValuesLoader;
import net.royling.lunchboxsustenanceproject.ModBlockEntities;
import net.royling.lunchboxsustenanceproject.ModItems;
import net.royling.lunchboxsustenanceproject.recipeClass.CookingPotRecipe;
import net.royling.lunchboxsustenanceproject.recipeClass.CookingPotRecipeLoader;
import net.royling.lunchboxsustenanceproject.recipeClass.RecipeCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ErrorManager;

public class CookingPotBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer,ContainerData {

    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{4};
    private static final Logger LOGGER = LogManager.getLogger();


    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 200;
    private boolean isCooking = false;

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> progress;
            case 1 -> maxProgress;
            case 2 -> isCooking ? 1 : 0;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> progress = value;
            case 1 -> maxProgress = value;
            case 2 -> isCooking = value == 1;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        if (slot == 4) {
            return false;
        }
        if (slot >= 0 && slot < 4) {
            return hasItemValues(stack);
        }
        return false;
    }
    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 4) {
            return false;
        }
        if (slot >= 0 && slot < 4) {
            return hasItemValues(stack);
        }

        return false;
    }
    private boolean hasItemValues(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ItemValuesLoader valuesLoader = getValuesLoader();
        if (valuesLoader == null) {
            return false;
        }
        ItemValues values = valuesLoader.getValues(stack.getItem());
        return values != null && !values.getValues().isEmpty();
    }
    public CookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COOKING_POT.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CookingPotBlockEntity blockEntity) {
        if (level.isClientSide) return;

        if (!blockEntity.isCooking && blockEntity.canStartCooking()) {
            blockEntity.startCooking();
        }

        // 烹饪进度
        if (blockEntity.isCooking) {
            // 检查是否还有足够的物品继续烹饪
            if (!blockEntity.hasEnoughItems()) {
                blockEntity.resetCooking();
                //LOGGER.info("Cooking stopped due to insufficient items during progress");
                return;
            }

            blockEntity.progress++;

            if (blockEntity.progress >= blockEntity.maxProgress) {
                blockEntity.finishCooking();
            }

            blockEntity.setChanged();
        }
    }
    private boolean canStartCooking() {
        // 检查所有输入槽都有物品
        for (int i = 0; i < 4; i++) {
            if (getItems().get(i).isEmpty()) {
                return false;
            }
        }

        // 检查输出槽为空
        return getItems().get(4).isEmpty();
    }
    private void startCooking() {
        this.isCooking = true;
        this.progress = 0;
        this.setChanged();
        sync();
    }
    private void finishCooking() {
        NonNullList<ItemStack> items = getItems();

        // 再次确认有足够的物品（防止在tick和finish之间被取走）
        if (!hasEnoughItems()) {
            LOGGER.warn("Cannot finish cooking: items were removed during cooking process");
            resetCooking();
            return;
        }

        ItemStack result = findRecipeResult();

        // 收集容器返还物品
        List<ItemStack> containerReturns = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ItemStack inputStack = items.get(i);
            if (!inputStack.isEmpty()) {
                ItemValues values = getValuesLoader().getValues(inputStack.getItem());
                if (values != null && values.hasContainer()) {
                    ResourceLocation containerId = values.getContainerItem();
                    Item containerItem = BuiltInRegistries.ITEM.get(containerId);
                    if (containerItem != null) {
                        containerReturns.add(new ItemStack(containerItem));
                        LOGGER.debug("Will return container: {} for input {}", containerId, inputStack.getItem());
                    }
                }
            }
        }
        if (!result.isEmpty()) {
            items.set(4, result.copy());
            LOGGER.info("Cooking finished: produced {}", result.getItem());
        } else {
            // 生成"失败之作"
            items.set(4, new ItemStack(ModItems.FAILED_CONCOCTION.get()));
            LOGGER.info("Cooking finished: produced failed concoction");
        }
        // 消耗输入物品
        for (int i = 0; i < 4; i++) {
            items.get(i).shrink(1);
        }
        // 返还容器物品
        if (!containerReturns.isEmpty() && level != null) {
            for (ItemStack containerStack : containerReturns) {
                returnContainerToWorld(containerStack);
            }
            LOGGER.info("Returned {} container items", containerReturns.size());
        }
        resetCooking();
        setChanged();
        sync();
    }
    private void returnContainerToWorld(ItemStack containerStack) {
        if (level == null || containerStack.isEmpty()) return;

        // 计算掉落位置（烹饪锅上方）
        double x = worldPosition.getX() + 0.5;
        double y = worldPosition.getY() + 1.0;
        double z = worldPosition.getZ() + 0.5;

        ItemEntity itemEntity = new ItemEntity(level, x, y, z, containerStack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    private ItemStack findRecipeResult() {
        if (level == null) return ItemStack.EMPTY;

        CookingPotRecipeLoader recipeLoader = getRecipeLoader();
        ItemValuesLoader valuesLoader = getValuesLoader();

        if (recipeLoader == null || valuesLoader == null) {
            return ItemStack.EMPTY;
        }
        // 获取输入物品列表
        List<ItemStack> inputs = getItems().subList(0, 4);

        // 获取每个输入物品的属性值
        ItemValues[] inputValues = new ItemValues[4];
        for (int i = 0; i < 4; i++) {
            if (!inputs.get(i).isEmpty()) {
                inputValues[i] = valuesLoader.getValues(inputs.get(i).getItem());
                if (inputValues[i] != null) {
                } else {
                }
            } else {
                inputValues[i] = null;}
        }
        // 计算每个属性的总和（用于调试）
        Map<String, Float> propertySums = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            if (inputValues[i] != null) {
                for (Map.Entry<ResourceLocation, Float> entry : inputValues[i].getValues().entrySet()) {
                    String propName = entry.getKey().getPath();
                    float value = entry.getValue();
                    propertySums.put(propName, propertySums.getOrDefault(propName, 0.0f) + value);
                }
            }
        }
        CookingPotRecipe recipe = recipeLoader.findMatchingRecipe(inputs, inputValues);
        return recipe != null ? recipe.getOutput() : ItemStack.EMPTY;
    }

    private void sync() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.cooking_pot");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        // 返回实际的物品列表，而不是每次都创建新的空列表
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        // 设置物品列表
        this.items = items;
        if (this.items == null) {
            this.items = NonNullList.withSize(5, ItemStack.EMPTY);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new CookingPotMenu(containerId, playerInventory, this);
    }
    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        NonNullList<ItemStack> items = getItems();
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        NonNullList<ItemStack> items = getItems();
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        NonNullList<ItemStack> items = getItems();
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) {
            // 如果从输入槽取出物品，重置烹饪
            if (slot != 4) {
                resetCooking();
            }
            // 物品发生变化，重新检查配方
            onItemsChanged();
        }
        setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        NonNullList<ItemStack> items = getItems();
        ItemStack stack = ContainerHelper.takeItem(items, slot);
        if (!stack.isEmpty() && slot != 4) {
            resetCooking();
            onItemsChanged();
        }
        setChanged();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        NonNullList<ItemStack> items = getItems();
        ItemStack oldStack = items.get(slot);

        // 检查物品是否真的发生了变化
        boolean itemChanged = !ItemStack.isSameItemSameComponents(oldStack, stack) ||
                oldStack.getCount() != stack.getCount();

        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

        if (itemChanged) {
            // 物品发生变化，重新检查
            onItemsChanged();

            if (slot != 4) { // 如果设置输入槽物品
                if (!level.isClientSide) {
                    if (canStartCooking()) {
                        startCooking();
                    } else if (isCooking && !hasEnoughItems()) {
                        // 如果正在烹饪但物品不足，停止烹饪
                        resetCooking();
                        LOGGER.info("Cooking stopped due to insufficient items");
                    }
                }
            }
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        if (level.getBlockEntity(worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr(
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5
        ) <= 64.0;
    }

    @Override
    public void clearContent() {
        NonNullList<ItemStack> items = getItems();
        items.clear();
        resetCooking();
        onItemsChanged();
        setChanged();
    }

    private void onItemsChanged() {
        // 这里可以添加物品变化时的逻辑
        // 例如：更新客户端、重新计算缓存等
        if (level != null && !level.isClientSide) {
            sync();
        }
    }
    private boolean hasEnoughItems() {
        NonNullList<ItemStack> items = getItems();

        // 检查所有输入槽都有物品
        for (int i = 0; i < 4; i++) {
            if (items.get(i).isEmpty()) {
                return false;
            }
        }

        // 检查输出槽为空
        return items.get(4).isEmpty();
    }

    private void resetCooking() {
        this.isCooking = false;
        this.progress = 0;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_UP;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN && slot == 4; // 只能从下方取出输出槽
    }
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        NonNullList<ItemStack> items = getItems();
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("Progress", progress);
        tag.putBoolean("IsCooking", isCooking);
    }
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        NonNullList<ItemStack> items = getItems();
        ContainerHelper.loadAllItems(tag, items, registries);
        progress = tag.getInt("Progress");
        isCooking = tag.getBoolean("IsCooking");
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
    public float getProgress() {
        return isCooking ? (float) progress / maxProgress : 0;
    }
    public boolean isCooking() {
        return isCooking;
    }
    @Nullable
    private CookingPotRecipeLoader getRecipeLoader() {
        try {
            return net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.getRecipeLoader();
        } catch (Exception e) {
            LOGGER.error("Failed to get recipe loader", e);
            return null;
        }
    }
    @Nullable
    private ItemValuesLoader getValuesLoader() {
        try {
            return net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.getItemValuesLoader();
        } catch (Exception e) {
            LOGGER.error("Failed to get item values loader", e);
            return null;
        }
    }
}
