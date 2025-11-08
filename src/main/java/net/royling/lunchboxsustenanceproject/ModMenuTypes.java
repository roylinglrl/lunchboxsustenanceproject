package net.royling.lunchboxsustenanceproject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.royling.lunchboxsustenanceproject.cookingPot.CookingPotMenu;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, LunchboxSustenanceProject.MODID);
    public static final Supplier<MenuType<CookingPotMenu>> COOKING_POT_MENU =
            MENUS.register("cooking_pot_menu",
                    () -> IMenuTypeExtension.create(CookingPotMenu::new));
}
