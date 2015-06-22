package io.gitcafe.zhanjiashu.newzhihudialy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jiashu on 2015/6/22.
 */
public class DateUtil {

    public static final long MILLISECONDS_ONE_DAY = 1000 * 60 * 60 * 24;

    public static String getBeforeDate(Date date) {
       return getBeforeDate(date.getTime());
    }

    public static String getBeforeDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return format.format(getOffsetDate(date, 1));
    }

    public static Date getOffsetDate(long milliseconds,  int offset) {
        return new Date(milliseconds + MILLISECONDS_ONE_DAY * offset);
    }
}
