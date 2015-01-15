package com.technology.tts.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * 时间工具类
 * @author Tim
 */
public class DateUtils {
    /**
     * 获取当前日期
     * @return
     */
    public static int getCurrentDate() {
        //本地时间
        LocalDate localDate = LocalDate.now();

        //线程安全的格式化类，不用每次都new个SimpleDateFormat，since 1.8
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String date = dateTimeFormatter.format(localDate);

        return Integer.parseInt(date);
    }
}
