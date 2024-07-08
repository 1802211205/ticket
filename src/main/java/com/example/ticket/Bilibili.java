package com.example.ticket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;

//b站
public class Bilibili {
    public static void main(String[] args) throws JSONException {
        select();
    }

    public static void select() throws JSONException {
        String urlString = "https://show.bilibili.com/api/ticket/project/getV2?id=85939&project_id=85939&requestSource=neul-next";
        String result = HttpGetUtils.doGet(urlString);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray screen_list = data.getAsJsonArray("screen_list");
        for (JsonElement screen : screen_list) {
            JsonObject screenObject = screen.getAsJsonObject();
            if (screenObject.get("id").getAsInt() == 181112) {
                JsonArray ticket_list = screenObject.getAsJsonArray("ticket_list");
                for (JsonElement ticket : ticket_list) {
                    JsonObject ticketObject = ticket.getAsJsonObject();
                    if (ticketObject.get("id").getAsInt() == 509138) {
                        JsonObject sale_flag = ticketObject.getAsJsonObject("sale_flag");
                        String number = String.valueOf(sale_flag.get("number"));
                        String display_name = String.valueOf(sale_flag.get("display_name"));
                        System.out.println(number + "---" + display_name);
                        if (!"4".equals(number) || !"已售罄".equals(display_name)) {
                            WXPusher.pusher("b站13号有票!", "b站13号有票!");
                        }
                        break;
                    }

                }

            }
        }
    }
}
