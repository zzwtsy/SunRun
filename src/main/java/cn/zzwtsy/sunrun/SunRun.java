package cn.zzwtsy.sunrun;

import cn.zzwtsy.sunrun.data.Config;
import cn.zzwtsy.sunrun.service.TaskService;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;

/**
 * @author zzwtsy
 * @since 2023/02/22
 */
public final class SunRun extends JavaPlugin {
    public static final SunRun INSTANCE = new SunRun();

    private SunRun() {
        super(new JvmPluginDescriptionBuilder("cn.zzwtsy.sun-run", "0.1.0")
                .name("SunRun")
                .author("zzwtsy")
                .build());
    }

    @Override
    public void onEnable() {
        reloadPluginConfig(Config.INSTANCE);
        String timedTask = Config.getTask();
        //启动定时任务
        String taskStatus = new TaskService().startTimedTask(timedTask);
        getLogger().info(taskStatus);
        getLogger().info("Plugin loaded!");
    }
}