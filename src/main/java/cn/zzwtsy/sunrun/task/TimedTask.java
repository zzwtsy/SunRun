package cn.zzwtsy.sunrun.task;

import cn.zzwtsy.sunrun.SunRun;
import cn.zzwtsy.sunrun.data.Config;
import cn.zzwtsy.sunrun.service.UserService;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.At;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务
 *
 * @author meng
 * @since 2023/02/17
 */
public class TimedTask implements Runnable {
    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (i == 6 || i == 7) {
            return;
        }
        Map<Long, String> runStatus = new HashMap<>();
        UserService userService = new UserService();
        Map<Long, String> imei = Config.getImei();
        for (Long aLong : imei.keySet()) {
            String status = userService.run(aLong);
            runStatus.put(aLong, status);
        }
        Bot bot = Bot.getInstance(Config.getBotId());
        Group group = bot.getGroup(Config.getGroupId());
        if (group == null) {
            SunRun.INSTANCE.getLogger().error("获取 Group 失败");
            return;
        }
        runStatus.forEach((k, v) -> group.sendMessage(new At(k).plus(v)));
    }
}
