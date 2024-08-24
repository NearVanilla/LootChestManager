package com.nearvanilla.lootChestManager.events;

import com.destroystokyo.paper.loottable.LootableInventory;
import com.nearvanilla.lootChestManager.LootChestManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.Inventory;

public class MinecartEvents implements Listener {

    TextComponent minecartMessage = Component.text(
            "You cannot destroy a lootable minecart!",
            NamedTextColor.RED
    );

    @EventHandler
    public void onMinecartDestroy(VehicleDestroyEvent event){
        Entity attacker = event.getAttacker();
        Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof StorageMinecart storageMinecart){
            if(storageMinecart instanceof LootableInventory lootableInventory){
                if(lootableInventory.hasLootTable()){
                    event.setCancelled(true);
                    if(attacker instanceof Player player){
                        player.sendMessage(minecartMessage);
                    }
                    return;
                }
            }
            if(storageMinecart.getPersistentDataContainer().has(LootChestManager.lootTableKey)){
                event.setCancelled(true);
                if(attacker instanceof Player player){
                    player.sendMessage(minecartMessage);
                }
            }
        }
    }
}
