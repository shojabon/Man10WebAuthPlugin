package com.shojabon.man10webauth;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class Man10WebAuthAPI {

    Man10WebAuth plugin;

    public Man10WebAuthAPI(Man10WebAuth plugin){
        this.plugin = plugin;

        // Install a custom TrustManager to ignore certificate validation
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // global methods
    public static JSONObject httpRequest(String endpoint, String method, JSONObject jsonInput) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // optional default is GET
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Authenticate", Man10WebAuth.config.getString("api.key"));
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

            System.out.println(response.toString());
            //print result
            JSONObject responseObject = new JSONObject();
            if(responseCode == 200){
                responseObject.put("status", "success");
                responseObject.put("message", "成功しました");
            }else{
                throw new Exception("failed");
            }
            return responseObject;
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

    public static JSONObject registerAccount(Player p, String password){
        JSONObject payload = new JSONObject();
        payload.put("username", p.getName());
        payload.put("userId", p.getUniqueId().toString());
        payload.put("password", password);
        payload.put("metadata", JSONObject.NULL);
        return httpRequest(Man10WebAuth.config.getString("api.endpoint") + "/register", "POST", payload);
    }

}