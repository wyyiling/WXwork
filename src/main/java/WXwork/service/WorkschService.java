package WXwork.service;

import WXwork.dao.WorkschDao;
import WXwork.model.Worksch;
import WXwork.util.time_util;

import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("WorkschService")
public class WorkschService {

    public WorkschService() {
    }

    @Resource
    private WorkschDao workschDao;

    public List<Worksch> getWorksch(String mid) {
        List<Worksch> worksches = this.workschDao.getWorksch(mid);

        for (Worksch sch : worksches) {
            try {
                sch.setWeekday(time_util.getWeekOfDate(sch.getWkcalendar()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return worksches;
    }

}
