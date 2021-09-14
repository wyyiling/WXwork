package WXwork.service;

import WXwork.model.User;

import WXwork.dao.IndexDao;
import WXwork.util.wx_util;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("IndexService")
public class IndexService {
    @Resource
    private IndexDao IndexDao;

    public IndexService() {
    }

    public User getUser(String userid, String access_token) {
        User user = this.IndexDao.getUser(userid);
        JSONObject us = wx_util.getUserInfo(access_token, userid);
        user.setTximg(us.getString("avatar"));
        return user;
    }
}
