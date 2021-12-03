package me.nhall.whitelist;

import me.nhall.whitelist.command.discord.DiscordWhitelistCmd;
import me.nhall.whitelist.listener.DeathListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Whitelist extends JavaPlugin {

    private static Whitelist plugin;
    private JDA jda;
    private FileConfiguration config;

    public static Whitelist getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        if (!this.getDataFolder().exists()) {
            this.saveDefaultConfig();
        }
        config = this.getConfig();
        try {
            jda = JDABuilder.createLight(config.getString("token")).setActivity(Activity.playing("mc.nhall.me")).addEventListeners(new DiscordWhitelistCmd(this)).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if (jda == null) {
            this.getPluginLoader().disablePlugin(this);
        }
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        jda.upsertCommand("whitelist", "Whitelist Somebody on the Server").queue();

    }

    @Override
    public void onDisable() {
        jda.shutdown();
    }

    public JDA getJda() {
        return jda;
    }
}
