package WXwork.service;

import WXwork.dao.MZschDao;
import WXwork.model.MZsch;
import WXwork.util.time_util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service("/MZschService")
public class MZschService {

    public MZschService() {

    }

    @Resource
    private MZschDao mZschDao;


    public List<MZsch> getMZsch(String mid) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());

        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(c.getTime());

        List<MZsch> mZsches = this.mZschDao.getMZsch(mid, first, last);

        for (MZsch mZsch:mZsches) {
            mZsch.setWeekday(time_util.getWeekOfDate(Integer.parseInt(mZsch.getWeekday())));
        }
        return mZsches;
    }

}
