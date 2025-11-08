package net.royling.lunchboxsustenanceproject.ModBlocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.cookingPot.CookingPotBlock;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            BuiltInRegistries.BLOCK,
            LunchboxSustenanceProject.MODID
    );
    public static final Supplier<Block> COOKING_POT = BLOCKS.register("cooking_pot",
            () -> new CookingPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    /*public static final Supplier<Block> TOMATO_BLOCK = BLOCKS.register("tomato_block",
            ()->new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MELON)));
    public static final Supplier<Block> TOMATO_STEM = BLOCKS.register("tomato_stem",
            ()->new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));*/
}
