package com.shojabon.man10webauth.commands.subCommands;

import com.shojabon.man10webauth.Man10WebAuth;
import com.shojabon.mcutils.Utils.SInventory.SInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {
    Man10WebAuth plugin;

    public ReloadConfigCommand(Man10WebAuth plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        plugin.reloadConfig();
        Man10WebAuth.config = plugin.getConfig();

        Man10WebAuth.prefix = Man10WebAuth.config.getString("prefix");

        sender.sendMessage(Man10WebAuth.prefix + "§a§lプラグインがリロードされました");
        return true;
    }
}
