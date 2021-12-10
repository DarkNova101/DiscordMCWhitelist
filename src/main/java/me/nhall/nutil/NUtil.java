package me.nhall.nutil;

import com.google.gson.Gson;
import me.nhall.nutil.command.discord.DiscordEvalCommand;
import me.nhall.nutil.command.discord.DiscordPlaytimeCmd;
import me.nhall.nutil.command.discord.DiscordWhitelistCmd;
import me.nhall.nutil.command.minecraft.ReloadCmd;
import me.nhall.nutil.listener.DeathListener;
import me.nhall.nutil.listener.InventoryListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public final class NUtil extends JavaPlugin {

    private static NUtil plugin;
    private JDA jda;
    private Map<?, ?> deathMap;
    private Map<?, ?> entityMap;
    private List<String> enchantBan;

    public static NUtil getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        deathMap = loadJsonFile("deaths.json");
        entityMap = loadJsonFile("entities.json");
        this.enchantBan = config.getStringList("enchant-ban");

        try {
            jda = JDABuilder.createLight(config.getString("token"))
                    .setActivity(Activity.playing("mc.nhall.me"))
                    .addEventListeners(new DiscordWhitelistCmd(this), new DiscordPlaytimeCmd(this), new DiscordEvalCommand())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if (jda == null) {
            this.getPluginLoader().disablePlugin(this);
        }
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        jda.upsertCommand("whitelist", "Whitelist Somebody on the Server")
                .addOption(OptionType.STRING, "username", "The username of the player you want to whitelist.", true).queue();
        jda.upsertCommand("playtime", "See Everyone's Playtime").queue();

        this.getCommand("nreload").setExecutor(new ReloadCmd());
    }

    @Override
    public void onDisable() {
    }

    public JDA getJda() {
        return jda;
    }

    private Map<?, ?> loadJsonFile(String fileName) {
        if (!new File(getDataFolder(), fileName).exists()) {
            saveResource(fileName, false);
        }

        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(getDataFolder() + "/" + fileName));
            return gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<?, ?> getDeathMap() {
        return deathMap;
    }

    public Map<?, ?> getEntityMap() {
        return entityMap;
    }

    public List<String> getEnchantBan() {
        return enchantBan;
    }
}
