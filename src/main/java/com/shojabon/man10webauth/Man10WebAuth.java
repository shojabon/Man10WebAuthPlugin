package com.shojabon.man10webauth;

import com.shojabon.man10webauth.commands.Man10WebAuthCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Man10WebAuth extends JavaPlugin {

    public static FileConfiguration config;
    public static String prefix;
    public static Man10WebAuthAPI api;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config = getConfig();
        prefix = getConfig().getString("prefix");
        api = new Man10WebAuthAPI(this);

        Man10WebAuthCommand command = new Man10WebAuthCommand(this);
        getCommand("mwa").setExecutor(command);
        getCommand("mwa").setTabCompleter(command);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
