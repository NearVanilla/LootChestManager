package com.nearvanilla.lootChestManager.events;

import com.destroystokyo.paper.loottable.LootableInventory;
import com.nearvanilla.lootChestManager.LootChestManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.loot.LootTable;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;

public class ChestEvents implements Listener {

    TextComponent blockBreakComponent = Component.text(
            "You cannot break loot chests!",
            NamedTextColor.RED
    );

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockState blockState = block.getState();
        if(blockState instanceof Container container){
            if(container.getPersistentDataContainer().has(LootChestManager.lootTableKey)){
                if(!player.isSneaking()){
                    event.setCancelled(true);
                    player.sendMessage(blockBreakComponent);
                    return;
                }
            }
            if(container instanceof LootableInventory lootableInventory){
                if(!player.isSneaking()){
                    if(lootableInventory.hasLootTable()){
                        event.setCancelled(true);
                        player.sendMessage(blockBreakComponent);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        List<Block> blocksToRemove = event.blockList().stream().filter(block -> {
            BlockState blockState = block.getState();
            if(blockState instanceof Container container){
                if(container.getPersistentDataContainer().has(LootChestManager.lootTableKey)){
                    return true;
                }
                if(container instanceof LootableInventory lootableInventory){
                    return lootableInventory.hasLootTable();
                }
            }
            return false;
        }).toList();
        event.blockList().removeAll(blocksToRemove);
    }

    @EventHandler
    public void onLootGenerate(LootGenerateEvent event){
        LootChestManager.pluginLogger.info("Loot generated.");
        LootTable lootTable = event.getLootTable();
        String originalLootTable = lootTable.getKey().asString();
        InventoryHolder invHolder = event.getInventoryHolder();
        if(invHolder instanceof Container container){
            LootChestManager.pluginLogger.info("Loot recently generated, setting PDC.");
            container.getPersistentDataContainer().set(LootChestManager.lootTableKey, PersistentDataType.STRING, originalLootTable);
            container.update();
        }

        if(invHolder instanceof StorageMinecart storageMinecart){
            LootChestManager.pluginLogger.info("Loot recently generated, setting PDC.");
            storageMinecart.getPersistentDataContainer().set(LootChestManager.lootTableKey, PersistentDataType.STRING, originalLootTable);
        }
    }
}
