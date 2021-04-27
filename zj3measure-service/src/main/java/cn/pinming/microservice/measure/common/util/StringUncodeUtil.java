package cn.pinming.microservice.measure.common.util;

public class StringUncodeUtil {

    public static String convert(String a) {
        StringBuilder b = new StringBuilder();
        for (char c : a.toCharArray()) {
            if (c != 65533) {
                b.append(c);
            }
        }
        return b.toString();
    }

}
