package cn.zzwtsy.sunrun;

import cn.zzwtsy.sunrun.data.Config;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;

import java.util.Map;

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
        Config.setTask(Map.of(1111L, "222", 3333L, "444"));
        reloadPluginConfig(Config.INSTANCE);
        Config.getTask().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println("=============");
        Config.getImei().forEach((k, v) -> System.out.println(k + ":" + v));
        getLogger().info("Plugin loaded!");
    }
}