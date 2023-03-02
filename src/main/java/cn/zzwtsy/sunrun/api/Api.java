package cn.zzwtsy.sunrun.api;

import cn.zzwtsy.sunrun.bean.EndRunning;
import cn.zzwtsy.sunrun.bean.UserBean;
import cn.zzwtsy.sunrun.data.Config;
import cn.zzwtsy.sunrun.tools.Tools;
import cn.zzwtsy.sunrun.utils.HttpUtil;
import okhttp3.Headers;

import java.util.Random;

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
        return HttpUtil.sendGet(
                url,
                new Headers.Builder()
                        .add("version", Config.getVersion())
                        .build()
        );
    }

    /**
     * 获取跑步信息
     *
     * @param userBean 用户信息
     * @return 跑步信息
     */
    public String getRunningRes(UserBean userBean) {
        String url = HOST + "/" + userBean.getToken() + "/QM_Runs/SRS?S1=" + Config.getLatitude() + "&S2=" + Config.getLongitude() + "&S3=2000";
        return HttpUtil.sendGet(
                url,
                new Headers.Builder()
                        .add("nonce", userBean.getNonce())
                        .add("timespan", userBean.getTimespan())
                        .add("sign", userBean.getSign())
                        .add("version", Config.getVersion())
                        .add("Accept", "")
                        .add("User-Agent", "")
                        .add("Accept-Encoding", "")
                        .add("Connection", "Keep-Alive")
                        .build()
        );
    }

    /**
     * 获取跑步结束信息
     *
     * @param endRunning {@link EndRunning}
     * @param userBean   {@link UserBean}
     * @return {@link String}
     */
    public String getEndRunning(EndRunning endRunning, UserBean userBean) {
        Random random = new Random();
        int tempStep = random.nextInt(10) + Config.getDistance();
        double tempSpeed = random.nextDouble(1) + Config.getMinSpeed();
        int tempTime = (int) (tempStep / tempSpeed);
        String runTime = String.valueOf(tempTime);
        String runStep = String.valueOf(tempStep);
        String runDist = String.valueOf(random.nextInt(4) + 1200);
        String url = HOST + "/" + userBean.getToken()
                + "/QM_Runs/ES?S1="
                + endRunning.getRunId()
                + "&S4=" + Tools.encrypt(runTime)
                + "&S5=" + Tools.encrypt(runDist)
                + "&S6=&S7=1&S8=" + Tools.randomAlphabet()
                + "&S9=" + Tools.encrypt(runStep);
        return HttpUtil.sendGet(url);
    }
}
