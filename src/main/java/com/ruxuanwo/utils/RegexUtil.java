package com.uhope.data.export.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 包含了常用表达式常量，也有返回true or false的方法
 * 有些是否包含等方法使用的是find()，比如字符串是否包含数字，用的是find()不是matches()
 * find()部分匹配就返回true，matches()需要完全匹配到返回true，所以包含某某就用find，精准用matches()
 *
 * @Author: 如漩涡
 * @Date: 2018/8/28 14:57
 */
public class RegexUtil {

    /**
     * ip正则
     */
    public static final String IP_REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /**
     * 中文正则
     */
    public static final String CHINESE_REGEX = "[\\u4E00-\\u9FA5]+";

    /**
     * 整数正则
     */
    public static final String NUMBER_REGEX = "[0-9]";

    /**
     * 浮点数正则
     */
    public static final String FLOAT_REGEX = "\\-?[0-9]\\d+(\\.\\d+)?";

    /**
     * 特殊字符正则
     */
    public static final String SPECIALCHAR_REGEX = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";

    /**
     * 身份证正则
     */
    public static final String IDCARD_REGEX = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";

    /**
     * 手机号码正则
     */
    public static final String MOBILE_REGEX = "(\\+\\d+)?1[3458]\\d{9}$";

    /**
     * 固定电话正则
     */
    public static final String PHONE_REGEX = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";

    /**
     * 空白字符正则
     */
    public static final String BLANKSPACE_REGEX = "\\s+";

    /**
     * 时间正则(YYYY-MM-DD HH:MM:SS、YYYY/MM/DD HH:MM:SS、YYYYMMDD HH:MM:SS，可时间，可日期)
     */
    public static final String TIME_ALL_REGEX = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))" +
            "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])" +
            "|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468]" +
            "[1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?" +
            "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|" +
            "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])" +
            "|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9]))?)))?$";

    /**
     * 日期（yyyy-MM-dd），精确，能检查到2月及31号
     */
    public static final String DATE_REGEX = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))" +
            "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|" +
            "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|" +
            "([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" +
            "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?" +
            "((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    /**
     * 时间（HH:mm:ss或HH:mm）
     */
    public static final String TIME_REGEX = "^((([0-1][0-9])|2[0-3]):[0-5][0-9])(:[0-5][0-9])?$";

    /**
     * URL正则
     */
    public static final String URL_REGEX = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*" +
            "(\\??(.+=.*)?(&.+=.*)?)?";

    /**
     * 获取域名正则
     */
    public static final String DOMAIN_REGEX = "(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)";

    /**
     * 中国邮政正则
     */
    public static final String POSTCODE_REGEX = "[1-9]\\d{5}";

    /**
     * 字母正则
     */
    public static final String LETTER_REGEX = "^[A-Za-z]+$";

    /**
     * 数字字母正则
     */
    public static final String NUM_LET_REGEX = "^[A-Za-z0-9]+";

    /**
     * 图片正则
     */
    public static final String PICTURE_REGEX = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

    /**
     * 压缩文件正则
     */
    public static final String ZIP_REGEX = "(.*)\\.(rar|zip|7zip|tgz)$";

    /**
     * QQ号码，最短5位，最长15位数字
     */
    public static final String QQ_REGEX = "^[1-9]\\d{4,14}$";

    /**
     * 验证IP是否符合要求
     * @param ip
     * @return
     */
    public static boolean checkIp(String ip){
        return Pattern.matches(IP_REGEX, ip);
    }

    /**
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        return Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * 验证是否中文
     * @param chinese
     * @return
     */
    public static boolean checkChinese(String chinese){
        return Pattern.compile(CHINESE_REGEX).matcher(chinese).find();
    }

    /**
     * 验证是否有数字
     * @param number
     * @return
     */
    public static boolean checkNumber(String number){
        return Pattern.compile(NUMBER_REGEX).matcher(number).find();
    }

    /**
     * 验证是否有浮点数
     * @param floats
     * @return
     */
    public static boolean checkFloat(String floats){
        return Pattern.matches(FLOAT_REGEX, floats);
    }

    /**
     * 验证是否带有特殊字符
     * @param specialChar
     * @return
     */
    public static boolean checkSpecialChar(String specialChar){
        return Pattern.compile(SPECIALCHAR_REGEX).matcher(specialChar).find();
    }

    /**
     * 验证身份证号码是否正确
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard){
        return Pattern.matches(IDCARD_REGEX, idCard);
    }

    /**
     * 验证手机号码是否正确
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile){
        return Pattern.matches(MOBILE_REGEX, mobile);
    }

    /**
     * 验证固定电话是否正确
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone){
        return Pattern.matches(PHONE_REGEX, phone);
    }

    /**
     * 验证是否空白字符
     * @param blankSpace
     * @return
     */
    public static boolean checkBlankSpace(String blankSpace){
        return Pattern.matches(BLANKSPACE_REGEX, blankSpace);
    }

    /**
     * 验证是否日期，可以是全日期，或者时间、日期
     * @param time
     * @return
     */
    public static boolean checkTimeAll(String time){
        return Pattern.matches(TIME_ALL_REGEX, time);
    }

    /**
     * 验证是否日期
     * @param time
     * @return
     */
    public static boolean checkDate(String time){
        return Pattern.matches(DATE_REGEX, time);
    }

    /**
     * 验证是否时间
     * @param time
     * @return
     */
    public static boolean checkTime(String time){
        return Pattern.matches(TIME_REGEX, time);
    }

    /**
     * 验证URL地址是否正确
     * @param url
     * @return
     */
    public static boolean checkUrl(String url){
        return Pattern.matches(URL_REGEX, url);
    }

    /**
     * 获取网址 URL 的一级域名
     * @param url
     * @return
     */
    public static String getDomain(String url){
        Pattern pattern = Pattern.compile(DOMAIN_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * 验证中国邮政是否正确
     * @param postCode
     * @return
     */
    public static boolean checkPostCode(String postCode){
        return Pattern.matches(POSTCODE_REGEX, postCode);
    }

    /**
     * 验证是否存在字母
     * @param letter
     * @return
     */
    public static boolean checkLetter(String letter){
        return Pattern.matches(LETTER_REGEX, letter);
    }

    /**
     * 验证是否存在字母和数字
     * @param numLet
     * @return
     */
    public static boolean checkNumLet(String numLet){
        return Pattern.matches(NUM_LET_REGEX, numLet);
    }

    /**
     * 验证图片格式是否正确
     * @param picture
     * @return
     */
    public static boolean checkPicture(String picture){
        return Pattern.matches(PICTURE_REGEX, picture);
    }

    /**
     * 验证压缩格式是否正确
     * @param zip
     * @return
     */
    public static boolean checkZip(String zip){
        return Pattern.matches(ZIP_REGEX, zip);
    }

    /**
     * 验证QQ号是否正确
     * @param qq
     * @return
     */
    public static boolean checkQq(String qq){
        return Pattern.matches(QQ_REGEX, qq);
    }

}
