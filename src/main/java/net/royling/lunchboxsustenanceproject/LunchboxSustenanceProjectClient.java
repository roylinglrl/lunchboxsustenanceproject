package net.royling.lunchboxsustenanceproject;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValuesLoader;
import net.royling.lunchboxsustenanceproject.cookingPot.CookingPotScreen;

import java.util.Locale;
import java.util.Map;

import static net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.MODID;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class LunchboxSustenanceProjectClient {
    public LunchboxSustenanceProjectClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

         }

    private static final ItemValuesLoader LOADER = LunchboxSustenanceProject.getItemValuesLoader();

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        // 1. 获取物品的自定义值
        ItemValues values = LOADER.getValues(item);
        Map<ResourceLocation, Float> valueMap = values.getValues();

        if (valueMap.isEmpty()) {
            return;
        }

        // 2. 添加 "按住 Shift 键显示..." 的提示
        if (!Screen.hasShiftDown()) {
            event.getToolTip().add(Component.translatable("tooltip." + MODID + ".shift_for_details")
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
            return;
        }

        // 3. 按住 Shift 键时显示详细信息
        event.getToolTip().add(Component.literal("")); // 添加空行分隔

        // 可选: 添加标题
        event.getToolTip().add(Component.translatable("tooltip." + MODID + ".food_values_title")
                .withStyle(ChatFormatting.YELLOW));


        // 4. 遍历并添加每个属性的 Tooltip
        for (Map.Entry<ResourceLocation, Float> entry : valueMap.entrySet()) {
            ResourceLocation type = entry.getKey();
            float value = entry.getValue();

            // 属性的本地化键：tooltip.[modid].[type_path] (type_path 是 fruit, meat 等)
            String translationKey = String.format("tooltip.%s.%s", type.getNamespace(), type.getPath()); // 使用属性定义的命名空间

            // 值显示（格式化为两位小数）
            String valueString = String.format(Locale.ROOT, "%.2f", value);

            // 构建最终的 Tooltip 文本：[本地化属性名]: [值]
            Component tooltipLine = Component.translatable(translationKey)
                    .withStyle(ChatFormatting.GRAY) // 属性名颜色
                    .append(": ")
                    .append(Component.literal(valueString).withStyle(ChatFormatting.GREEN)); // 值颜色

            event.getToolTip().add(tooltipLine);
        }
    }
    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event){
        event.register(ModMenuTypes.COOKING_POT_MENU.get(), CookingPotScreen::new);
    }
}
