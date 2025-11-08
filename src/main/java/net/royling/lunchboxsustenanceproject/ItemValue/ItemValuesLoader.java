package net.royling.lunchboxsustenanceproject.ItemValue;

import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Map;

import static net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.MODID;

public class ItemValuesLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();

    // 明确定义目录名，用于内部访问
    public static final String FOLDER_NAME = "foodvalues";

    // 存储加载的数据：物品ID (ResourceLocation) -> 自定义值 (ItemValues)
    private Map<ResourceLocation, ItemValues> itemValuesCache = ImmutableMap.of();

    // 构造函数：指定加载器的名称 FOLDER_NAME
    public ItemValuesLoader() {
        // 使用自定义的 Gson 实例
        super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), FOLDER_NAME);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        ImmutableMap.Builder<ResourceLocation, ItemValues> builder = ImmutableMap.builder();
        int successCount = 0;

        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation fileRL = entry.getKey();
            JsonObject json = entry.getValue().getAsJsonObject();

            try {
                // 新的JSON结构：每个文件包含多个物品的定义
                // 文件格式示例：
                // {
                //   "minecraft:apple": {
                //     "nutrition": 4.0,
                //     "saturation": 0.3
                //   },
                //   "minecraft:beef": {
                //     "nutrition": 3.0,
                //     "saturation": 0.5,
                //     "container": "minecraft:bowl"
                //   }
                // }

                // 遍历JSON对象中的所有条目，每个条目对应一个物品
                for (Map.Entry<String, JsonElement> itemEntry : json.entrySet()) {
                    String itemIdStr = itemEntry.getKey();
                    JsonObject itemJson = itemEntry.getValue().getAsJsonObject();

                    // 解析物品ID
                    ResourceLocation itemIdentifier = ResourceLocation.parse(itemIdStr);

                    // 检查物品是否存在
                    if (!BuiltInRegistries.ITEM.containsKey(itemIdentifier)) {
                        LOGGER.warn("Item {} does not exist, skipping food values", itemIdentifier);
                        continue;
                    }

                    // 从物品的JSON对象创建ItemValues
                    ItemValues values = ItemValues.fromJson(itemJson);
                    builder.put(itemIdentifier, values);
                    successCount++;

                    LOGGER.debug("Loaded food values for {}: {}", itemIdentifier, values);
                }

            } catch (Exception e) {
                LOGGER.error("Error parsing item values for file: {}", fileRL, e);
            }
        }

        this.itemValuesCache = builder.build();
        LOGGER.info("Loaded {} custom item value entries successfully.", successCount);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Items with food values:");
            for (ResourceLocation itemId : itemValuesCache.keySet()) {
                LOGGER.debug(" - {}: {}", itemId, itemValuesCache.get(itemId));
            }
        }
    }

    public ItemValues getValues(Item item) {
        ResourceLocation itemRL = LunchboxSustenanceProject.getItemResourceLocation(item);
        if (itemRL == null) {
            return new ItemValues(ImmutableMap.of());
        }
        return itemValuesCache.getOrDefault(itemRL, new ItemValues(ImmutableMap.of()));
    }

    public boolean hasValues(Item item) {
        ResourceLocation itemId = LunchboxSustenanceProject.getItemResourceLocation(item);
        if (itemId == null) {
            return false;
        }
        return itemValuesCache.containsKey(itemId);
    }

    public Collection<ResourceLocation> getItemsWithValues() {
        return itemValuesCache.keySet();
    }
}
