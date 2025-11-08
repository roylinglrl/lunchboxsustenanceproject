package net.royling.lunchboxsustenanceproject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValuesLoader;
import net.royling.lunchboxsustenanceproject.LootTableModifier.ModLootModifiers;
import net.royling.lunchboxsustenanceproject.ModBlocks.ModBlocks;
import net.royling.lunchboxsustenanceproject.ModCreative.ModCreative;
import net.royling.lunchboxsustenanceproject.recipeClass.CookingPotRecipeLoader;
import net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition.JEIIconConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LunchboxSustenanceProject.MODID)
public class LunchboxSustenanceProject {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "lunchboxsustenanceproject";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    private static ItemValuesLoader itemValuesLoader;
    private static CookingPotRecipeLoader recipeLoader;
    // Create a Deferred Register to hold Blocks which will all be registered under the "lunchboxsustenanceproject" namespace

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public LunchboxSustenanceProject(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modContainer.registerConfig(ModConfig.Type.COMMON, JEIIconConfig.SPEC);
        modEventBus.addListener(this::commonSetup);

        itemValuesLoader = new ItemValuesLoader();
        recipeLoader = new CookingPotRecipeLoader();
        //iconLoader = new JEIIconLoader();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModCreative.CREATIVE_MODE_TABS.register(modEventBus);
        ModLootModifiers.MODIFIER_SERIALIZERS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (LunchboxSustenanceProject) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        // 将自定义加载器添加到服务器资源重载列表中
        event.addListener(itemValuesLoader);
        event.addListener(recipeLoader);
        //event.addListener(iconLoader);
    }

    public static ItemValuesLoader getItemValuesLoader() {
        return itemValuesLoader;
    }

    public static CookingPotRecipeLoader getRecipeLoader() {
        return recipeLoader;
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public static ResourceLocation getItemResourceLocation(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
    public static boolean hasItemValues(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        ItemValuesLoader loader = getItemValuesLoader();
        if (loader == null) {
            return false;
        }

        return loader.hasValues(stack.getItem());
    }

}
