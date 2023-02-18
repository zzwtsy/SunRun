package cn.zzwtsy.sunrun.bean;

public class UserBean {
    private String token;
    private String imei;
    private String userId;
    private String auth;
    private String nonce;
    private String sign;
    private String timespan;

    public String getToken() {
        return token;
    }

    public UserBean setToken(String token) {
        this.token = token;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public UserBean setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public UserBean setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getAuth() {
        return auth;
    }

    public UserBean setAuth(String auth) {
        this.auth = auth;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public UserBean setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public UserBean setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getTimespan() {
        return timespan;
    }

    public UserBean setTimespan(String timespan) {
        this.timespan = timespan;
        return this;
    }
}
