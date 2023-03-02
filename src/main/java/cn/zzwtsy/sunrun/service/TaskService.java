package cn.zzwtsy.sunrun.service;

import cn.zzwtsy.sunrun.task.TimedTask;

import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static cn.zzwtsy.sunrun.tools.Tools.calculateTime;

/**
 * 任务服务
 *
 * @author zzwtsy
 * @since 2023/03/02
 */
public class TaskService {
    /**
     * 启动定时任务
     *
     * @return {@link String}
     */
    private String execute(long delayTime) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new TimedTask(), delayTime, 86400, TimeUnit.SECONDS);
        int anHour = 3600;
        if (delayTime > anHour || delayTime == anHour) {
            return "已启动定时任务，延时" + (delayTime / 3600) + "小时后开始发送消息";
        }
        int aMinute = 60;
        if (delayTime > aMinute || delayTime == aMinute) {
            return "已启动定时任务，延时" + (delayTime / 60) + "分钟后开始发送消息";
        }
        return "已启动定时任务，延时" + delayTime + "秒后开始发送消息";
    }

    /**
     * 启动定时任务
     *
     * @param time 时间
     * @return {@link String}
     */
    public String startTimedTask(String time) {
        try {
            return execute(calculateTime(time));
        } catch (ParseException e) {
            return e.getMessage();
        }
    }
}
