package cn.zzwtsy.sunrun.commands;

import cn.zzwtsy.sunrun.SunRun;
import cn.zzwtsy.sunrun.data.Config;
import cn.zzwtsy.sunrun.tools.Tools;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author zzwtsy
 * @since 2023/03/02
 */
public class Group extends SimpleListenerHost {
    String message;
    GroupMessageEvent groupMessageEvent;

    @EventHandler
    private void onEvent(GroupMessageEvent event) {
        this.groupMessageEvent = event;
        message = groupMessageEvent.getMessage().contentToString();
        long userQqId = groupMessageEvent.getSender().getId();

        if (message.startsWith("IMEICode=")) {
            String[] strings = message.split("=");
            if (strings.length != 2) {
                groupMessageEvent.getGroup().sendMessage("命令格式错误");
                return;
            }
            String newImei = strings[1];
            if (newImei.length() != 32) {
                groupMessageEvent.getGroup().sendMessage("imei 格式错误");
                return;
            }
            Map<Long, String> imei = Config.getImei();
            String oldImei = imei.get(userQqId);
            if (newImei.equals(oldImei)) {
                groupMessageEvent.getGroup().sendMessage("imei 未过期，无需更新");
                return;
            }
            imei.put(userQqId, newImei);
            groupMessageEvent.getGroup().sendMessage(new At(userQqId).plus("添加成功"));
        }
        if (message.startsWith("#日期")) {
            String[] split = message.split(" ");
            if (split[1].startsWith("add")) {
                Config.getExcludeDate().clear();
                for (int i = 2; i < split.length; i++) {
                    try {
                        Config.getExcludeDate().add(Integer.valueOf(split[i]));
                    } catch (Throwable e) {
                        groupMessageEvent.getGroup().sendMessage(Tools.arraysToString(e.getStackTrace()));
                    }
                }
            }
            if (split[1].startsWith("show")) {
                groupMessageEvent.getGroup().sendMessage("以下日期不进行跑步:" + Config.getExcludeDate());
            }
        }
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        SunRun.INSTANCE.getLogger().error("发生错误:", exception);
    }
}
