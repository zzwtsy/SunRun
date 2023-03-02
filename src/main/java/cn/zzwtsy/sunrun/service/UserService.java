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
        JsonNode data = jsonNode.get("Data");
        String token;
        try {
            token = data.get("Token").asText();
        } catch (Throwable e) {
            return null;
        }
        String timespan = String.valueOf(System.currentTimeMillis());
        String userId = data.get("UserId").asText();
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
            return "false";
        }
        String runningRes = api.getRunningRes(userInfo);
        JsonNode jsonNode;
        try {
            jsonNode = JsonUtil.fromJson(runningRes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        EndRunning endRunning = new EndRunning();
        endRunning.setRunId(jsonNode.get("Data").get("RunId").asText());
        endRunning.setLengths(jsonNode.get("Data").get("SchoolRun").get("Lengths").asInt());
        String endRunningRes = api.getEndRunning(endRunning, userInfo);
        JsonNode endNode;
        try {
            endNode = JsonUtil.fromJson(endRunningRes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            endNode.get("Success");
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
