package vip.crazyboy.img.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间日期类
 *
 * @author zhanjie.zhang
 * @date 17/7/5
 */
public class DateUtils {

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd";
    public final static String SIMPLE_PATTERN = "yyyyMMdd";
    public final static String YMDHM_PATTERN = "yyyy-MM-dd HH:mm";
    public final static String YMDH_PATTERN = "yyyyMMddHH";
    public final static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static String MONTH_PATTERN = "yyyy-MM";
    public final static String MONTH_PATTERN_YYYYMM = "yyyyMM";
    public static final String HOUR_TIME_FORMAT = "HH:mm";
    public static final String HOUR_TIME_FORMATV2 = " HH:mm:ss";
    public static final String DAY_HOUR_TIME = "yyyy-MM-dd HH";
    public static final String DAY_HOUR_MINUTE = "MM-dd HH:mm";
    public static final String DAY_HOUR_HOUR = "HH";
    public static final String YMDHM_PATTERN_CN = "yyyy年MM月dd日 HH:mm";
    public static final String DAY_PATTERN = "dd";

    /**
     * ms
     */
    public static final long SECOND = 1000;
    public static final long MINUTE = 60000;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final byte SUB_YEAR = Calendar.MONTH;
    public static final byte SUB_MONTH = Calendar.DAY_OF_MONTH;
    public static final byte SUB_DAY = Calendar.HOUR_OF_DAY;
    public static final byte SUB_HOUR = Calendar.MINUTE;
    public static final byte SUB_MINUTE = Calendar.SECOND;
    public static final byte SUB_SECOND = Calendar.MILLISECOND;
    //public static final SimpleDateFormat TIME_SIMPLE_FORMAT = new SimpleDateFormat(TIME_PATTERN);

    public static final String DAY_MIN_TIME = " 00:00:00";
    public static final String DAY_MAX_TIME = " 23:59:59";
    //数据库默认时间2000-01-01 00:00:00
    public static final Date DEFAULT_DB_TIME = new Date(946656000000L);



