package com.ruxuanwo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期操作辅助类
 *
 * @author 如漩涡
 */
public final class DateUtil {
    private static SimpleDateFormat sf;
    private static SimpleDateFormat sdf;
    /**
     * 英文简写如：2010
     */
    public static String FORMAT_Y = "yyyy";

    /**
     * 英文简写如：12:01
     */
    public static String FORMAT_HM = "HH:mm";

    /**
     * 英文简写如：1-12 12:01
     */
    public static String FORMAT_MDHM = "MM-dd HH:mm";

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 英文全称  如：2010-12-01 23:15
     */
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL_SN = "yyyyMMddHHmmssS";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_YMD_CN = "yyyy年MM月dd日";

    /**
     * 中文简写  如：2010年12月01日  12时
     */
    public static String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

    /**
     * 中文简写  如：2010年12月01日  12时12分
     */
    public static String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分";

    /**
     * 中文全称  如：2010年12月01日  23时15分06秒
     */
    public static String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static Calendar calendar = null;
    /**
     * 初始化一些时间设置格式
     */

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm",Locale.CHINA);

    private static final Calendar INSTANCE = Calendar.getInstance();
    public static int getYear(){
        return INSTANCE.get(Calendar.YEAR);
    }
    public static int getMonth(){
        return INSTANCE.get(Calendar.MONTH);
    }
    public static int getDay(){
        return INSTANCE.get(Calendar.DAY_OF_MONTH);
    }
    public static int getHours(){
        return INSTANCE.get(Calendar.HOUR_OF_DAY);
    }
    public static int getMinutes(){
        return INSTANCE.get(Calendar.MINUTE);
    }
    public static int getSeconds(){
        return INSTANCE.get(Calendar.SECOND);
    }

    private DateUtil(){

    }

    /**
     * 获取N天前、N天后的 日期
     *
     * @param nowDate
     *            当前日期;
     * @param dayAddNum
     *            正数：N天前，负数：N天后;
     * @return
     */
    public static Date getAddDay(Date nowDate, int dayAddNum) {
        Calendar tday = new GregorianCalendar();
        tday.setTime(nowDate);
        tday.add(Calendar.DAY_OF_MONTH, dayAddNum);
        Date preDate = tday.getTime();
        return preDate;
    }
    /**
     * 获取N天前、N天后的 日期
     * @param nowDate  当前时间戳;
     * @param dayAddNum 正数：N天前，负数：N天后;
     * @return
     */
    public static Date getAddDay(long nowDate, int dayAddNum){
        return getAddDay(new Date(nowDate), dayAddNum);
    }

    /**
     * 时间格式化
     * @param format
     * @param time
     * @return
     */
    public static String formatDate(String format,Long time) {
        return formatDate(new SimpleDateFormat(format, Locale.CHINA), time);
    }
    /**
     * 时间格式化
     * @param format
     * @param time
     * @return
     */
    public static String formatDate(SimpleDateFormat format,Long time) {
        if(null==time || time<=0) return "";
        return format.format(new Date(String.valueOf(time).length() == 13 ? time : time * 1000));
    }

    /**
     * 将秒转换为（ 分：秒 格式）
     * @param time 单位：秒
     * @return
     */
    public static String getTimeFromInt(int time) {
        if (time <= 0) return "00:00";
        int secondnd = time / 60;
        int million = time % 60;
        String f = secondnd >= 10 ? String.valueOf(secondnd) : "0" + String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0" + String.valueOf(million);
        return f + ":" + m;
    }

