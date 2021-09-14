package WXwork.service;

import WXwork.dao.SigninDao;
import WXwork.model.Signin;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("SigninService")
public class SigninService {

    public SigninService() {
    }

    @Resource
    private SigninDao signinDao;

    public List<Signin> getsignin(String mid) {
        return this.signinDao.getSignin(mid);
    }
}
