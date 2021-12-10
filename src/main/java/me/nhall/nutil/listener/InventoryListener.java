package me.nhall.nutil.listener;

import me.nhall.nutil.NUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        NUtil plugin = NUtil.getPlugin();
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.getType() == InventoryType.ENCHANTING && NUtil.getPlugin().getEnchantBan().contains(player.getUniqueId().toString())) {
            double number = Math.random();
            if(number < plugin.getConfig().getDouble("enchant-rate")) {
                NUtil.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(NUtil.getPlugin(), player::closeInventory, 10L);
            }
        }


    }
}