    /**
     * 根据月份获得最大天数
     * @param year 年
     * @param month 月
     * @return 最大天数
     */
    public static int getMaxDayByMonth(int year,int month){
        Calendar time=Calendar.getInstance();//使用默认时区和语言环境获得一个日历
        //注意：在使用set方法之前，必须先调用clear（），否则很多信息会继承自系统当前的时间
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month);//注意Calendar对象默认一月是为零的
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//获得本月份的天数
        return day;
    }

    /**
     * 根据日期获得星期
     */
    public static String getWeek(Date d) {
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return (dayNames[dayOfWeek]);
    }

    /**
     * 时间格式化（刚刚、几分钟前、几小时前、昨天、前天、年-月-日）
     * @param time
     * @return
     */
    public static String getShortTime(long time) {
        String shortstring = "";
        if (time == 0)
            return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long datetime = (now - time) / 1000;
        if (datetime > 365 * 24 * 60 * 60) {
            shortstring = FORMAT_DATE.format(new Date(time));
        } else if (datetime > 24 * 60 * 60 && ((int) (datetime / (24 * 60 * 60)))==2){
            shortstring = "前天";
        } else if (datetime > 24 * 60 * 60 && ((int) (datetime / (24 * 60 * 60)))==1){
            shortstring = "昨天";
        } else if (datetime > 60 * 60) {
            shortstring = (int) (datetime / (60 * 60)) + "小时前";
        } else if (datetime > 60) {
            shortstring = (int) (datetime / (60)) + "分钟前";
        } else {
            shortstring = "刚刚";
        }
        return shortstring;
    }
    /**
     * 时间格式化(秒，分，小时，天)
     * @param time
     * @return
     */
    public static String getShortTime2(long time) {
        String shortstring = "";
        if (time == 0)
            return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long datetime = (now - time) / 1000; // 秒
        if (datetime > 24 * 60 * 60) {
            shortstring = (int) (datetime / (24 * 60 * 60)) + "天";
        } else if (datetime > 60 * 60) {
            shortstring = (int) (datetime / (60 * 60)) + "小时";
        } else if (datetime > 60) {
            shortstring = (int) (datetime / (60)) + "分钟";
        } else {
            shortstring = (int) datetime + "秒";
        }
        return shortstring;
    }
    /**
     * 获取当前日期上一周的开始日期 （周日）
     */
    public static String previousWeekByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - s);//根据日历的规则，给当前日期减往星期几与一个星期第一天的差值
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }
    /**
     * 获取当前日期上一周的结束日期 （周六）
     */
    public static String previousWeekEndDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + (6 - s));
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }
    /**
     * 获取当前日期当前一周的开始日期 （周日）
     */
    public static String getCurrentWeekFirstDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - s);//根据日历的规则，给当前日期减往星期几与一个星期第一天的差值

        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }
    /**
     * 获取当前日期当前一周的结束日期 （周六）
     */
    public static String getCurrentWeekEndDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + (6 - s));

        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }
    /**
     * 返回上一个月的第一天
     *
     * @return 20120201
     */
    public static String previousMonthByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 2, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }
    /**
     * 返回下一个月的第一天
     *
     * @return 20120401
     */
    public static String nextMonthByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }
    /**
     * 返回当前月的第一天
     *
     * @return 20120101
     */
    public static String getCurrentMonthFirstDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    //转换中文对应的时段
    public static String convertNowHour2CN(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hourString = sdf.format(date);
        int hour = Integer.parseInt(hourString);
        if(hour < 6) {
            return "凌晨";
        }else if(hour >= 6 && hour < 12) {
            return "早上";
        }else if(hour == 12) {
            return "中午";
        }else if(hour > 12 && hour <=18) {
            return "下午";
        }else if(hour >=19) {
            return "晚上";
        }
        return "";
    }

    //剩余秒数转换
    public static String convertSecond2Day(int time) {
        int day = time/86400;
        int hour = (time - 86400*day)/3600;
        int min = (time - 86400*day - 3600*hour)/60;
        int sec = (time - 86400*day - 3600*hour - 60*min);
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("天");
        sb.append(hour);
        sb.append("时");
        sb.append(min);
        sb.append("分");
        sb.append(sec);
        sb.append("秒");
        return sb.toString();
    }
    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     **/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："yyyy "
     **/
    public static String getCurrentYear() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："MM"
     **/
    public static String getCurrentMonth() {
        Date d = new Date();
        sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："dd"
     **/
    public static String getCurrentDay() {
        Date d = new Date();
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentTime() {
        long d = new Date().getTime() / 1000;
        return d;
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 时间戳中获取年
     */
    public static String getYearFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 时间戳中获取月
     */
    public static String getMonthFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 时间戳中获取日
     */
    public static String getDayFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) throws java.text.ParseException {
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    public static Date str2Date(String str) {
        return str2Date(str, null);
    }


    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);
    }


    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }


    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }


    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }


    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }


    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }


    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" +
                c.get(Calendar.DAY_OF_MONTH) + "-" +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) +
                ":" + c.get(Calendar.SECOND);
    }


    /**
     * 获得当前日期的字符串格式
     * @param format    格式化的类型
     * @return  返回格式化之后的事件
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);

    }


    /**
     *
     * @param time 当前的时间
     * @return  格式到秒
     */
    //
    public static String getMillon(long time) {

        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);

    }


    /**
     *
     * @param time  当前的时间
     * @return  当前的天
     */
    public static String getDay(long time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }


    /**
     *
     * @param time 时间
     * @return 返回一个毫秒
     */
    // 格式到毫秒
    public static String getSMillon(long time) {

        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

    }


    /**
     * 在日期上增加数个整月
     * @param date 日期
     * @param n 要增加的月数
     * @return   增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();

    }


    /**
     * 在日期上增加天数
     * @param date 日期
     * @param n 要增加的天数
     * @return   增加之后的天数
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();

    }


    /**
     * 获取距现在某一小时的时刻
     *
     * @param format 格式化时间的格式
     * @param h 距现在的小时 例如：h=-1为上一个小时，h=1为下一个小时
     * @return  获取距现在某一小时的时刻
     */
    public static String getNextHour(String format, int h) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime() + h * 60 * 60 * 1000);
        return sdf.format(date);

    }


    /**
     * 获取时间戳
     * @return 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());

    }



    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }


    /**
     * 获得默认的 date pattern
     * @return  默认的格式
     */
    public static String getDatePattern() {

        return FORMAT_YMDHMS;
    }


    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();

        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }


    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return 提取字符串的日期
     */
    public static Date parse(String strDate) throws java.text.ParseException {
        return parse(strDate, getDatePattern());

    }


    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }


    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return 按默认格式的字符串距离今天的天数
     */
    public static int countDays(String date) throws java.text.ParseException {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

    }


    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return  提取字符串日期
     */
    public static Date parse(String strDate, String pattern) throws java.text.ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date 日期字符串
     * @param format 日期格式
     * @return  按用户格式字符串距离今天的天数
     */
    public static int countDays(String date, String format) throws java.text.ParseException {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

    }
}
