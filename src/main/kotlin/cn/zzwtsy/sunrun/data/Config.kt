package cn.zzwtsy.sunrun.data

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 配置文件
 * @author zzwtsy
 * @date 2023/02/17
 */
object Config : AutoSavePluginConfig("config") {
    @ValueDescription("机器人 qq 号")
    var botId by value(0L)

    @ValueDescription("qq 群号")
    var groupId by value(0L)

    @ValueDescription(
        """
        定时任务时间
        """
    )
    var task: Map<Long, String> by value()
    var imei: Map<Long, String> by value()

    @ValueDescription("阳光体育版本号")
    val version by value("2.44")
}