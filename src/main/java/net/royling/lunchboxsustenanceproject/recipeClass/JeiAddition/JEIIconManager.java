package net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.MODID;

public class JEIIconManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, ItemStack> propertyIcons = new HashMap<>();

    public JEIIconManager(ResourceManager resourceManager) {
        loadPropertyIconsFromConfig();
    }
    private void loadPropertyIconsFromConfig() {
        try {
            List<String> configIcons = JEIIconConfig.icons.get();

            for (String entry : configIcons) {
                String[] parts = entry.split("=", 2);
                if (parts.length == 2) {
                    String property = parts[0].trim();
                    String itemId = parts[1].trim();
                    Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId));
                    if (item != null) {
                        propertyIcons.put(property, new ItemStack(item));
                        LOGGER.info("Loaded JEI icon for property '{}': {}", property, itemId);
                    } else {
                        LOGGER.warn("Invalid item ID '{}' for property '{}'", itemId, property);
                    }
                } else {
                    LOGGER.warn("Invalid config entry: {}", entry);
                }
            }
            LOGGER.info("Successfully loaded {} JEI property icons from config", propertyIcons.size());
        } catch (Exception e) {
            LOGGER.error("Failed to load JEI property icons from config, using defaults", e);
            createDefaultIcons();
        }
    }

    private void createDefaultIcons() {
        // 创建一些默认的图标映射
        addDefaultIcon("fruit", "minecraft:apple");
        addDefaultIcon("vegetable", "minecraft:carrot");
        addDefaultIcon("protein", "minecraft:cooked_beef");
        addDefaultIcon("grain", "minecraft:bread");
        addDefaultIcon("sweet", "minecraft:cookie");
    }

    private void addDefaultIcon(String property, String itemId) {
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId));
        if (item != null) {
            propertyIcons.put(property, new ItemStack(item));
        }
    }

    public ItemStack getIconForProperty(String propertyName) {
        // 先尝试完整属性名
        ItemStack icon = propertyIcons.get(propertyName);
        if (icon != null) {
            return icon;
        }

        // 如果属性名包含命名空间，尝试只用路径部分
        if (propertyName.contains(":")) {
            String path = propertyName.split(":")[1];
            icon = propertyIcons.get(path);
        }

        return icon != null ? icon : ItemStack.EMPTY;
    }

    public List<ItemStack> getItemsForTag(TagKey<Item> tag) {
        List<ItemStack> items = new ArrayList<>();
        BuiltInRegistries.ITEM.getTag(tag).ifPresent(holders -> {
            holders.forEach(holder -> {
                Item item = holder.value();
                items.add(new ItemStack(item));
            });
        });
        return items;
    }
}
