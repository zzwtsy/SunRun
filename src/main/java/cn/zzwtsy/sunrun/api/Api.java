package cn.zzwtsy.sunrun.api;

import cn.zzwtsy.sunrun.data.Config;
import cn.zzwtsy.sunrun.utils.HttpUtil;
import okhttp3.Headers;

/**
 * 阳光体育 api
 *
 * @author zzwtsy
 * @since 2023/02/22
 */
public class Api {
    private final String HOST = "http://client3.aipao.me/api";

    /**
     * 获取用户信息
     *
     * @param imei imei
     * @return {@link String}
     */
    public String getUserInfo(String imei) {
        String url = HOST + "/%7Btoken%7D/QM_Users/Login_AndroidSchool?IMEICode=" + imei;
        return HttpUtil.sendGet(url,
                new Headers.Builder()
                        .add("version", Config.getVersion())
                        .build()
        );
    }
}
