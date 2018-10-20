package com.turingoal.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 工具类-》日期工具类
 */
public final class TgDateUtil {

    private TgDateUtil() {
        throw new Error("工具类不能实例化！");
    }

    public static final Integer SIXTY = 60;
    public static final Integer TWENTY_FOUR = 24;
    public static final Integer SEVEN = 7;
    public static final Integer FOUR = 4;

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_ZH = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FORMAT_MM_DD_HH_MM_SS_ZH = "MM月dd日 HH:mm:ss";
    public static final String FORMAT_MM_DD_HH_MM_ZH = "MM月dd日 HH:mm";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_ZH = "yyyy年MM月dd HH:mm";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_YYYY_MM = "yyyy-MM";
    public static final String FORMAT_YYYYMM = "yyyyMM";
    public static final String FORMAT_YYYY = "yyyy";
    public static final String FORMAT_YYYY_MM_DD_ZH = "yyyy年MM月dd日";
    public static final String FORMAT_YYYY_MM_DD_WEEK_ZH = "yyyy年MM月dd日 EEE";
    public static final String FORMAT_YYYY_MM_ZH = "yyyy年MM月";
    public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String FORMAT_HH_MM = "HH:mm";
    public static final Date DEFAULT_START_DATE = TgDateUtil.string2Date("2000-01-01", TgDateUtil.FORMAT_YYYY_MM_DD); // 初始化开始时间
    public static final Date DEFAULT_END_DATE = TgDateUtil.string2Date("2046-12-31", TgDateUtil.FORMAT_YYYY_MM_DD); // 初始化结束时间

    /**
     * 这个方法在日历对象中设置设置时间为00:00:00
     */
    public static Calendar getMidnight(Calendar calendar) {
        if (calendar != null) {
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar;
    }

    /**
     * 得到本周第一天
     */
    public static Calendar getWeekFirst() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0)
            dayOfWeek = 7;
        calendar.add(Calendar.DATE, -dayOfWeek + 1);
        return calendar;
    }

    /**
     * 得到本周最后一天
     */
    public static Calendar getWeekLast() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0)
            dayOfWeek = 7;
        calendar.add(Calendar.DATE, -dayOfWeek + 7);
        return calendar;
    }

    /**
     * 返回不同的时间格式
     */
    public static String getDateStr(final Date date) {
        if (date == null) {
            return "";
        } else {
            long contrast = new Date().getTime() - date.getTime(); // 得到时间差
            int fz = 1000 * 60 * 1; // 1分鐘
            if (contrast < fz) { // 1分钟之内
                return "刚刚";
            } else if (contrast < fz * 30) { // 30分钟
                return "" + (contrast / fz) + "分钟前";
            } else if (contrast < fz * 60 * 1) { // 1小时
                return "半小時前";
            } else if (contrast < fz * 60 * 2) { // 2小时
                return "1小时前";
            } else {
                return date2String(date, TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH);
            }
        }
    }

    /**
     * 格式化时间
     */
    public static String date2String(final Date date, final String formatStr) {
        if (date == null) {
            return "---";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 字符串转时间
     */
    public static Date string2Date(final String dateStr, final String formatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 功能描述：返回月
     */
    public static int getMonth(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     */
    public static int getDay(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     */
    public static int getHour(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     */
    public static int getMinute(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     */
    public static int getSecond(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     */
    public static long getMillis(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(final Date date, final int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }


    /**
     * 在日期上增加天数
     */
    public static Date addDay(final Date date, final int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();

    }

    /**
     * 前一天
     */
    public static Date getBeforeDay(final Date date) {
        return addDay(date, -1);
    }

    /**
     * 获取下一天
     */
    public static Date getNextDay(final Date date) {
        return addDay(date, 1);
    }

    /**
     * 计时
     */
    public static String timing(final Date startTime) {
        long workStartTimt = startTime.getTime();
        long time = (new Date().getTime() - workStartTimt) / 1000;
        long hour = time / (SIXTY * SIXTY);
        time = time % (SIXTY * SIXTY);
        long minute = time / SIXTY;
        long second = time % SIXTY;
        String secondStr = "" + second;
        if (second < 10) {
            secondStr = "0" + second;
        }
        String minuteStr = "" + minute;
        if (minute < 10) {
            minuteStr = "0" + minute;
        }
        return hour + "小时" + minuteStr + "分" + secondStr + "秒";
    }


    /**
     * 返回距离发帖时间的时间差
     */
    public static String timeDistanceFormat(final Date startDate) {
        long timeLong = new Date().getTime() - startDate.getTime();
        if (timeLong <= 10 * 1000) {
            return "刚刚";
        } else if (timeLong < SIXTY * 1000) {
            return timeLong / 1000 + "秒前";
        } else if (timeLong < SIXTY * SIXTY * 1000) {
            timeLong = timeLong / 1000 / SIXTY;
            return timeLong + "分钟前";
        } else if (timeLong < SIXTY * SIXTY * TWENTY_FOUR * 1000) {
            timeLong = timeLong / SIXTY / SIXTY / 1000;
            return timeLong + "小时前";
        } else if (timeLong < SIXTY * SIXTY * TWENTY_FOUR * 1000 * SEVEN) {
            timeLong = timeLong / 1000 / SIXTY / SIXTY / TWENTY_FOUR;
            return timeLong + "天前";
        } else if (timeLong < SIXTY * SIXTY * TWENTY_FOUR * 1000 * SEVEN * FOUR) {
            timeLong = timeLong / 1000 / SIXTY / SIXTY / TWENTY_FOUR / SEVEN;
            return timeLong + "周前";
        } else {
            timeLong = timeLong / 1000 / SIXTY / SIXTY / TWENTY_FOUR;
            return timeLong + "天前";
        }
    }

    /**
     * 通过毫秒值，手动计算日期间的相关的值
     * <p>
     * 跨年不会出现问题
     * 使用此种方法的话需要注意
     * 如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
     */
    public static long daysBetweenTwoDays(Date fDate, Date oDate) {
        return (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
    }
}
