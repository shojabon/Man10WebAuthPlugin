package com.shojabon.man10webauth.commands.subCommands;

import com.shojabon.man10webauth.Man10WebAuth;
import com.shojabon.man10webauth.Man10WebAuthAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class RegisterAccountCommand implements CommandExecutor {
    Man10WebAuth plugin;

    public RegisterAccountCommand(Man10WebAuth plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        new Thread(() -> {
            JSONObject request = Man10WebAuthAPI.registerAccount(player, args[1]);
            String status = request.getString("status");
            if(status.equals("success")){
                Man10WebAuthAPI.successMessage(player, "アカウントを作成しました");
                return;
            }

            if(status.equals("account_exists") || status.equals("kong_consumer_exists")){
                Man10WebAuthAPI.warnMessage(player, "すでに、このアカウントで登録されているか、同じユーザー名のアカウントが存在します");
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
