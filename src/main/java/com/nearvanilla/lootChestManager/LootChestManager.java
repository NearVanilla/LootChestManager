package com.nearvanilla.lootChestManager;

import com.nearvanilla.lootChestManager.events.ChestEvents;
import com.nearvanilla.lootChestManager.events.MinecartEvents;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class LootChestManager extends JavaPlugin {

    public static LootChestManager plugin;
    public static Logger pluginLogger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        pluginLogger = getLogger();
        getServer().getPluginManager().registerEvents(new ChestEvents(), this);
        getServer().getPluginManager().registerEvents(new MinecartEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
