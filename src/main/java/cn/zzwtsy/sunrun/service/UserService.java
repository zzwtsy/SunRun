package cn.zzwtsy.sunrun.service;

import cn.zzwtsy.sunrun.SunRun;
import cn.zzwtsy.sunrun.api.Api;
import cn.zzwtsy.sunrun.bean.EndRunning;
import cn.zzwtsy.sunrun.bean.UserBean;
import cn.zzwtsy.sunrun.data.Config;
import cn.zzwtsy.sunrun.utils.Encryption;
import cn.zzwtsy.sunrun.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Random;

/**
 * 用户服务
 *
 * @author zzwtsy
 * @since 2023/02/17
 */
public class UserService {
    private final Random random = new Random();
    private final Api api = new Api();

    /**
     * 获取用户信息
     *
     * @param qqId qq id
     * @return {@link UserBean} | null
     */
    private UserBean getUserInfo(long qqId) {
        JsonNode jsonNode;
        String imei = Config.getImei().get(qqId);
        try {
            jsonNode = JsonUtil.fromJson(api.getUserInfo(imei));
        } catch (JsonProcessingException e) {
            SunRun.INSTANCE.getLogger().error("获取用户信息失败", e);
            return null;
        }
        String token;
        try {
            token = jsonNode.get("Data").get("Token").asText();
        } catch (Throwable e) {
            return null;
        }
        String timespan = String.valueOf(System.currentTimeMillis());
        String userId = jsonNode.get("Data").get("UserId").asText();
        String auth = "B" + Encryption.encryptionToMd5(Encryption.encryptionToMd5(imei)) + ":;" + token;
        String nonce = String.valueOf(random.nextInt(10000000 - 100000 + 1) + 100000);
        String sign = Encryption.encryptionToMd5(token + nonce + timespan + userId);
        return UserBean.builder()
                .nonce(nonce)
                .timespan(timespan)
                .userId(userId)
                .auth(auth)
                .sign(sign)
                .imei(imei)
                .token(token)
                .build();
    }

    public String run(long qq) {
        UserBean userInfo = getUserInfo(qq);
        if (userInfo == null) {
            return "imei 已过期";
        }
        String runningRes = api.getRunningRes(userInfo);
        JsonNode jsonNode;
        try {
            jsonNode = JsonUtil.fromJson(runningRes);
        } catch (JsonProcessingException e) {
            return "读取跑步信息 json 错误";
        }
        EndRunning endRunning = new EndRunning();
        endRunning.setRunId(jsonNode.get("Data").get("RunId").asText());
        String endRunningRes = api.getEndRunning(endRunning, userInfo);
        JsonNode endNode;
        try {
            endNode = JsonUtil.fromJson(endRunningRes);
        } catch (JsonProcessingException e) {
            return "读取结束 json 错误";
        }
        try {
            endNode.get("Success");
            return "跑步成功 Success";
        } catch (Exception e) {
            return endNode.get("Data").asText();
        }
    }
}
