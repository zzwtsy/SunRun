import cn.zzwtsy.sunrun.api.Api;
import cn.zzwtsy.sunrun.bean.UserBean;
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
public class UserServiceTest {

    public static void main(String[] args) {
//        Long qqId = random.nextLong();
        JsonNode jsonNode;
//        String imei = Config.INSTANCE.getImei().get(qqId);
        String imei = "44430da8e68a445a8c301bce27906ca9";
        try {
            jsonNode = JsonUtil.fromJson(new Api().getUserInfo(imei));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        Random random = new Random();
        JsonNode data = jsonNode.get("Data");
        String token = data.get("Token").asText();
        String timespan = String.valueOf(System.currentTimeMillis());
        String userId = data.get("UserId").asText();
        String auth = "B" + Encryption.encryptionToMd5(Encryption.encryptionToMd5(imei)) + ":;" + token;
        String nonce = String.valueOf(random.nextInt(10000000 - 100000 + 1) + 100000);
        String sign = Encryption.encryptionToMd5(token + nonce + timespan + userId);
        String string = UserBean.builder()
                .nonce(nonce)
                .timespan(timespan)
                .userId(userId)
                .auth(auth)
                .sign(sign)
                .imei(imei)
                .token(token)
                .toString();
        System.out.println(string);
    }
}
