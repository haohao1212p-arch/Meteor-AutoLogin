package com.example.addon;

import com.example.addon.hud.HudExample;
import com.example.addon.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Auto Login");
    public static final HudGroup HUD_GROUP = new HudGroup("Auto Login");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor AutoLogin Addon");

        // Modules
        Modules.get().add(new ModuleExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }
}
