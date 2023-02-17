package cn.zzwtsy.sunrun.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 常量
 *
 * @author meng
 * @since 2023/02/17
 */
public class Const {
    public static final Map<Long, ScheduledFuture<?>> TASKS_MAP = new HashMap<>();
}
