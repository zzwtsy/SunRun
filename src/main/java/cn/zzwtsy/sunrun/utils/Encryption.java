package cn.zzwtsy.sunrun.utils;

import cn.zzwtsy.sunrun.SunRun;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密
 *
 * @author meng
 * @since 2023/02/17
 */
public class Encryption {
    /**
     * md5加密
     *
     * @param value 密码
     * @return {@link String}
     */
    public static String encryptionToMd5(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest((value).getBytes(StandardCharsets.UTF_8));
            // 将处理后的字节转成 16 进制，得到最终 32 个字符
            StringBuilder sb = new StringBuilder();
            for (byte b : md5) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            SunRun.INSTANCE.getLogger().error(e);
            return null;
        }
    }
}
