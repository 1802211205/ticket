package com.example.ticket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 云村有票
 */
public class YunCun {
    private static final long REST_INTERVAL_MILLIS = 20 * 60 * 1000; // 半小时
    private static final long REST_DURATION_MILLIS = 60 * 1000 * 2; // 1分钟
    private static final long TASK_INTERVAL_MILLIS = 5 * 1000; // 5秒

    public static void main(String[] args) {
        long lastRestTime = 0; // 上次休息的时间
        while (true) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                Instant instant = Instant.ofEpochMilli(currentTimeMillis);  // 转换为 Instant 对象
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());  // 转换为本地日期时间

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");  // 定义格式化模式
                String formattedDateTime = dateTime.format(formatter);
                System.out.print(formattedDateTime + "\t");

                // 检查是否需要休息
                if (currentTimeMillis - lastRestTime >= REST_INTERVAL_MILLIS) {
                    // 休息一分钟
                    System.out.println("开始休息...");
                    Thread.sleep(REST_DURATION_MILLIS);
                    lastRestTime = currentTimeMillis; // 更新上次休息的时间
                    System.out.println("休息结束，继续执行任务...");
                } else {
                    // 执行选票任务
                    select();
                }

                // 无论是否休息，都等待5秒再执行下一次任务
                Thread.sleep(TASK_INTERVAL_MILLIS);

            } catch (InterruptedException e) {
                e.printStackTrace();
                // 如果线程被中断，可能需要退出循环或采取其他行动
                break; // 这里假设我们退出循环
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void select() throws JSONException {
        String urlString = "https://interface.music.163.com/api/batch?%2Fapi%2Fconcert%2Fdetail%2Fv3=%7B%22concertId%22%3A%2213361688%22%7D";
        String result = HttpGetUtils.doGet(urlString);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
        JsonObject api = jsonObject.getAsJsonObject("/api/concert/detail/v3");
        JsonObject data = api.getAsJsonObject("data");
        String title = data.get("title").toString();
        System.out.println(title);
        JsonObject units = (JsonObject) data.getAsJsonArray("units").get(0);
        JsonArray tickets = units.getAsJsonArray("tickets");
        for (JsonElement ticket : tickets) {
            JsonObject ticketObject = ticket.getAsJsonObject();
            if (ticketObject.get("stock").getAsInt() != 0) {
                String noticeString = ticketObject.get("note").toString() + ticketObject.get("price").toString() + " : " + ticketObject.get("stock").toString();
                WXPusher.pusher(noticeString, noticeString);
                System.out.println("通知你啦!!!");
                break;
            }
        }
    }
}
