import java.util.Calendar;

/**
 * 用户服务
 *
 * @author zzwtsy
 * @since 2023/02/17
 */
public class UserServiceTest {

    public static void main(String[] args) {
//        String imei = "IMEICode=16a18751777c412d91c07b79ca3c0e9b";
//        String[] split = imei.split("=");
//        System.out.println(split.length);
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(i);
    }
}
