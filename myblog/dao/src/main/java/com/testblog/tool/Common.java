package com.testblog.tool;

import java.text.ParsePosition;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Common {

    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);//这个是字符串
//        System.out.println(dateString);
//        System.out.println("=-==-1");
        ParsePosition pos = new ParsePosition(8);
//        System.out.println(pos);
//        System.out.println("=-==-2");
        Date currentTime_2 = formatter.parse(dateString, pos);
//        System.out.println(currentTime_2);
//        System.out.println("=-==-3");
        return currentTime_2;
    }
    /**
     * 获取现在时间,这个好用
     *
     * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static Date getSqlDate() {
        Date sqlDate = new java.sql.Date(new Date().getTime());
        return sqlDate;
    }
}
