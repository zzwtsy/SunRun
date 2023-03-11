import static cn.zzwtsy.sunrun.tools.Tools.randomAlphabet;

/**
 * 用户服务
 *
 * @author zzwtsy
 * @since 2023/02/17
 */
public class UserServiceTest {
    private static final String TABLE = "jklmnopqrstuvwxyza0123456789bcdefghi";

    public static String encrypt(String s) {
        String table = randomAlphabet();
        System.out.println("table:" + table);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - '0';
            result.append(table.charAt(index));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "740";
        String encrypted = encrypt(input);
        System.out.println("输入: " + input);
        System.out.println("加密结果: " + encrypted);
    }
}
