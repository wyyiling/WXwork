
package WXwork.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class time_util {

    public static String getWeekOfDate(String date) throws ParseException {
        SimpleDateFormat sdf;
        if (date.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date datey = sdf.parse(date);
        String[] weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(datey);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static String getWeekOfDate(int i) {
        String[] weekDays = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return weekDays[i - 1];
    }

}
