package cn.pinming.microservice.measure.common.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luo hao
 */
public class CamelUtils {

    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }


    /**
     * 部门code转换及组合
     * @param code 部门code
     * @return 部门code上下级列表
     */
    public static List<String> getCodeAsList(String code){

//        String regEx = "\\d\\|";
        String regEx = "\\d+\\|";
        Pattern p = Pattern.compile(regEx); //编译对象
        Matcher m = p.matcher(code);
        List<String> list = new ArrayList<>();
        while (m.find()){
            list.add(m.group());
        }
        List<String> newList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            sb.setLength(0);
            for (int j = 0; j <= i; j++){
                sb.append(list.get(j));
            }
            newList.add(sb.toString());
        }
        return newList;
    }

    /**
     * 获取近n个月
     */
    public static List<String> getLastMonths(int size) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> list = new ArrayList(size);
        for (int i=0;i<size;i++) {
            c.setTime(new Date());
            c.add(Calendar.MONTH, -i);
            Date m = c.getTime();
            list.add(sdf.format(m));
        }
        Collections.reverse(list);
        return list;

    }
}
