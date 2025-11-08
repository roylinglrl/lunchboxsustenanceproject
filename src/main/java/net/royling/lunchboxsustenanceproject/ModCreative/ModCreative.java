package net.royling.lunchboxsustenanceproject.ModCreative;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.royling.lunchboxsustenanceproject.LunchboxSustenanceProject;
import net.royling.lunchboxsustenanceproject.ModItems;

public class ModCreative {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LunchboxSustenanceProject.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COOKING_POT = CREATIVE_MODE_TABS.register("cooking_pot", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.cooking_pot"))
            .icon(() -> ModItems.COOKING_POT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.COOKING_POT.get());
                output.accept(ModItems.FAILED_CONCOCTION.get());
                output.accept(ModItems.BACON_AND_EGGS.get());
                output.accept(ModItems.APPLE_PIE.get());
                output.accept(ModItems.GOLDEN_APPLE_PIE.get());
                output.accept(ModItems.FISH_ROLL.get());
                output.accept(ModItems.FRIED_FISH.get());
                output.accept(ModItems.JAM.get());
                output.accept(ModItems.RABBIT_SANDWICH.get());
                output.accept(ModItems.FRUIT_SALAD.get());
                output.accept(ModItems.HONEY_GLAZED_HAM.get());
                output.accept(ModItems.HONEY_GLAZED_PORK.get());
                output.accept(ModItems.MEAT_SKEWER.get());
                output.accept(ModItems.MEATBALL.get());
                output.accept(ModItems.MEAT_SOUP.get());
                output.accept(ModItems.MONSTER_LASAGNA.get());
                output.accept(ModItems.EGG_DUMPLING.get());
                output.accept(ModItems.PUMPKIN_COOKIE.get());
                output.accept(ModItems.STIR_FRY.get());
                output.accept(ModItems.STUFFED_POTATO.get());
                output.accept(ModItems.TOFFEE.get());
                output.accept(ModItems.TURKEY_DINNER.get());
                output.accept(ModItems.COD_DISH.get());
                output.accept(ModItems.WAFFLE.get());
                output.accept(ModItems.WATERMELON_POPSICLE.get());
                output.accept(ModItems.MIXED_NUTS.get());
                output.accept(ModItems.ICE_CREAM.get());
                output.accept(ModItems.FLOWER_SALAD.get());
                output.accept(ModItems.STIR_FRIED_PORK.get());
                output.accept(ModItems.SEAFOOD_BISQUE.get());
                output.accept(ModItems.CEVICHE.get());
                output.accept(ModItems.CALIFORNIA_ROLL.get());
                output.accept(ModItems.SURF_AND_TURF.get());
                output.accept(ModItems.RAINBOW_CANDY.get());
                output.accept(ModItems.APPLE_JELLY.get());
                output.accept(ModItems.CARROT_SOUP.get());
                output.accept(ModItems.MEAT_BUN.get());
                output.accept(ModItems.FRESH_SALSA.get());
                output.accept(ModItems.CREAMED_POTATOES.get());
                output.accept(ModItems.POTATO_TOWER.get());
                output.accept(ModItems.VEGETABLE_COCKTAIL.get());
                output.accept(ModItems.MONSTER_TARTARE.get());
                output.accept(ModItems.FRESH_FRUIT_CREPE.get());
                output.accept(ModItems.BONE_SOUP.get());
                output.accept(ModItems.GLOW_BERRY_MOUSSE.get());
                output.accept(ModItems.SEAFOOD_CHOWDER.get());
                output.accept(ModItems.BLAZE_JELLY.get());
                output.accept(ModItems.FLUFFY_POTATO_SOUFFLE.get());
                output.accept(ModItems.LOBSTER_DINNER.get());
                output.accept(ModItems.LOBSTER_SOUP.get());
                output.accept(ModItems.STUFFED_FISH_HEAD.get());
                output.accept(ModItems.BARNACLE_PITA.get());
                output.accept(ModItems.BARNACLE_LINGUINE.get());
                output.accept(ModItems.BARNACLE_SUSHI.get());
                output.accept(ModItems.TOMATO_SCRAMBLED_EGGS.get());
                output.accept(ModItems.LOBSTER.get());
                output.accept(ModItems.COOKED_LOBSTER.get());
                output.accept(ModItems.BARNACLE.get());
                output.accept(ModItems.COOKED_BARNACLE.get());
                output.accept(ModItems.BUTTER.get());
                output.accept(ModItems.LOBSTER.get());
                output.accept(ModItems.COOKED_LOBSTER.get());

                output.accept(ModItems.SEA_CUCUMBER.get());
                output.accept(ModItems.COOKED_SEA_CUCUMBER.get());

                output.accept(ModItems.SMALL_HALIBUT.get());
                output.accept(ModItems.COOKED_SMALL_HALIBUT.get());

                output.accept(ModItems.HALIBUT.get());
                output.accept(ModItems.COOKED_HALIBUT.get());

                output.accept(ModItems.EEL.get());
                output.accept(ModItems.COOKED_EEL.get());

                output.accept(ModItems.TUNA.get());
                output.accept(ModItems.COOKED_TUNA.get());

                output.accept(ModItems.PIKE.get());
                output.accept(ModItems.COOKED_PIKE.get());

                output.accept(ModItems.TILAPIA.get());
                output.accept(ModItems.COOKED_TILAPIA.get());

                output.accept(ModItems.HERRING.get());
                output.accept(ModItems.COOKED_HERRING.get());

                output.accept(ModItems.CATFISH.get());
                output.accept(ModItems.COOKED_CATFISH.get());

                output.accept(ModItems.GROUPER.get());
                output.accept(ModItems.COOKED_GROUPER.get());

                output.accept(ModItems.BLACK_SEABREAM.get());
                output.accept(ModItems.COOKED_BLACK_SEABREAM.get());

                output.accept(ModItems.SEAHORSE.get());
                output.accept(ModItems.COOKED_SEAHORSE.get());

                output.accept(ModItems.SARDINE.get());
                output.accept(ModItems.COOKED_SARDINE.get());

                output.accept(ModItems.SILVERFISH.get());
                output.accept(ModItems.COOKED_SILVERFISH.get());

                output.accept(ModItems.BASS.get());
                output.accept(ModItems.COOKED_BASS.get());

                output.accept(ModItems.OCTOPUS.get());
                output.accept(ModItems.COOKED_OCTOPUS.get());

                output.accept(ModItems.JELLYFISH.get());
                output.accept(ModItems.COOKED_JELLYFISH.get());

                output.accept(ModItems.STURGEON.get());
                output.accept(ModItems.COOKED_STURGEON.get());

                output.accept(ModItems.BREAM.get());
                output.accept(ModItems.COOKED_BREAM.get());

                output.accept(ModItems.SHRIMP.get());
                output.accept(ModItems.COOKED_SHRIMP.get());

                output.accept(ModItems.CRAB.get());
                output.accept(ModItems.COOKED_CRAB.get());

                //output.accept(ModItems.TOMATO_BLOCK.get());
                //output.accept(ModItems.TOMATO_STEM.get());
            }).build());

}
