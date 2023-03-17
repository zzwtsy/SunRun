package cn.zzwtsy.sunrun.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户 bean
 *
 * @author zzwtsy
 * @since 2023/02/22
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserBean {
    /**
     * 用户 Token
     */
    private String token;
    private String imei;
    private String userId;
    private String auth;
    private String nonce;
    private String sign;
    private String timespan;
}
