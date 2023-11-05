package com.shojabon.man10webauth.commands.subCommands;

import com.shojabon.man10webauth.Man10WebAuth;
import com.shojabon.man10webauth.Man10WebAuthAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class APIKeyCommand implements CommandExecutor {
    Man10WebAuth plugin;

    public APIKeyCommand(Man10WebAuth plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        new Thread(() -> {
            JSONObject request = Man10WebAuthAPI.authenticateAccount(player, plugin.getConfig().getString("BETAKEY"));
            String status = request.getString("status");
            if(status.equals("success")){
                Man10WebAuthAPI.successMessage(player, "APIキー: " + request.getString("data"));
                return;
            }

            Man10WebAuthAPI.warnMessage(player, request.getString("message"));
        }).start();


//        BlockDataMeta meta = (BlockDataMeta) block;
//        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "shopId"), PersistentDataType.STRING, "test");
//        block.getState()
        return true;
    }
}
