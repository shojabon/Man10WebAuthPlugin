package com.shojabon.man10webauth.commands.subCommands;

import com.shojabon.man10webauth.Man10WebAuth;
import com.shojabon.man10webauth.Man10WebAuthAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
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
                String apiKey = request.getString("data");
                player.sendMessage(Component.text(Man10WebAuth.prefix + "§a§lAPIキー: " + apiKey)
                        .clickEvent(ClickEvent.copyToClipboard(apiKey))
                        .hoverEvent(HoverEvent.showText(Component.text("クリックでコピー"))));

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
