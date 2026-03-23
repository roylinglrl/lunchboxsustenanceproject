package net.royling.lunchboxsustenanceproject.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

public class LunchboxKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        KubeJSPlugin.super.registerEvents(registry);
        registry.register(LunchboxEvents.GROUP);
    }
}
