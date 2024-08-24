package com.nearvanilla.lootChestManager.events;

import com.destroystokyo.paper.loottable.LootableEntityInventory;
import com.destroystokyo.paper.loottable.LootableInventory;
import com.nearvanilla.lootChestManager.LootChestManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

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
            if(container instanceof LootableInventory){
                event.setCancelled(true);
                player.sendMessage(blockBreakComponent);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        List<Block> affectedBlocks = event.blockList();
        for (Block block : affectedBlocks) {
            BlockState blockState = block.getState();
            if(blockState instanceof Container container){
                if(container instanceof LootableInventory){
                    event.setCancelled(true);
                }
            }
        }
    }
}
