package me.nhall.whitelist.listener;

import me.nhall.whitelist.Whitelist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.getType() == InventoryType.ENCHANTING && Whitelist.getPlugin().getEnchantBan().contains(player.getUniqueId().toString())) {
            Whitelist.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Whitelist.getPlugin(), () -> {
                player.closeInventory();
            }, 10L);
        }


    }
}