    private static ThreadLocal<Map<String, SimpleDateFormat>> simpleDateFormatThreadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new ConcurrentHashMap<String, SimpleDateFormat>();
        }
    };

    public static String format(Date date, String pattern) {
        return getFormat(pattern).format(date);
    }

    public static String format(String strDate, String pattern) {
        long times = Long.valueOf(strDate);
        Date date = new Date(times);
        return getFormat(pattern).format(date);
    }

    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    public static String getTimeToString(Date date) {
        return format(date, HOUR_TIME_FORMAT);
    }

    public static Date addDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static Date floor(int field) {
        return floor(field, new Date());
    }

    public static String createTimeString() {
        return getFormat(TIME_PATTERN).format(new Date());
    }

    public static String getTimeString(Date date) {
        try {
            return date == null ? "" : getFormat(TIME_PATTERN).format(date);
        } catch (Exception e) {
        }
        return "";
    }

    public static Date floor(int field, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case SUB_YEAR:
                calendar.set(Calendar.MONTH, 0);
            case SUB_MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            case SUB_DAY:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            case SUB_HOUR:
                calendar.set(Calendar.MINUTE, 0);
            case SUB_MINUTE:
                calendar.set(Calendar.SECOND, 0);
            case SUB_SECOND:
                calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTime();
    }

    public static Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return floor(SUB_DAY, cal.getTime());
    }

    public static String getYesterdayToString() {
        return format(getYesterday(), DEFAULT_PATTERN);
    }

    /**
     * 获取月份
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 一个月的最后时间
     */
    public static Date monthLastTime(Date date) {
        Date nextMonthBegin = getNextMonthBegin(date);
        return new Date(nextMonthBegin.getTime() - 1L);
    }

    public static Date getOneDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getOneDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到从1970年1月1日到此日期的天数<br>
     * 可以利用返回值进行日期间隔的比较<br>
     * <br>
     * 适用于不需要构造Date对象的情况，如使用System.currentTimeMillis作为参数
     */
    public static int getDayInt(long time) {
        return (int) (time / DAY);
    }

    /**
     * 得到从1970年1月1日到此日期的分钟数
     *
     * @param time
     * @return
     */
    public static int getMinuteInt(long time) {

        return (int) (time / MINUTE);
    }

    /**
     * 使用若干种规则解析时间
     *
     * @see #parse(String, String)
     */
    public static Date parse(String time, String form) {

        try {
            SimpleDateFormat format = getFormat(form);
            return format.parse(time);
        } catch (ParseException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 使用若干种规则解析时间
     *
     * @see #parse(String, String)
     */
    public static Date parse(String time) {

        try {
            SimpleDateFormat format = getFormat(DEFAULT_PATTERN);
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用若干种规则解析时间
     *
     * @see #parse(String, String)
     */
    public static Date enumParse(String time) {

        Date date = parse(time, DEFAULT_PATTERN);
        if (date == null) {
            date = parse(time, TIME_PATTERN);
        }
        return date;
    }

    /**
     * 使用若干种规则解析时间
     *
     * @see #parse(String, String)
     */
    public static Long parseLongTime(String time) {

        try {
            SimpleDateFormat format = getFormat(TIME_PATTERN);
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当前月的总天数
     *
     * @param date
     * @return
     */
    public static int getDays(Date date) {
        return Calendar.getInstance().getActualMaximum(Calendar.DATE);
    }

    /**
     * 返回是当前月的第几天
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 由于SimpleDateFormat很常用，但并不是线程安全，每次new一个出来又有点费
     * 此函数使用ThreadLocal方式缓存SimpleDateFormat，保证性能前提下较好地解决了问题
     */
    public static SimpleDateFormat getFormat(String form) {
        Map<String, SimpleDateFormat> formatMap = simpleDateFormatThreadLocal.get();
        if (formatMap.containsKey(form)) {
            return formatMap.get(form);
        } else {
            SimpleDateFormat format = new SimpleDateFormat(form);
            formatMap.put(form, format);
            return format;
        }
    }

    public static Date getFormatDate(String currDate, String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = getFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = getFormat(DEFAULT_PATTERN);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 一个月的第一天
     *
     * @param date
     * @return
     */
    public static Date getOneMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 下一个月的第一天
     *
     * @param date
     * @return
     */
    public static Date getNextMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 一个月的最后时间
     *
     * @param date
     * @return
     */
    public static Date getOneMonthLast(Date date) {
        Date nextMonthBegin = getNextMonthBegin(date);
        return new Date(nextMonthBegin.getTime() - 1L);
    }


    /**
     * 获取当月第一天参数 yyyy-MM
     *
     * @param date
     * @return
     */
    public static Date getOneMonthFirst(String date) {
        SimpleDateFormat sdf = getFormat(MONTH_PATTERN);
        Date dates = null;
        try {
            dates = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date first = getMonthBegin(dates);
        return first;
    }

    /**
     * 获取当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getOneMonthLasts(String date) {
        SimpleDateFormat sdf = getFormat(MONTH_PATTERN);
        Date dates = null;
        try {
            dates = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date last = getOneMonthLast(dates);
        return last;
    }

    /**
     * 获取两个日历的月份之差
     *
     * @param calendarBirth
     * @param calendarNow
     * @return
     */
    public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow) {
        return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)) * 12
                + calendarNow.get(Calendar.MONTH) - calendarBirth.get(Calendar.MONTH);
    }

    /**
     * 判断这一天是否是月底
     *
     * @param calendar
     * @return
     */
    public static boolean isEndOfMonth(Calendar calendar) {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    /**
     * 计算开始时间和结束时间相差的年月日
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int[] getNeturalAge(Calendar startDate, Calendar endDate) {
        int diffYears = 0, diffMonths, diffDays;
        int dayOfBirth = startDate.get(Calendar.DAY_OF_MONTH);
        int dayOfNow = endDate.get(Calendar.DAY_OF_MONTH);
        if (dayOfBirth <= dayOfNow) {
            diffMonths = getMonthsOfAge(startDate, endDate);
            diffDays = dayOfNow - dayOfBirth;
            if (diffMonths == 0) {
                diffDays++;
            }
        } else {
            if (isEndOfMonth(startDate)) {
                if (isEndOfMonth(endDate)) {
                    diffMonths = getMonthsOfAge(startDate, endDate);
                    diffDays = 0;
                } else {
                    endDate.add(Calendar.MONTH, -1);
                    diffMonths = getMonthsOfAge(startDate, endDate);
                    diffDays = dayOfNow + 1;
                }
            } else {
                if (isEndOfMonth(endDate)) {
                    diffMonths = getMonthsOfAge(startDate, endDate);
                    diffDays = 0;
                } else {
                    endDate.add(Calendar.MONTH, -1);// 上个月
                    diffMonths = getMonthsOfAge(startDate, endDate);
                    // 获取上个月最大的一天
                    int maxDayOfLastMonth = endDate.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxDayOfLastMonth > dayOfBirth) {
                        diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow;
                    } else {
                        diffDays = dayOfNow;
                    }
                }
            }
        }
        // 计算月份时，没有考虑年
        diffYears = diffMonths / 12;
        diffMonths = diffMonths % 12;
        return new int[]{diffYears, diffMonths, diffDays};
    }

    /**
     * 获取指定日期前后num天日期
     *
     * @param date
     * @param num  +-
     * @return Date
     * @author xixingwang@01zhuanche.com
     * @date 2017年7月7日上午10:50:29
     */
    public static Date getDateBeforeDay(Date date, Integer num) {
        if (null == date || null == num) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }

    /**
     * 获取指定日期 前后num小时日期
     *
     * @param date
     * @param num  +-
     * @return Date
     * @author xixingwang@01zhuanche.com
     * @date 2017年7月7日上午10:51:30
     */
    public static Date getDateBeforeHour(Date date, Integer num) {
        if (null == date || null == num) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, num);
        return calendar.getTime();
    }

    /**
     * 获取指定日期 前后num分钟日期
     *
     * @param date
     * @param num  +-
     * @return Date
     * @author xixingwang@01zhuanche.com
     * @date 2017年7月8日下午6:20:58
     */
    public static Date getDateBeforeMinute(Date date, Integer num) {
        if (null == date || null == num) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        return calendar.getTime();
    }

    public static String addDateMinut(Date date, int x)// 返回的是字符串型的时间，输入的
    {
        SimpleDateFormat format = getFormat(TIME_PATTERN);// 24小时制
        return format.format(addDateMinutB(date, x));

    }

    public static String subtractDateMinut(Date date, int x)// 返回的是字符串型的时间，输入的
    {
        SimpleDateFormat format = getFormat(TIME_PATTERN);// 24小时制
        return format.format(subtractDateMinutB(date, x));

    }

    public static String subtractDateMinut(String dateStr, int x)// 返回的是字符串型的时间，输入的
    {
        SimpleDateFormat format = getFormat(TIME_PATTERN);// 24小时制
        return format.format(subtractDateMinutB(dateStr, x));

    }

    public static Date subtractDateMinutReturnDate(String dateStr, int x)// 返回的是字符串型的时间，输入的
    {
        return subtractDateMinutB(dateStr, x);
    }

    /**
     * 减少指定分钟数
     *
     * @param date
     * @param x
     * @return
     */
    public static Date subtractDateMinutB(Date date, int x) {
        // 引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - x);
        date = cal.getTime();
        return date;
    }

    /**
     * 减少指定分钟数
     *
     * @param date
     * @param x
     * @return
     */
    public static String subtractDateSecond(Date date, int x) {
        SimpleDateFormat format = getFormat(TIME_PATTERN);
        return format.format(subtractDateSecond2(date, x));
    }

    /**
     * 减少多少秒
     */
    public static Date subtractDateSecond2(Date date, int x) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - x);
        date = cal.getTime();
        return date;

    }

    /**
     * 减少指定分钟数
     */
    public static Date subtractDateMinutB(String dateStr, int x)// 返回的是字符串型的时间，输入的
    // 是String day, int x
    {
        SimpleDateFormat format = getFormat(TIME_PATTERN);// 24小时制
        // 引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
        // 量day格式一致
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - x);
        date = cal.getTime();
        return date;

    }

    /**
     * 增加指定时间
     *
     * @param date
     * @param x
     * @return
     */
    public static Date addDateHour(Date date, int x) {
        // 引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, x);// 24小时制
        date = cal.getTime();
        return date;
    }

    /**
     * 增加指定时间
     *
     * @param date
     * @param x
     * @return
     */
    public static Date addDateMinutB(Date date, int x) {
        // 引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);// 24小时制
        date = cal.getTime();
        return date;
    }

    /**
     * 增加指定时间 秒数
     *
     * @param date
     * @param x
     * @return
     */
    public static Date addDateSecond(Date date, int x) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, x);
        date = cal.getTime();
        return date;
    }

    /**
     * 获取当前小时数（24小时制）
     *
     * @param date
     * @return
     */
    public static int getCurrentHour(Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        return rightNow.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 凌晨
     *
     * @param date
     * @return
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     * 1 返回yyyy-MM-dd 23:59:59日期
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        // 时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        // 凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            // 凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }

    /**
     * yyyy年MM月dd日 HH时mm分
     *
     * @param date
     * @return
     */
    public static String dateTimeToString(Date date) {
        SimpleDateFormat format = getFormat("yyyy年MM月dd日 HH时mm分");
        String strDate = format.format(date);
        return strDate;
    }

    /**
     * string毫秒数获取date
     *
     * @param dateTimes
     * @return
     */
    public static Date longString2Date(String dateTimes) {
        long times = Long.valueOf(dateTimes);
        Date date = new Date(times);
        return date;
    }

    public static String longString2Format(String dateTimes) {
        String strDate = "";
        if (null != dateTimes) {
            long times = Long.valueOf(dateTimes);
            Date date = new Date(times);
            strDate = getFormat(TIME_PATTERN).format(date);
        }
        return strDate;

    }

    public static String long2Format(long dateTimes) {
        String strDate = "";
        if (dateTimes > 0) {
            Date date = new Date(dateTimes);
            strDate = getFormat(TIME_PATTERN).format(date);
        }
        return strDate;

    }

    /**
     * 获取给定时间的num-1天的凌晨时间
     *
     * @param date
     * @param num
     * @return
     */
    public static synchronized Date getBeforeDate(Date date, int num) {
        Date week = weeHours(date, 0);
        if (num == 0) {
            num = -1;
        }
        Date res = getDateBeforeDay(week, num + 1);
        return res;
    }

    /**
     * 获取两个时间相差小时数
     *
     * @param before
     * @param after
     * @return 单位:小时
     */
    public static long getHourBetween2Day(Date before, Date after) {
        if (null == before || null == after || before.getTime() > after.getTime()) {
            return -1;
        }
        long hour = (after.getTime() - before.getTime()) / (HOUR);
        return hour;
    }

    /**
     * 获取两个时间相差分钟数
     *
     * @param before
     * @param after
     * @return 单位:分
     */
    public static long getMinuteBetween2Day(Date before, Date after) {
        if (null == before || null == after || before.getTime() > after.getTime()) {
            return -1;
        }
        long minute = (after.getTime() - before.getTime()) / (MINUTE);
        return minute;
    }

    /**
     * 获取两个时间相差秒数
     * @param before
     * @param after
     * @return 单位:秒
     */
    public static long getSecondBetween2Day(Date before, Date after) {
        if (null == before || null == after || before.getTime() > after.getTime()) {
            return -1;
        }
        long second = (after.getTime() - before.getTime()) / (SECOND);
        return second;
    }

    /**
     * 星期获取
     *
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 返回格式：yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static String getDateYMD(String dateStr) {
        if (dateStr != null && dateStr.length() > 9) {
            dateStr = dateStr.substring(0, 10);
        }
        return dateStr;
    }

    /**
     * 按照日期获取时间
     *
     * @param date
     * @return
     */
    public static int getHoursDay(Date date) {
        SimpleDateFormat smf = getFormat(DAY_HOUR_HOUR);
        String res = smf.format(date);
        int hour = Integer.valueOf(res);
        return hour;
    }

    /**
     * 是否小于等于30分钟
     *
     * @param currentDate 当前时间
     * @param bookingDate 订单预约上车时间
     * @param outTime     超过阈值,单位:分钟,默认30分钟
     * @return true 小于等于30分钟; 否则false 大于30分钟
     */
    public static boolean isInTime(Date currentDate, Date bookingDate, int outTime) {
        long gap = getMinuteBetween2Day(currentDate, bookingDate);
        if (gap <= outTime) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否处于一个时间段内
     * @param thisDate
     * @param beginHour
     * @param endHour
     * @return
     */
    public static boolean isBelong(Date thisDate, String beginHour, String endHour) {
        return isBelong(thisDate, beginHour, endHour, false);
    }

    /**
     * 判断是否处于一个时间段内 如果跨天,则需要判断两次
     *
     * @param thisDate
     * @param beginHour
     * @param endHour
     * @param isOverDay 是否跨天
     * @return 属于时间段内, 返回true;否则返回false
     */
    public static boolean isBelong(Date thisDate, String beginHour, String endHour, boolean isOverDay) {
        if (StringUtils.isBlank(beginHour) || StringUtils.isBlank(endHour)) {
            return false;
        }
        SimpleDateFormat df = getFormat(HOUR_TIME_FORMAT);
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(thisDate));
            beginTime = df.parse(beginHour + ":00");
            endTime = df.parse(endHour + ":00");
            if (isOverDay) {
                if (Integer.parseInt(beginHour) > Integer.parseInt(endHour)) {
                    boolean a = isBelong(thisDate, beginHour, "24");
                    boolean b = isBelong(thisDate, "00", endHour);
                    return a || b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return belongCalendar(now, beginTime, endTime);
    }

    /**
     * 判断是否处于一个时间段内 如果跨天,则需要判断两次
     *
     * @param thisDate 时间
     * @param beginTimeStr 开始时间 HH:mm
     * @param endTimeStr 结束时间 HH:mm
     * @param isOverDay 是否跨天
     * @return 属于时间段内, 返回true;否则返回false
     */
    public static boolean isBelongTime(Date thisDate, String beginTimeStr, String endTimeStr, boolean isOverDay) {
        if (StringUtils.isBlank(beginTimeStr) || StringUtils.isBlank(endTimeStr)) {
            return false;
        }
        if (beginTimeStr.length() >= 1 && beginTimeStr.length() <= 2 && endTimeStr.length() >= 1
                && endTimeStr.length() <= 2) {
            return isBelong(thisDate, beginTimeStr, endTimeStr, isOverDay);
        }
        SimpleDateFormat df = getFormat(HOUR_TIME_FORMAT);
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(thisDate));
            beginTime = df.parse(beginTimeStr);
            endTime = df.parse(endTimeStr);
            if (isOverDay) {
                if (beginTime.compareTo(endTime)>0) {
                    boolean a = isBelongTime(thisDate, beginTimeStr, "24:00", false);
                    boolean b = isBelongTime(thisDate, "00:00", endTimeStr, false);
                    return a || b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return belongCalendar(now, beginTime, endTime);
    }

//    public static void main(String[] args) {
//        boolean f = isBelongTime(new Date(2018,11,12,22,0,37),"00","22",true);
//        System.out.println(f);
//        Date now = new Date(2018,11,12,0,0,37);
//        Date start = new Date(2018,11,11,0,0,0);
//        Date end = new Date(2018,11,11,24,0,0);
//        int flag = start.compareTo(now);
//        System.out.println(flag);
//    }


    /**
     * 判断时间是否在一个时间段内
     */
    public static boolean bookingDateInEarlyMorning(Date bookingDate, String startHour, String endHour) {
        return isBelong(bookingDate, startHour, endHour);
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        //[begin,end)
        if (begin.compareTo(date)<1 && date.compareTo(end)<0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 毫秒字符串转date
     */
    public static Date strLongToDate(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        Date date = new Date(Long.valueOf(time));
        return date;
    }

    /**
     *
     * @param time
     * @return
     */
    public static Date longToDate(long time) {
        Date date = new Date(time);
        return date;
    }

    /**
     * Description 判断目标时间是否在给定时间段内.仅适用于以下格式.
     * startDate="2018-12-03" endDate="2018-12-09" startTime="15" endTime="24"
     * @param: [date, startDate, endDate, startTime, endTime]
     * @return: boolean
     * @author: LiuPeng
     * @date: 2018/12/12 15:49
     */
    public static boolean betweenDate(Date date,String startDate,String endDate,String startTime,String endTime){
        try {
            if(null == date || StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate) || StringUtils
                    .isBlank(startTime) || StringUtils.isBlank(endTime)){
                return false;
            }
            SimpleDateFormat sdf = getFormat(TIME_PATTERN);
            Date start = sdf.parse(startDate + DAY_MIN_TIME);
            Date end = sdf.parse(endDate + DAY_MAX_TIME);
            if(date.after(start) && date.before(end)){
                return isBelongTime(date,startTime,endTime,true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     *  判断时间是否在日期之内
     * @param date 时间
     * @param startDate  yyyy-MM-dd
     * @param endDate yyyy-MM-dd
     * @return
     */
    public static boolean inDate(Date date, String startDate, String endDate) {

        if(StringUtils.isBlank(startDate)|| StringUtils.isBlank(endDate)){
            return false;
        }
        startDate = startDate + " 00:00:00";
        endDate = endDate + " 23:59:59";
        try {
            Date startDateTime = parse(startDate, TIME_PATTERN);
            Date endDateTime = parse(endDate, TIME_PATTERN);
            if(date.after(startDateTime)&&date.before(endDateTime)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
