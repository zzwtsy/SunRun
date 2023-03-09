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
    @JvmStatic
    val botId: Long by value(0L)

    @ValueDescription("qq 群号")
    @JvmStatic
    val groupId: Long by value(0L)

    @ValueDescription(
        """
        定时任务时间
        """
    )
    @JvmStatic
    val task: String by value()

    @ValueDescription(
        """
        如何获取 IMEI:https://github.com/Waldenth/FuckHamSports#for-users
        |示例:
            imei:
              qq 号: imei
              1111: 222
              3333: 444
        或
            imei: { 1111: 222,3333: 444 }
        """
    )
    @JvmStatic
    var imei: Map<Long, String> by value()

    @ValueDescription("经度")
    @JvmStatic
    val longitude: String by value()

    @ValueDescription("纬度")
    @JvmStatic
    val latitude: String by value()

    @ValueDescription("要求路程")
    @JvmStatic
    val distance: Int by value()

    @ValueDescription("最小速度")
    @JvmStatic
    val minSpeed: Int by value()

    @ValueDescription("阳光体育版本号")
    @JvmStatic
    val version: String by value("2.44")
}