package cn.zzwtsy.sunrun.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户 bean
 *
 * @author zzwtsy
 * @since 2023/02/22
 */
@Getter
@Setter
@Builder
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
