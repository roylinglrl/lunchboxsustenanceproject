package net.royling.lunchboxsustenanceproject.LootTableModifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.royling.lunchboxsustenanceproject.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class FishModifier extends LootModifier {
    public static final MapCodec<FishModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst->
                    LootModifier.codecStart(inst)
                            .apply(inst,FishModifier::new));

    private static final List<ItemStack> REPLACEMENT_ITEMS = List.of(
            ModItems.LOBSTER.get().getDefaultInstance(),
            ModItems.BARNACLE.get().getDefaultInstance(),
            ModItems.SEA_CUCUMBER.get().getDefaultInstance(),
            ModItems.SMALL_HALIBUT.get().getDefaultInstance(),
            ModItems.HALIBUT.get().getDefaultInstance(),
            ModItems.EEL.get().getDefaultInstance(),
            ModItems.TUNA.get().getDefaultInstance(),
            ModItems.PIKE.get().getDefaultInstance(),
            ModItems.TILAPIA.get().getDefaultInstance(),
            ModItems.HERRING.get().getDefaultInstance(),
            ModItems.CATFISH.get().getDefaultInstance(),
            ModItems.GROUPER.get().getDefaultInstance(),
            ModItems.BLACK_SEABREAM.get().getDefaultInstance(),
            ModItems.SEAHORSE.get().getDefaultInstance(),
            ModItems.SARDINE.get().getDefaultInstance(),
            ModItems.SILVERFISH.get().getDefaultInstance(),
            ModItems.BASS.get().getDefaultInstance(),
            ModItems.OCTOPUS.get().getDefaultInstance(),
            ModItems.JELLYFISH.get().getDefaultInstance(),
            ModItems.STURGEON.get().getDefaultInstance(),
            ModItems.BREAM.get().getDefaultInstance(),
            ModItems.SHRIMP.get().getDefaultInstance(),
            ModItems.CRAB.get().getDefaultInstance()
    );
    protected FishModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }


    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.FISHING_FISH.location().toString()))) return generatedLoot;

        if (context.getRandom().nextFloat() <= 0.25) {
            ItemStack randomReplacement = getRandomReplacement(context.getRandom());
            generatedLoot.set(0, randomReplacement);
        }
        return generatedLoot;
    }

    private ItemStack getRandomReplacement(RandomSource random) {
        int index = random.nextInt(REPLACEMENT_ITEMS.size());
        return REPLACEMENT_ITEMS.get(index);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
