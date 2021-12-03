package me.nhall.whitelist.listener;

import me.nhall.whitelist.Whitelist;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        TextChannel channel = Whitelist.getPlugin().getJda().getTextChannelById(Whitelist.getPlugin().getConfig().getString("death-channel"));


        channel.sendMessage(event.getDeathMessage()).queue();
    }
}
