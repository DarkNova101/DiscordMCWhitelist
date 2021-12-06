package me.nhall.whitelist.command.discord;

import me.nhall.whitelist.Whitelist;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DiscordPlaytimeCmd extends ListenerAdapter {

    private final Whitelist plugin;

    public DiscordPlaytimeCmd(Whitelist plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (!event.isFromGuild()) return;

        if (!event.getName().equals("playtime")) return;
        event.deferReply().queue();
        Map<OfflinePlayer, Integer> allPlayers = new HashMap<>();

        for (OfflinePlayer p : plugin.getServer().getOfflinePlayers()) {
            allPlayers.put(p, p.getStatistic(Statistic.PLAY_ONE_MINUTE));

        }
        allPlayers = sortMap(allPlayers);

        event.getHook().sendMessageEmbeds(createEmbed(allPlayers)).queue();
    }

    private MessageEmbed createEmbed(Map<OfflinePlayer, Integer> map) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Playtime")
                .setDescription("Here is everyone's playtime");

        for (Map.Entry<OfflinePlayer, Integer> entry : map.entrySet()) {
            if (eb.getFields().size() >= 24) {
                break;
            }
            double time = entry.getValue() / 20 / 60;
            eb.addField(entry.getKey().getName(), String.valueOf(time), true);
        }
        return eb.build();
    }

    private Map<OfflinePlayer, Integer> sortMap(Map<OfflinePlayer, Integer> map) {
        List<Map.Entry<OfflinePlayer, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing((Map.Entry<OfflinePlayer, Integer> o) -> o.getValue()));
        Map<OfflinePlayer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<OfflinePlayer, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


}
