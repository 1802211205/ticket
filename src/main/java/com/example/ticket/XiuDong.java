package com.example.ticket;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 秀动
 */
public class XiuDong {
    private static final long REST_INTERVAL_MILLIS = 20 * 60 * 1000; // 半小时
    private static final long REST_DURATION_MILLIS = 60 * 1000 * 2; // 1分钟
    private static final long TASK_INTERVAL_MILLIS = 10 * 1000; // 5秒

    public static void main(String[] args) {
        long lastRestTime = 0; // 上次休息的时间
        select();
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
                    select();
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

    public static void select(){
        try {

            ProcessBuilder cdProcessBuilder = new ProcessBuilder("cmd", "/c", "cd", "C:\\Users\\M4800\\Downloads\\xiudong-go-main\\xiudong-go-main\\cli");
            Process cdProcess = cdProcessBuilder.start();
            cdProcess.waitFor();

            // 执行命令
            ProcessBuilder commandProcessBuilder = new ProcessBuilder("cmd", "/c", "main.exe", "tickets", "-a", "233191", "--config", "cli-sample.yaml");
            commandProcessBuilder.directory(new java.io.File("C:\\Users\\M4800\\Downloads\\xiudong-go-main\\xiudong-go-main\\cli"));
            Process commandProcess = commandProcessBuilder.start();

            // 读取命令执行结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("有")){
                    //通知我
                    WXPusher.pusher(line,line);
                    System.out.println("通知你啦!!!");
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
