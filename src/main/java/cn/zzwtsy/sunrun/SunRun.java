package cn.zzwtsy.sunrun;

import cn.zzwtsy.sunrun.data.Config;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;

import java.util.Map;

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
        Config.INSTANCE.setTask(Map.of(1111L, "222", 3333L, "444"));
        reloadPluginConfig(Config.INSTANCE);
        Config.INSTANCE.getTask().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("=============");
        Config.INSTANCE.getImei().forEach((k, v) -> System.out.println(k + ":" + v));
        getLogger().info("Plugin loaded!");
    }
}