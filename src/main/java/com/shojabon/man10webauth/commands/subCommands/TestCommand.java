package com.shojabon.man10webauth.commands.subCommands;

import com.shojabon.man10webauth.Man10WebAuth;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    Man10WebAuth plugin;

    public TestCommand(Man10WebAuth plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

//        BlockDataMeta meta = (BlockDataMeta) block;
//        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "shopId"), PersistentDataType.STRING, "test");
//        block.getState()
        return true;
    }
}
