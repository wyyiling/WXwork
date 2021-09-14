
package WXwork.service;

import WXwork.dao.MeetingDao;
import WXwork.model.Meeting;
import WXwork.model.Attender;
import WXwork.model.User;
import WXwork.util.data_util;
import com.alibaba.fastjson.JSONObject;

import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

import java.sql.Timestamp;
import java.util.*;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("/MeetingService")
public class MeetingService {

    @Resource
    private MeetingDao meetingDao;

    public MeetingService() {

    }

    private String getpass() {

        String allchar = data_util.getAll_char();
        String str1 = allchar.charAt((int) (Math.random() * allchar.length())) + "";
        String str2 = allchar.charAt((int) (Math.random() * allchar.length())) + "";
        String str3 = allchar.charAt((int) (Math.random() * allchar.length())) + "";
        String str4 = allchar.charAt((int) (Math.random() * allchar.length())) + "";
        String str5 = allchar.charAt((int) (Math.random() * allchar.length())) + "";

        return (str1 + str2 + str3 + str4 + str5);
    }

    public Integer checkAdmin(String mid) {
        return this.meetingDao.checkAdmin(mid);
    }

    public String submitat(JSONObject newMt) {
        String password = this.getpass();
        while (meetingDao.checkatd(password) == 1) {
            password = this.getpass();
        }
        newMt.put("password", password);
        return meetingDao.submitat(newMt);
    }

    public List<Meeting> myattdce(String mid) {
        return meetingDao.myattdce(mid);
    }

    public String mySign(JSONObject info) {
        return meetingDao.mySign(info);
    }

    public List<Meeting> myMeeting(String mid) {
        return this.meetingDao.myMeeting(mid);
    }

    public Meeting attendinfo(String id) {

        return meetingDao.attendinfo(id);
    }

    public List<Attender> attender(String mtpassword) {
        List<Attender> list = meetingDao.attender(mtpassword);
        if (list.size() == 0) {
            Attender attend = new Attender();
            attend.setMtpassword("error");
            list.add(attend);
        }
        return list;
    }

    public List<User> adminList() {
        return this.meetingDao.adminList();
    }

    public String gnmtqrcode(JSONObject code) {
        String oricode = code.getString("meetpassword");
        if (meetingDao.gnmtqrcode(oricode) == 0) {
            return "error,错误的编号!";
        }
        String ts = Timestamp.valueOf(code.getString("time")).toString();
        String codein = ts + data_util.getJS_code() + code.getString("meetpassword");
        Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(codein.getBytes()));

    }

    public String scan(Attender attender) {
        String ret;
        Decoder decoder = Base64.getDecoder();

        String codeout = new String(decoder.decode(attender.getMtpassword().getBytes()));

        try {
            String mtpass = codeout.split(data_util.getJS_code())[1];
            attender.setMtpassword(mtpass);

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        switch (meetingDao.scan(attender)) {
            case 0:
                ret = "发生数据错误，签到失败!";
                break;
            case 1:
                ret = "不在可签到时间!";
                break;
            case 2:
                ret = "签到成功!";
                break;
            case 3:
                ret = "不可重复签到!";
                break;
            case 4:
                ret = "无法识别的二维码!";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + meetingDao.scan(attender));

        }
        return ret;
    }

}
