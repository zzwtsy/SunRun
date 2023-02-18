package cn.zzwtsy.sunrun.service;

import cn.zzwtsy.sunrun.api.Api;
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
    public Random random = new Random();
    public UserBean userBean = new UserBean();

    public void login(long qq) {
        JsonNode jsonNode;
        String imei = Config.INSTANCE.getImei().get(qq);
        try {
            jsonNode = JsonUtil.fromJson(new Api().getUserInfo(imei));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode data = jsonNode.get("Data");
        String token = data.get("Token").asText();
        String timespan = String.valueOf(System.currentTimeMillis());
        String userId = data.get("UserId").asText();
        String auth = "B" + Encryption.encryptionToMd5(Encryption.encryptionToMd5(imei)) + ":;" + token;
        String nonce = String.valueOf(random.nextInt(10000000 - 100000 + 1) + 100000);
        String sign = Encryption.encryptionToMd5(token + nonce + timespan + userId);
        userBean.setAuth(auth)
                .setUserId(userId)
                .setImei(imei)
                .setNonce(nonce)
                .setSign(sign)
                .setTimespan(timespan)
                .setToken(token);
    }

    public void getUserInfo() {
//        HttpUtil.sendGet()
    }
}
