package me.nhall.whitelist;

import me.nhall.whitelist.listener.DeathListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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

        getServer().getPluginManager().registerEvents(new DeathListener(), this);

    }

    @Override
    public void onDisable() {
        jda.shutdown();
    }

    public static Whitelist getPlugin() {
        return plugin;
    }

    public JDA getJda() {
        return jda;
    }
}
