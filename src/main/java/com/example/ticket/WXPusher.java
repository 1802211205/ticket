package com.example.ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WXPusher {
    public static void pusher(String content,String summary) throws JSONException {
        String pusherUrl="https://wxpusher.zjiecode.com/api/send/message";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("appToken", "AT_4ygeMpZLdYFJ3Zi5WbUAP28sz275gB8i");
        jsonBody.put("content", content);
        jsonBody.put("summary", summary);
        jsonBody.put("contentType", 1);
        JSONArray uids = new JSONArray();
        uids.put("UID_khxJ9pETlYZ6zvz3r6xm5QxbwwBk");
        jsonBody.put("uids", uids);
        jsonBody.put("verifyPay", false);
        jsonBody.put("verifyPayType", 0);
        HttpGetUtils.doPost(pusherUrl,jsonBody.toString());

    }
}
