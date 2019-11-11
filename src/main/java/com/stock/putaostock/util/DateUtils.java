//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.stock.putaostock.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String ZH_PATTERN_MONTH = "yyyy-MM";
    public static String ZH_PATTERN_DAY = "yyyy-MM-dd";
    public static String ZH_PATTERN_HOUR = "yyyy-MM-dd HH";
    public static String ZH_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
    public static String ZH_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static String ZH_PATTERN_MONTH_DD = "MM-dd";

    public DateUtils() {
    }

    public static long currentTimeSecs() {
        return Clock.systemUTC().millis() / 1000L;
    }

    public static long currentTimeMillis() {
        return Clock.systemUTC().millis();
    }

    public static String dateTimeFormatter(String pattern) {
        return dateTimeFormatter(pattern, LocalDateTime.now());
    }

    public static String dateTimeFormatter(String pattern, LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern(ZH_PATTERN_DAY).withLocale(Locale.CHINESE);
        return LocalDate.parse(date, germanFormatter);
    }

    public static LocalDate stringToLocalDate(String date, String pattern) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern(pattern).withLocale(Locale.CHINESE);
        return LocalDate.parse(date, germanFormatter);
    }

    public static LocalDate stringToLocalDate(Long date) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.CHINESE);
        return LocalDate.parse(date.toString(), germanFormatter);
    }

    public static LocalDateTime stringToLocalDatetime(String date) {
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern(ZH_PATTERN_SECOND);
        return LocalDateTime.parse(date, germanFormatter);
    }

    public static long unixTime(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return localDate.atStartOfDay(zoneId).toEpochSecond();
    }

    public static long unixTime(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        return localDateTime.atZone(zoneId).toEpochSecond();
    }

    public static LocalDateTime unixTimeToLocalDateTime(long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.systemDefault());
    }

    public static String longToDateString(long unixTimeStamp, String pattern) {
        return unixMillsToLocalDateTime(unixTimeStamp).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime unixMillsToLocalDateTime(long timeMills) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMills), ZoneId.systemDefault());
    }

    public static LocalDate unixTimeToLocalDate(long unixTime) {
        return unixTimeToLocalDateTime(unixTime).toLocalDate();
    }

    public static LocalDate unixMillsToLocalDate(long timeMills) {
        return unixMillsToLocalDateTime(timeMills).toLocalDate();
    }

    public static long stringDateToTimeStampSecs(String dateString, String pattern) {
        return unixTime(stringToLocalDate(dateString, pattern));
    }

    public static long stringDateToTimeStampMillis(String dateString, String pattern) {
        return unixTime(stringToLocalDate(dateString, pattern)) * 1000L;
    }

    public static long getTimeInMillisOfMondayMorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTimeMillis());
        int dayOfWeek = cal.get(7);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }

        cal.add(5, 2 - dayOfWeek);
        return getDayStartTimeInMillis(cal.getTime());
    }

    public static long getTimeInSecondOfMondayMorning() {
        return getTimeInMillisOfMondayMorning() / 1000L;
    }

    public static long getTimeInSecondOfSundayEvening() {
        return getTimeInMillisOfSundayEvening() / 1000L;
    }

    public static long getTimeInMillisOfSundayEvening() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimeInMillisOfMondayMorning());
        cal.add(7, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTimeInMillis(weekEndSta);
    }

    public static long getDayStartTimeInSecond(Date date) {
        return getDayStartTimeInMillis(date) / 1000L;
    }

    public static long getDayStartTimeInMillis() {
        return getDayStartTimeInMillis(new Date());
    }

    public static long getDayStartTimeInSecond() {
        return getDayStartTimeInMillis() / 1000L;
    }

    public static long getDayStartTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }

        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static long getDayEndTimeInMillis() {
        return getDayEndTimeInMillis(new Date());
    }

    public static long getDayEndTimeInSecond() {
        return getDayEndTimeInMillis() / 1000L;
    }

    public static long getDayEndTimeInSecond(Date date) {
        return getDayEndTimeInMillis(date);
    }

    public static long getDayEndTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }

        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 23, 59, 59);
        calendar.set(14, 999);
        return calendar.getTimeInMillis();
    }

    public static long getMonthStartTimeInMills(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }

        c.add(2, 0);
        c.set(5, 1);
        return getDayStartTimeInMillis(c.getTime());
    }

    public static long getMonthStartTimeInSecond(Date date) {
        return getMonthStartTimeInMills(date) / 1000L;
    }

    public static long getCurrentMonthStartTimeInMillis() {
        return getMonthStartTimeInMills(new Date());
    }

    public static long getCurrentMonthStartTimeInSecond() {
        return getMonthStartTimeInSecond(new Date());
    }

    public static long getMonthEndTimeInMills(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }

        c.set(5, c.getActualMaximum(5));
        return getDayEndTimeInMillis(c.getTime());
    }

    public static long getMonthEndTimeInSecond(Date date) {
        return getMonthEndTimeInMills(date) / 1000L;
    }

    public static long getCurrentMonthEndTimeInMillis() {
        return getMonthEndTimeInMills(new Date());
    }

    public static long getCurrentMonthEndTimeInSecond() {
        return getMonthEndTimeInSecond(new Date());
    }

    public static int getYearFromSecond(long second) {
        return getYearFromMillis(second * 1000L);
    }

    public static int getYearFromMillis(long millis) {
        return getLocalDateTimeByMillis(millis).getYear();
    }

    public static LocalDateTime getLocalDateTimeByMillis(long millis) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    public static int getMonthFromMillis(long millis) {
        return getLocalDateTimeByMillis(millis).getMonthValue();
    }

    public static int getMonthFromSecond(long second) {
        return getMonthFromMillis(second * 1000L);
    }

    public static int getDayFromMillis(long millis) {
        return getLocalDateTimeByMillis(millis).getDayOfMonth();
    }

    public static int getDayFromSecond(long second) {
        return getDayFromMillis(second * 1000L);
    }

    public static long getYearMonthDayFromMillis(long millis) {
        LocalDateTime localDateTime = getLocalDateTimeByMillis(millis);
        return (long)((localDateTime.getYear() * 100 + localDateTime.getMonthValue()) * 100 + localDateTime.getDayOfMonth());
    }

    public static long getYearMonthDayFromSecond(long second) {
        return getYearMonthDayFromMillis(second * 1000L);
    }

    public static long getYearMonthFromMillis(long millis) {
        LocalDateTime localDateTime = getLocalDateTimeByMillis(millis);
        return (long)(localDateTime.getYear() * 100 + localDateTime.getMonthValue());
    }

    public static long getYearMonthFromSecond(long second) {
        return getYearMonthFromMillis(second * 1000L);
    }

    public static void main(String[] args) {
        System.out.println(getYearFromMillis(currentTimeMillis()));
    }

    public static enum Format {
        YYYY {
            public long LongValue() {
                return YYYY.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return (long)localDateTime.getYear();
            }

            public String StringValue(String... pattern) {
                return YYYY.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return String.valueOf(localDateTime.getYear());
            }
        },
        YYYYMM {
            public long LongValue() {
                return YYYYMM.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return (long)(localDateTime.getYear() * 100 + localDateTime.getMonthValue());
            }

            public String StringValue(String... pattern) {
                return YYYYMM.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return pattern.length == 0 ? DateUtils.dateTimeFormatter(DateUtils.ZH_PATTERN_MONTH, localDateTime) : DateUtils.dateTimeFormatter(pattern[0], localDateTime);
            }
        },
        YYYYMMDD {
            public long LongValue() {
                return YYYYMMDD.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMdd")));
            }

            public String StringValue(String... pattern) {
                return YYYYMMDD.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return pattern.length == 0 ? DateUtils.dateTimeFormatter(DateUtils.ZH_PATTERN_DAY, localDateTime) : DateUtils.dateTimeFormatter(pattern[0], localDateTime);
            }
        },
        YYYYMMDDHH {
            public long LongValue() {
                return YYYYMMDDHH.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHH")));
            }

            public String StringValue(String... pattern) {
                return YYYYMMDDHH.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return pattern.length == 0 ? DateUtils.dateTimeFormatter(DateUtils.ZH_PATTERN_HOUR, localDateTime) : DateUtils.dateTimeFormatter(pattern[0], localDateTime);
            }
        },
        YYYYMMDDHHMM {
            public long LongValue() {
                return YYYYMMDDHHMM.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHHmm")));
            }

            public String StringValue(String... pattern) {
                return YYYYMMDDHHMM.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return pattern.length == 0 ? DateUtils.dateTimeFormatter(DateUtils.ZH_PATTERN_MINUTE, localDateTime) : DateUtils.dateTimeFormatter(pattern[0], localDateTime);
            }
        },
        YYYYMMDDHHMMSS {
            public long LongValue() {
                return YYYYMMDDHHMMSS.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss")));
            }

            public String StringValue(String... pattern) {
                return YYYYMMDDHHMMSS.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return pattern.length == 0 ? DateUtils.dateTimeFormatter(DateUtils.ZH_PATTERN_SECOND, localDateTime) : DateUtils.dateTimeFormatter(pattern[0], localDateTime);
            }
        },
        MMDD {
            public long LongValue() {
                return MMDD.LongValue(LocalDateTime.now());
            }

            public long LongValue(LocalDateTime localDateTime) {
                return Long.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("MMdd")));
            }

            public String StringValue(String... pattern) {
                return MMDD.StringValue(LocalDateTime.now(), pattern);
            }

            public String StringValue(LocalDateTime localDateTime, String... pattern) {
                return pattern.length == 0 ? DateUtils.dateTimeFormatter(DateUtils.ZH_PATTERN_MONTH_DD, localDateTime) : DateUtils.dateTimeFormatter(pattern[0], localDateTime);
            }
        };

        private Format() {
        }

        public abstract long LongValue();

        public abstract long LongValue(LocalDateTime var1);

        public abstract String StringValue(String... var1);

        public abstract String StringValue(LocalDateTime var1, String... var2);
    }
}
