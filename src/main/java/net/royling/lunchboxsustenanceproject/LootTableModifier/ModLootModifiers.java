package net.royling.lunchboxsustenanceproject.LootTableModifier;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LunchboxSustenanceProject.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> FISHING_fish =
            MODIFIER_SERIALIZERS.register("fishing_fish", () -> FishModifier.CODEC);

}
