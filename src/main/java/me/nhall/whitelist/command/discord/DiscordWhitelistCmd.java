package me.nhall.whitelist.command.discord;

import me.nhall.whitelist.Whitelist;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordWhitelistCmd extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equals("whitelist")) {
            event.deferReply().queue();
            Role role = event.getGuild().getRoleById(Whitelist.getPlugin().getConfig().getString("role"));
            if (!event.getMember().getRoles().contains(role)) {
                event.getHook().sendMessage("You do not have the required permissions.").queue();
                return;
            }

            String username = event.getOption("username").getAsString();
            Whitelist.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + username);
            event.getHook().sendMessage("Added " + username + " to the server whitelist.").queue();
        }
    }
}
