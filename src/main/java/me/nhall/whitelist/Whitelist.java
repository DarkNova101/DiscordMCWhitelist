package me.nhall.whitelist;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Whitelist extends JavaPlugin {

    private JDA jda;

    @Override
    public void onEnable() {
        try {
            jda = JDABuilder.createDefault("").build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if(jda == null) {
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }
}
