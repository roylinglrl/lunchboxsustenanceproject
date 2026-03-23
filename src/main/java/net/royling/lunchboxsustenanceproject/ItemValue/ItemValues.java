package net.royling.lunchboxsustenanceproject.ItemValue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.MODID;

public class ItemValues {
    private final Map<ResourceLocation, Float> values;
    private final ResourceLocation containerItem;

    public ItemValues(Map<ResourceLocation, Float> values, ResourceLocation containerItem) {
        this.values = values;
        this.containerItem = containerItem;
    }
    public ItemValues(Map<ResourceLocation, Float> values) {
        this(values, null);
    }

    public static ItemValues fromJson(JsonObject json) {
        Map<ResourceLocation, Float> values = new HashMap<>();
        ResourceLocation container = null;

        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            String key = entry.getKey();

            if ("container".equals(key)) {
                String containerStr = entry.getValue().getAsString();
                container = ResourceLocation.parse(containerStr);
                continue;
            }

            ResourceLocation type;
            if (key.contains(":")) {
                type = ResourceLocation.parse(key);
            } else {
                type = ResourceLocation.fromNamespaceAndPath(MODID, key);
            }
            float value = entry.getValue().getAsFloat();
            values.put(type, value);
        }

        return new ItemValues(values, container);
    }

    public Map<ResourceLocation, Float> getValues() {
        return values;
    }

    public float getValue(ResourceLocation type) {
        return values.getOrDefault(type, 0.0f);
    }
    public float getValue(String propertyName) {
        ResourceLocation key;
        if (propertyName.contains(":")) {
            key = ResourceLocation.parse(propertyName);
        } else {
            key = ResourceLocation.fromNamespaceAndPath(MODID, propertyName);
        }
        return getValue(key);
    }

    public ResourceLocation getContainerItem() {
        return containerItem;
    }
    public boolean hasContainer() {
        return containerItem != null;
    }


    @Override
    public String toString() {
        if (values.isEmpty()) {
            return "ItemValues{empty}";
        }

        StringBuilder sb = new StringBuilder("ItemValues{");
        boolean first = true;
        for (Map.Entry<ResourceLocation, Float> entry : values.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            // 显示完整的 ResourceLocation
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }

        if (hasContainer()) {
            sb.append(", container=").append(containerItem);
        }

        sb.append("}");
        return sb.toString();
    }
}
