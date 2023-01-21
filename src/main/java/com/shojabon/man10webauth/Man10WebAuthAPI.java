package com.shojabon.man10webauth;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Man10WebAuthAPI {

    Man10WebAuth plugin;

    public Man10WebAuthAPI(Man10WebAuth plugin){
        this.plugin = plugin;
    }

    // global methods
    public static JSONObject httpRequest(String endpoint, String method, JSONObject jsonInput) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // optional default is GET
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json; charset=UTF-8");
            con.setRequestProperty("x-api-key", Man10WebAuth.config.getString("api.key"));
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = con.getResponseCode();
            BufferedReader in = null;
            if(responseCode == 200){
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            }else{
                in = new BufferedReader(
                        new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject failResponse = new JSONObject();
            failResponse.put("status", "endpoint_error");
            failResponse.put("message", "エンドポイントに接続することがで来ませんでした");
            return failResponse;
        }
    }

    public static void warnMessage(Player p, String message){
        p.sendMessage(Man10WebAuth.prefix + "§c§l" + message);
    }

    public static void successMessage(Player p, String message){
        p.sendMessage(Man10WebAuth.prefix + "§a§l" + message);
    }

    public static JSONObject registerAccount(Player p, String username, String password){
        JSONObject payload = new JSONObject();
        payload.put("minecraftUUID", p.getUniqueId().toString());
        payload.put("minecraftUsername", username);
        payload.put("password", password);
        return httpRequest(Man10WebAuth.config.getString("api.endpoint") + "/register", "POST", payload);
    }

    public static JSONObject updatePassword(Player p, String password){
        JSONObject payload = new JSONObject();
        payload.put("minecraftUUID", p.getUniqueId().toString());

        JSONObject data = new JSONObject();
        data.put("password", password);

        payload.put("data", data);

        return httpRequest(Man10WebAuth.config.getString("api.endpoint") + "/update", "POST", payload);
    }

    public static JSONObject updateUsername(Player p, String username){
        JSONObject payload = new JSONObject();
        payload.put("minecraftUUID", p.getUniqueId().toString());

        JSONObject data = new JSONObject();
        data.put("username", username);

        payload.put("data", data);

        return httpRequest(Man10WebAuth.config.getString("api.endpoint") + "/update", "POST", payload);
    }

}