package net.royling.lunchboxsustenanceproject.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.ModBlocks.ModBlocks;

import java.util.function.Supplier;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LunchboxSustenanceProject.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //blockWithItem(ModBlocks.TOMATO_BLOCK);
        //CrossBlockAndItem(ModBlocks.TOMATO_STEM.get());
    }

    private void blockWithItem(Supplier<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void CrossBlockAndItem(Block block){
        String blockName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        simpleBlock(block,models().cross(blockName, ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID,"block/"+blockName)));
        models().getBuilder(blockName).parent(models().getExistingFile(mcLoc("block/cross")))
                .texture("cross",ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID,"block/"+blockName))
                .renderType("cutout");
        itemModels().getBuilder(blockName).parent(models().getExistingFile(mcLoc("item/generated")))
                .texture("layer0",ResourceLocation.fromNamespaceAndPath(LunchboxSustenanceProject.MODID,"block/"+blockName));
    }
}
