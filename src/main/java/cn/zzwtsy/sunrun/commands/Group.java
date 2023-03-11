package cn.zzwtsy.sunrun.commands;

import cn.zzwtsy.sunrun.SunRun;
import cn.zzwtsy.sunrun.data.Config;
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

        if (message.startsWith("#imei")) {
            String[] strings = message.split(" ");
            if (strings.length != 2) {
                groupMessageEvent.getGroup().sendMessage("命令格式错误");
                return;
            }
            if (strings[1].length() != 32) {
                groupMessageEvent.getGroup().sendMessage("imei 格式错误");
                return;
            }
            Map<Long, String> imei = Config.getImei();
            imei.put(userQqId, strings[1]);
            groupMessageEvent.getGroup().sendMessage(new At(userQqId).plus("添加成功"));
        }
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        SunRun.INSTANCE.getLogger().error("发生错误:", exception);
    }
}