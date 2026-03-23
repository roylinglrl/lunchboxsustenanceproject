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
import net.royling.lunchboxsustenanceproject.kubejs.FoodValueKubeEvent;
import net.royling.lunchboxsustenanceproject.kubejs.LunchboxEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.MODID;

public class ItemValuesLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String FOLDER_NAME = "foodvalues";

    private Map<ResourceLocation, ItemValues> itemValuesCache = ImmutableMap.of();

    public ItemValuesLoader() {
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

                for (Map.Entry<String, JsonElement> itemEntry : json.entrySet()) {
                    String itemIdStr = itemEntry.getKey();
                    JsonObject itemJson = itemEntry.getValue().getAsJsonObject();
                    ResourceLocation itemIdentifier = ResourceLocation.parse(itemIdStr);
                    if (!BuiltInRegistries.ITEM.containsKey(itemIdentifier)) {
                        LOGGER.warn("Item {} does not exist, skipping food values", itemIdentifier);
                        continue;
                    }
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
        this.itemValuesCache = builder.build();
        LOGGER.info("Loaded {} item value entries", successCount);
        applyKubeJS();
    }
    private void applyKubeJS() {
        FoodValueKubeEvent e = new FoodValueKubeEvent();
        LunchboxEvents.FOOD_VALUES.post(e);   // 触发脚本

        Map<ResourceLocation, ItemValues> cache = new HashMap<>(itemValuesCache);

        // 全量移除
        e.removals.forEach(cache::remove);

        // 添加/覆盖
        e.additions.forEach((itemId, props) -> {
            ItemValues existing = cache.get(itemId);
            Map<ResourceLocation, Float> merged = existing != null
                    ? new HashMap<>(existing.getValues()) : new HashMap<>();
            merged.putAll(props);
            cache.put(itemId, new ItemValues(merged,
                    existing != null ? existing.getContainerItem() : null));
        });

        // 修改（NaN = 删除该属性）
        e.modifications.forEach((itemId, props) -> {
            ItemValues existing = cache.get(itemId);
            if (existing == null) return;
            Map<ResourceLocation, Float> m = new HashMap<>(existing.getValues());
            props.forEach((k, v) -> {
                if (Float.isNaN(v)) m.remove(k);
                else                m.put(k, v);
            });
            cache.put(itemId, new ItemValues(m, existing.getContainerItem()));
        });

        this.itemValuesCache = ImmutableMap.copyOf(cache);
        LOGGER.info("[KubeJS] food values applied");
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
