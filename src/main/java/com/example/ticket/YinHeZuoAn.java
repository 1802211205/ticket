package com.example.ticket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 银河左岸
 */
public class YinHeZuoAn {
    private static final long REST_INTERVAL_MILLIS = 20 * 60 * 1000; // 半小时
    private static final long REST_DURATION_MILLIS = 60 * 1000*2; // 1分钟
    private static final long TASK_INTERVAL_MILLIS = 5 * 1000; // 5秒



    public static void main(String[] args) {
        long lastRestTime = 0; // 上次休息的时间
        while (true) {
            try {
                long currentTimeMillis = System.currentTimeMillis();

                // 检查是否需要休息
                if (currentTimeMillis - lastRestTime >= REST_INTERVAL_MILLIS) {
                    // 休息一分钟
                    System.out.println("开始休息...");
                    Thread.sleep(REST_DURATION_MILLIS);
                    lastRestTime = currentTimeMillis; // 更新上次休息的时间
                    System.out.println("休息结束，继续执行任务...");
                } else {
                    // 执行选票任务
                    selectTicket();
                }

                // 无论是否休息，都等待5秒再执行下一次任务
                Thread.sleep(TASK_INTERVAL_MILLIS);

            } catch (InterruptedException e) {
                e.printStackTrace();
                // 如果线程被中断，可能需要退出循环或采取其他行动
                break; // 这里假设我们退出循环
            }
        }
    }

    public static void selectTicket() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("time: " + formattedDateTime);

        String url = "http://sapi.xingyeshow.com/api/v1/micro_travel/products/114?seller_number=guest";
        String response = HttpGetUtils.doGetNoParametersSetHeaderClientId(url, null, null);

        JSONObject json = JSON.parseObject(response);
        JSONObject body = (JSONObject) json.get("body");
        JSONObject product = (JSONObject) body.get("product");
        JSONArray ticketArray = (JSONArray) product.get("variants");


        for (int i = 0; i < ticketArray.size(); i++) {
            JSONObject t = ticketArray.getJSONObject(i);
            int onHandCount = (int) t.get("on_hand_count");
            String name = (String) t.get("name");
            System.out.println(name + "\t-----\t" + onHandCount);
            if (onHandCount > 0) {
                //通知我
                String urlString = "https://sctapi.ftqq.com/SCT248415T3w8Y0M298X8jje10aWwWUocJ.send?title=" + name + " : " + onHandCount;
                HttpGetUtils.doGetNoParameters(urlString, null, null);
                System.out.println("通知你啦!!!");
                break;
            }
        }
    }
}
