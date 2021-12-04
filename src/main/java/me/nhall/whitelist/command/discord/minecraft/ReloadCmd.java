package me.nhall.whitelist.command.discord.minecraft;

import me.nhall.whitelist.Whitelist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Whitelist plugin = Whitelist.getPlugin();

        plugin.reloadConfig();
        sender.sendMessage("Reloaded Config");
        return true;
    }
}
