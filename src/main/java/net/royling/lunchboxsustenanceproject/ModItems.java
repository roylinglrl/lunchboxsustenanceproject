package net.royling.lunchboxsustenanceproject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.royling.lunchboxsustenanceproject.ModBlocks.ModBlocks;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, LunchboxSustenanceProject.MODID);

    public static final Supplier<Item> FRUIT_ICON =
            ITEMS.register("fruit_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> SUGAR_ICON =
            ITEMS.register("sugar_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> NOT_EAT_ICON =
            ITEMS.register("not_eat_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> MAGIC_ICON =
            ITEMS.register("magic_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> ENDER_ICON =
            ITEMS.register("ender_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> VEGETABLE_ICON =
            ITEMS.register("vegetable_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> MONSTER_ICON =
            ITEMS.register("monster_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> ICE_ICON =
            ITEMS.register("ice_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> MEAT_ICON =
            ITEMS.register("meat_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> FISH_ICON =
            ITEMS.register("fish_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> CEREALS_ICON =
            ITEMS.register("cereals_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> EGG_ICON =
            ITEMS.register("egg_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> MILK_ICON =
            ITEMS.register("milk_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> SEED_ICON =
            ITEMS.register("seed_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> MUSHROOM_ICON =
            ITEMS.register("mushroom_icon",
                    ()-> new Item(new Item.Properties()));

    public static final Supplier<Item> FAILED_CONCOCTION =
            ITEMS.register("failed_concoction",
                    () -> new Item(new Item.Properties()));


    public static final Supplier<Item> COOKING_POT =
            ITEMS.register("cooking_pot",
                    ()->new BlockItem(ModBlocks.COOKING_POT.get(),new Item.Properties()));

    public static final Supplier<Item> BACON_AND_EGGS = ITEMS.register("bacon_and_eggs",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())){
            });

    public static final Supplier<Item> APPLE_PIE = ITEMS.register("apple_pie",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));

    public static final Supplier<Item> GOLDEN_APPLE_PIE = ITEMS.register("golden_apple_pie",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,600,0),1f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .effect(()->new MobEffectInstance(MobEffects.FIRE_RESISTANCE,600,0),1f)
                    .build())));

    public static final Supplier<Item> FISH_ROLL = ITEMS.register("fish_roll",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> FRIED_FISH = ITEMS.register("fried_fish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));

    public static final Supplier<Item> JAM = ITEMS.register("jam",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,300,0),1f)
                    .build())));

    public static final Supplier<Item> RABBIT_SANDWICH = ITEMS.register("rabbit_sandwich",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> FRUIT_SALAD = ITEMS.register("fruit_salad",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600,0),1f)
                    .build())));

    public static final Supplier<Item> HONEY_GLAZED_HAM = ITEMS.register("honey_glazed_ham",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.4f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,1),1f)
                    .build())));

    public static final Supplier<Item> HONEY_GLAZED_PORK = ITEMS.register("honey_glazed_pork",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));

    public static final Supplier<Item> MEAT_SKEWER = ITEMS.register("meat_skewer",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).build())));

    public static final Supplier<Item> MEATBALL = ITEMS.register("meatball",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.4f).build())));

    public static final Supplier<Item> MEAT_SOUP = ITEMS.register("meat_soup",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(15).saturationModifier(0.6f).build())));

    public static final Supplier<Item> MONSTER_LASAGNA = ITEMS.register("monster_lasagna",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.POISON,160,0),1f)
                    .effect(()->new MobEffectInstance(MobEffects.HARM,1,0),1f)
                    .build())));

    public static final Supplier<Item> EGG_DUMPLING = ITEMS.register("egg_dumpling",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> PUMPKIN_COOKIE = ITEMS.register("pumpkin_cookie",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(0.3f).build())));

    public static final Supplier<Item> STIR_FRY = ITEMS.register("stir_fry",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f).build())));

    public static final Supplier<Item> STUFFED_POTATO = ITEMS.register("stuffed_potato",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.33f).build())));

    public static final Supplier<Item> TOFFEE = ITEMS.register("toffee",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f)
                    .effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1200,0),1f)
                    .build())));

    public static final Supplier<Item> TURKEY_DINNER = ITEMS.register("turkey_dinner",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.6f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> COD_DISH = ITEMS.register("cod_dish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> WAFFLE = ITEMS.register("waffle",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));

    public static final Supplier<Item> WATERMELON_POPSICLE = ITEMS.register("watermelon_popsicle",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600,0),1f)
                    .build())));

    public static final Supplier<Item> MIXED_NUTS = ITEMS.register("mixed_nuts",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> ICE_CREAM = ITEMS.register("ice_cream",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> FLOWER_SALAD = ITEMS.register("flower_salad",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> STIR_FRIED_PORK = ITEMS.register("stir_fried_pork",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> SEAFOOD_BISQUE = ITEMS.register("seafood_bisque",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> CEVICHE = ITEMS.register("ceviche",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> CALIFORNIA_ROLL = ITEMS.register("california_roll",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> SURF_AND_TURF = ITEMS.register("surf_and_turf",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> RAINBOW_CANDY = ITEMS.register("rainbow_candy",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,1200,1),1f)
                    .build())));

    public static final Supplier<Item> APPLE_JELLY = ITEMS.register("apple_jelly",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> CARROT_SOUP = ITEMS.register("carrot_soup",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.2f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> MEAT_BUN = ITEMS.register("meat_bun",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> FRESH_SALSA = ITEMS.register("fresh_salsa",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).build())));

    public static final Supplier<Item> CREAMED_POTATOES = ITEMS.register("creamed_potatoes",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> POTATO_TOWER = ITEMS.register("potato_tower",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> VEGETABLE_COCKTAIL = ITEMS.register("vegetable_cocktail",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.6f).build())));

    public static final Supplier<Item> MONSTER_TARTARE = ITEMS.register("monster_tartare",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.4f)
                    .effect(()->new MobEffectInstance(MobEffects.HARM,1,0),1f)
                    .build())));

    public static final Supplier<Item> FRESH_FRUIT_CREPE = ITEMS.register("fresh_fruit_crepe",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(15).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,1200,1),1f)
                    .build())));

    public static final Supplier<Item> BONE_SOUP = ITEMS.register("bone_soup",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));

    public static final Supplier<Item> GLOW_BERRY_MOUSSE = ITEMS.register("glow_berry_mousse",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.GLOWING,600,0),1f)
                    .build())));

    public static final Supplier<Item> SEAFOOD_CHOWDER = ITEMS.register("seafood_chowder",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(11).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> BLAZE_JELLY = ITEMS.register("blaze_jelly",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.DAMAGE_BOOST,1800,1),1f)
                    .build())));

    public static final Supplier<Item> FLUFFY_POTATO_SOUFFLE = ITEMS.register("fluffy_potato_souffle",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> LOBSTER_DINNER = ITEMS.register("lobster_dinner",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> LOBSTER_SOUP = ITEMS.register("lobster_soup",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,1),1f)
                    .build())));

    public static final Supplier<Item> STUFFED_FISH_HEAD = ITEMS.register("stuffed_fish_head",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationModifier(0.1f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> BARNACLE_PITA = ITEMS.register("barnacle_pita",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,200,0),1f)
                    .build())));

    public static final Supplier<Item> BARNACLE_LINGUINE = ITEMS.register("barnacle_linguine",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.HEAL,1,0),1f)
                    .build())));

    public static final Supplier<Item> BARNACLE_SUSHI = ITEMS.register("barnacle_sushi",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));

    public static final Supplier<Item> TOMATO_SCRAMBLED_EGGS = ITEMS.register("tomato_scrambled_eggs",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3f)
                    .effect(()->new MobEffectInstance(MobEffects.REGENERATION,400,0),1f)
                    .build())));
    public static final Supplier<Item> LOBSTER = ITEMS.register("lobster",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final Supplier<Item> COOKED_LOBSTER = ITEMS.register("cooked_lobster",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    public static final Supplier<Item> BARNACLE = ITEMS.register("barnacle",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final Supplier<Item> COOKED_BARNACLE = ITEMS.register("cooked_barnacle",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    public static final Supplier<Item> BUTTER = ITEMS.register("butter",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 生海参
    public static final Supplier<Item> SEA_CUCUMBER = ITEMS.register("sea_cucumber",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟海参
    public static final Supplier<Item> COOKED_SEA_CUCUMBER = ITEMS.register("cooked_sea_cucumber",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生小比目鱼
    public static final Supplier<Item> SMALL_HALIBUT = ITEMS.register("small_halibut",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟小比目鱼
    public static final Supplier<Item> COOKED_SMALL_HALIBUT = ITEMS.register("cooked_small_halibut",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生大比目鱼
    public static final Supplier<Item> HALIBUT = ITEMS.register("halibut",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟大比目鱼
    public static final Supplier<Item> COOKED_HALIBUT = ITEMS.register("cooked_halibut",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生鳗鱼
    public static final Supplier<Item> EEL = ITEMS.register("eel",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟鳗鱼
    public static final Supplier<Item> COOKED_EEL = ITEMS.register("cooked_eel",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生大金枪鱼
    public static final Supplier<Item> TUNA = ITEMS.register("tuna",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟大金枪鱼
    public static final Supplier<Item> COOKED_TUNA = ITEMS.register("cooked_tuna",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生狗鱼
    public static final Supplier<Item> PIKE = ITEMS.register("pike",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟狗鱼
    public static final Supplier<Item> COOKED_PIKE = ITEMS.register("cooked_pike",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生罗非鱼
    public static final Supplier<Item> TILAPIA = ITEMS.register("tilapia",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟罗非鱼
    public static final Supplier<Item> COOKED_TILAPIA = ITEMS.register("cooked_tilapia",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生西鲱鱼
    public static final Supplier<Item> HERRING = ITEMS.register("herring",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟西鲱鱼
    public static final Supplier<Item> COOKED_HERRING = ITEMS.register("cooked_herring",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生鲶鱼
    public static final Supplier<Item> CATFISH = ITEMS.register("catfish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟鲶鱼
    public static final Supplier<Item> COOKED_CATFISH = ITEMS.register("cooked_catfish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生青斑鱼
    public static final Supplier<Item> GROUPER = ITEMS.register("grouper",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟青斑鱼
    public static final Supplier<Item> COOKED_GROUPER = ITEMS.register("cooked_grouper",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生黑鯛鱼
    public static final Supplier<Item> BLACK_SEABREAM = ITEMS.register("black_seabream",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟黑鯛鱼
    public static final Supplier<Item> COOKED_BLACK_SEABREAM = ITEMS.register("cooked_black_seabream",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生海马
    public static final Supplier<Item> SEAHORSE = ITEMS.register("seahorse",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟海马
    public static final Supplier<Item> COOKED_SEAHORSE = ITEMS.register("cooked_seahorse",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生沙丁鱼
    public static final Supplier<Item> SARDINE = ITEMS.register("sardine",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟沙丁鱼
    public static final Supplier<Item> COOKED_SARDINE = ITEMS.register("cooked_sardine",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生银鱼
    public static final Supplier<Item> SILVERFISH = ITEMS.register("silverfish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟银鱼
    public static final Supplier<Item> COOKED_SILVERFISH = ITEMS.register("cooked_silverfish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生鲈鱼
    public static final Supplier<Item> BASS = ITEMS.register("bass",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟鲈鱼
    public static final Supplier<Item> COOKED_BASS = ITEMS.register("cooked_bass",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生章鱼
    public static final Supplier<Item> OCTOPUS = ITEMS.register("octopus",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟章鱼
    public static final Supplier<Item> COOKED_OCTOPUS = ITEMS.register("cooked_octopus",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生水母
    public static final Supplier<Item> JELLYFISH = ITEMS.register("jellyfish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟水母
    public static final Supplier<Item> COOKED_JELLYFISH = ITEMS.register("cooked_jellyfish",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生鲟鱼
    public static final Supplier<Item> STURGEON = ITEMS.register("sturgeon",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟鲟鱼
    public static final Supplier<Item> COOKED_STURGEON = ITEMS.register("cooked_sturgeon",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生鳊鱼
    public static final Supplier<Item> BREAM = ITEMS.register("bream",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟鳊鱼
    public static final Supplier<Item> COOKED_BREAM = ITEMS.register("cooked_bream",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生明虾
    public static final Supplier<Item> SHRIMP = ITEMS.register("shrimp",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟明虾
    public static final Supplier<Item> COOKED_SHRIMP = ITEMS.register("cooked_shrimp",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
    // 生蟹
    public static final Supplier<Item> CRAB = ITEMS.register("crab",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    // 熟蟹
    public static final Supplier<Item> COOKED_CRAB = ITEMS.register("cooked_crab",
            ()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())));
   /* public static final Supplier<Item> TOMATO_BLOCK = ITEMS.register("tomato_block",
            ()->new BlockItem(ModBlocks.TOMATO_BLOCK.get(),new Item.Properties()));
    public static final Supplier<Item> TOMATO_STEM = ITEMS.register("tomato_stem",
            ()->new BlockItem(ModBlocks.TOMATO_STEM.get(),new Item.Properties()));*/

}
