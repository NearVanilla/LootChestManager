package com.nearvanilla.lootChestManager;

import com.nearvanilla.lootChestManager.events.ChestEvents;
import com.nearvanilla.lootChestManager.events.MinecartEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class LootChestManager extends JavaPlugin {

    public static LootChestManager plugin;
    public static Logger pluginLogger;
    public static NamespacedKey lootTableKey;
    public static TextComponent breakMessage = Component.text()
      .color(NamedTextColor.RED)
      .append(Component.text("You need to sneak ("))
      .append(
          Component.keybind()
            .keybind("key.sneak")
            .color(NamedTextColor.LIGHT_PURPLE)
            .decoration(TextDecoration.BOLD, true)
            .build()
          )
      .append(Component.text(") to break loot containers!"))
      .build();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        pluginLogger = getLogger();
        lootTableKey = new NamespacedKey(this, "loot_table");
        getServer().getPluginManager().registerEvents(new ChestEvents(), this);
        getServer().getPluginManager().registerEvents(new MinecartEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
