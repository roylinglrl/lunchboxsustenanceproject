package net.royling.lunchboxsustenanceproject.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodValueKubeEvent implements KubeEvent {
    public final Map<ResourceLocation, Map<ResourceLocation, Float>> additions    = new HashMap<>();
    public final Map<ResourceLocation, Map<ResourceLocation, Float>> modifications = new HashMap<>();
    public final List<ResourceLocation> removals = new ArrayList<>();
    private static final String MODID = "lunchboxsustenanceproject";

    /**
     * 添加/覆盖属性值
     *
     * LunchboxEvents.foodValues(event => {
     *     event.add('minecraft:bread', 'cereals', 2.0)
     *     event.add('minecraft:bread', 'magic', 0.5)
     * })
     */
    public void add(String itemId, String property, float value) {
        additions.computeIfAbsent(
                ResourceLocation.parse(itemId), k -> new HashMap<>()
        ).put(toRL(property), value);
    }
    /**
     * 修改已有属性值
     *
     * event.modify('minecraft:apple', 'fruit', 3.0)
     */
    public void modify(String itemId, String property, float value) {
        modifications.computeIfAbsent(
                ResourceLocation.parse(itemId), k -> new HashMap<>()
        ).put(toRL(property), value);
    }
    /**
     * 移除某物品的指定属性
     *
     * event.removeProperty('minecraft:rotten_flesh', 'meat')
     */
    public void removeProperty(String itemId, String property) {
        // 用 NaN 作为删除标记
        modifications.computeIfAbsent(
                ResourceLocation.parse(itemId), k -> new HashMap<>()
        ).put(toRL(property), Float.NaN);
    }
    /**
     * 移除某物品的全部属性
     *
     * event.remove('minecraft:pufferfish')
     */
    public void remove(String itemId) {
        removals.add(ResourceLocation.parse(itemId));
    }
    // 属性名没有命名空间时补上 modid
    private ResourceLocation toRL(String property) {
        return property.contains(":")
                ? ResourceLocation.parse(property)
                : ResourceLocation.fromNamespaceAndPath(MODID, property);
    }

}
