package cn.zzwtsy.sunrun.tools;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 随机
 *
 * @author zzwtsy
 * @since 2023/02/17
 */
public class Tools {
    private static List<String> alphabet = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));

    /**
     * 加密
     *
     * @param value 值
     * @return {@link String}
     */
    @NotNull
    public static String encrypt(String value, String alphabet) {
        List<String> table = Arrays.asList(alphabet.split(""));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char curChar = value.charAt(i);
            result.append(table.get(curChar - '0'));
        }
        return result.toString();
    }

    public static String randomAlphabet() {
        //使用默认随机源对列表进行置换
        Collections.shuffle(alphabet);
        alphabet = alphabet.subList(0, 10);
        StringBuilder table = new StringBuilder();
        int alphabetLength = 10;
        for (int i = 0; i < alphabetLength; i++) {
            table.append(alphabet.get(i));
        }
        return table.toString();
    }

    /**
     * 补全日期
     *
     * @param date 日期 (yyyy-MM-dd HH:mm:ss)
     * @return {@link String}
     */
    public static String complementaryDate(String date) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + date + ":00";
    }


    /**
     * 计算时间
     *
     * @param time 时间
     * @return long
     * @throws ParseException 解析异常
     */
    public static long calculateTime(String time) throws ParseException {
        String s = complementaryDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        long timedTaskTime = sdf.parse(s).getTime();
        //获取当前时间的时间戳
        long nowTime = System.currentTimeMillis();
        //设定时间减去当前时间并转换为秒
        long delayTime = (timedTaskTime - nowTime) / 1000L;
        //设定时间位于当前时间之后，直接返回延迟时间即可
        if (delayTime > 0) {
            return delayTime;
        } else if (delayTime < 0) {
            //设定时间位于当前时间之前，需要延迟到第二天执行
            //24小时 = 86400秒
            return 86400 + delayTime;
        } else {
            return 0;
        }
    }

    public static String arraysToString(Object[] objects) {
        if (objects == null) {
            return "null";
        }

        int iMax = objects.length - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(objects[i]);
            if (i == iMax) {
                return b.toString();
            }
            b.append("\n");
        }
    }
}
