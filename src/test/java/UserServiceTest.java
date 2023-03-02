import java.util.Calendar;

/**
 * 用户服务
 *
 * @author zzwtsy
 * @since 2023/02/17
 */
public class UserServiceTest {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(i);
//        Map<Long, String> map = new HashMap<>();
//        map.put(1L, "2");
//        System.out.println(map);
//        map.put(1L, "4");
//        System.out.println(map);
    }
}
