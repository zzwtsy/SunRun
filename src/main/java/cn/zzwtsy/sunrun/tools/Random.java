package cn.zzwtsy.sunrun.tools;

import cn.zzwtsy.sunrun.data.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 随机
 *
 * @author zzwtsy
 * @since 2023/02/17
 */
public class Random {
    private static List<String> alphabet = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));

    @NotNull
    public static String encrypt(@NotNull String value) {
        List<String> table = Arrays.asList(randomAlphabet().split(""));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char curChar = value.charAt(i);
            result.append(table.get(curChar - '0'));
        }
        return result.toString();
    }

    @NotNull
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
}
