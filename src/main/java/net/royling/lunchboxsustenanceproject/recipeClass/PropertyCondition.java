package net.royling.lunchboxsustenanceproject.recipeClass;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.royling.lunchboxsustenanceproject.ItemValue.ItemValues;

import java.util.List;

import static net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject.MODID;

public class PropertyCondition implements RecipeCondition{
    private final String propertyName;
    private final Float minValue;
    private final Float maxValue;

    public PropertyCondition(String propertyName, Float minValue, Float maxValue) {
        this.propertyName = propertyName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static PropertyCondition fromJson(JsonObject json) {
        String property = json.get("property").getAsString();
        Float min = json.has("min") ? json.get("min").getAsFloat() : null;
        Float max = json.has("max") ? json.get("max").getAsFloat() : null;
        return new PropertyCondition(property, min, max);
    }

    @Override
    public boolean matches(List<ItemStack> inputs, ItemValues[] inputValues) {
        // 计算所有输入物品的该属性值的总和
        float totalValue = 0.0f;

        ResourceLocation propertyKey;
        if (propertyName.contains(":")) {
            // 如果属性名包含冒号，直接解析为 ResourceLocation
            propertyKey = ResourceLocation.parse(propertyName);
        } else {
            // 如果属性名不包含冒号，使用默认命名空间
            propertyKey = ResourceLocation.fromNamespaceAndPath(MODID, propertyName);
        }

        for (int i = 0; i < inputs.size(); i++) {
            if (!inputs.get(i).isEmpty() && inputValues[i] != null) {
                // 使用正确的 ResourceLocation 获取值
                float value = inputValues[i].getValue(propertyKey);
                totalValue += value;
            }
        }
        // 检查总和是否在范围内
        if (minValue != null && totalValue < minValue) {
            return false;
        }
        if (maxValue != null && totalValue > maxValue) {
            return false;
        }

        return true;
    }

    @Override
    public String getType() {
        return "property";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Property[");
        sb.append(propertyName);
        if (minValue != null) sb.append(" min=").append(minValue);
        if (maxValue != null) sb.append(" max=").append(maxValue);
        sb.append("]");
        return sb.toString();
    }
    public String getPropertyName() {
        return propertyName;
    }

    public Float getMinValue() {
        return minValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }
}
