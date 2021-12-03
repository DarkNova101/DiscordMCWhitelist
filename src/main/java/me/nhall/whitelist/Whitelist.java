package me.nhall.whitelist;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public final class Whitelist extends JavaPlugin {

    private JDA jda;
    private FileConfiguration config;
    private static Whitelist plugin;

    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.saveDefaultConfig();
        }
        config = this.getConfig();
        try {
            jda = JDABuilder.createDefault(config.getString("token")).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if (jda == null) {
            this.getPluginLoader().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {

    }

    public static Whitelist getPlugin() {
        return plugin;
    }

}
