
package TXmanage.util;

import java.text.SimpleDateFormat;


public class time_util {

    public static String tsToDate(Long ts) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(ts);
    }

}
