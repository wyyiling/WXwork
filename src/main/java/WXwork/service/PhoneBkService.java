
package WXwork.service;

import WXwork.dao.PhoneBkDao;
import WXwork.model.User;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("/PhoneBkService")
public class PhoneBkService {
    @Resource
    private PhoneBkDao phoneBkDao;

    public PhoneBkService() {
    }

    public List<User> getPhoneusers(String querystr) {
        return this.phoneBkDao.getPhoneusers(querystr);
    }

    public User getUserindt(String querystr) {
        return this.phoneBkDao.getUserindt(querystr);
    }
}
