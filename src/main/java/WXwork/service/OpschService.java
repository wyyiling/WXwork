package WXwork.service;

import WXwork.dao.OpschDao;
import WXwork.model.Opsch;
import WXwork.util.time_util;
import WXwork.util.wx_util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("OpschService")
public class OpschService {
    @Resource
    private OpschDao opscheduleDao;

    public OpschService() {
    }

    public List<Opsch> getOpsch(String mid) {
        String name = wx_util.EmpToName(mid);
        List<Opsch> opschedule = this.opscheduleDao.getOpschedule(name);

        for (Opsch sch : opschedule) {
            try {
                sch.setWeekday(time_util.getWeekOfDate(sch.getSch_dtime()));
                sch.setSupnurse(wx_util.EmpToName(sch.getSupnurse()));
                sch.setOpnurse(wx_util.EmpToName(sch.getOpnurse()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return opschedule;
    }
}
