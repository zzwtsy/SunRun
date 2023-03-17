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
    private String imeiStatus = null;

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
            String userInfo = api.getUserInfo(imei);
            jsonNode = JsonUtil.fromJson(userInfo);
            SunRun.INSTANCE.getLogger().debug("userInfo >>" + userInfo);
        } catch (JsonProcessingException e) {
            SunRun.INSTANCE.getLogger().error("获取用户信息失败", e);
            imeiStatus = "获取用户信息失败";
            return null;
        }
        SunRun.INSTANCE.getLogger().debug(qqId + ":" + jsonNode.asText());
        JsonNode data = jsonNode.get("Data");
        if (data == null) {
            imeiStatus = jsonNode.get("ErrMsg").asText();
            return null;
        }
        String token = data.get("Token").asText();
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
            return imeiStatus;
        }
        String runningRes = api.getRunningRes(userInfo);
        JsonNode jsonNode;
        try {
            jsonNode = JsonUtil.fromJson(runningRes);
        } catch (JsonProcessingException e) {
            SunRun.INSTANCE.getLogger().error("runningRes >>" + runningRes);
            return "读取跑步信息 json 错误";
        }
        EndRunning endRunning = new EndRunning();
        endRunning.setRunId(jsonNode.get("Data").get("RunId").asText());
        String endRunningRes;
        try {
            var runSpeed = random.nextFloat(1) + Config.getMinSpeed();
            var runDist = random.nextInt(6) + Config.getDistance();
            var runTime = String.valueOf((int) (runDist / runSpeed));
            var runStep = String.valueOf(random.nextInt(300) + 1300);
            endRunningRes = api.getEndRunning(endRunning, userInfo, runTime, runStep, String.valueOf(runDist));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JsonNode endNode;
        try {
            SunRun.INSTANCE.getLogger().debug("endRunningRes >>" + endRunningRes);
            endNode = JsonUtil.fromJson(endRunningRes);
        } catch (JsonProcessingException e) {
            return "读取结束 json 错误";
        }
        try {
            SunRun.INSTANCE.getLogger().debug("endNode >>" + endNode);
            var success = endNode.get("Success").asText();
            return "跑步成功-" + success;
        } catch (Exception e) {
            return "跑步失败：" + endNode.get("Data").asText();
        }
    }
}
