package net.royling.lunchboxsustenanceproject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.royling.lunchboxsustenanceproject.ModBlocks.ModBlocks;
import net.royling.lunchboxsustenanceproject.cookingPot.CookingPotBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LunchboxSustenanceProject.MODID);

    public static final Supplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT =
            BLOCK_ENTITY_TYPES.register("cooking_pot",
                    () -> BlockEntityType.Builder.of(CookingPotBlockEntity::new, ModBlocks.COOKING_POT.get()).build(null));
}
