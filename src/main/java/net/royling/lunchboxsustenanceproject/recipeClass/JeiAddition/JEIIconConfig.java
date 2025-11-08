package net.royling.lunchboxsustenanceproject.recipeClass.JeiAddition;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.royling.lunchboxsustenanceproject.Config;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class JEIIconConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<List<String>> icons;

    static {
        BUILDER.push("property_icon");
        List<String> result = new ArrayList<>();
        result.add("fruit=lunchboxsustenanceproject:fruit_icon");
        result.add("sugar=lunchboxsustenanceproject:sugar_icon");
        result.add("not_eat=lunchboxsustenanceproject:not_eat_icon");
        result.add("magic=lunchboxsustenanceproject:magic_icon");
        result.add("ender=lunchboxsustenanceproject:ender_icon");
        result.add("vegetable=lunchboxsustenanceproject:vegetable_icon");
        result.add("monster=lunchboxsustenanceproject:monster_icon");
        result.add("ice=lunchboxsustenanceproject:ice_icon");
        result.add("meat=lunchboxsustenanceproject:meat_icon");
        result.add("fish=lunchboxsustenanceproject:fish_icon");
        result.add("cereals=lunchboxsustenanceproject:cereals_icon");
        result.add("egg=lunchboxsustenanceproject:egg_icon");
        result.add("milk=lunchboxsustenanceproject:milk_icon");
        result.add("seed=lunchboxsustenanceproject:seed_icon");
        result.add("mushroom=lunchboxsustenanceproject:mushroom_icon");
        // 默认为空列表
        icons = BUILDER
                .comment("食材值对应的图标，格式: 属性=物品ID")
                .define("property_icons", result);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
