
package WXwork.controller;

import WXwork.model.Meeting;
import WXwork.model.Attender;
import WXwork.model.User;
import WXwork.service.MeetingService;
import WXwork.util.wx_util;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MeetingController {

    @Resource
    MeetingService meetingService;

    public MeetingController() {
    }
//进入主界面

    @RequestMapping({"/meeting"})
    public ModelAndView getMeeting(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("meeting___" + mid);
            String userid = wx_util.EmpToUID(mid);
            String name = wx_util.EmpToName(mid);
            int level = meetingService.checkAdmin(mid);
            Map<String, Object> map = new HashMap<>();
            map.put("level", level);
            map.put("name", name);
            map.put("userid", userid);
            map.put("mid", mid);
            mav.addObject("maper", map);

            mav.setViewName("views/meeting");
        }
        return mav;
    }

    /***
     * 创建新的meeting
     * @param newMt 提交的表单
     * @return 返回password，如失败返回空
     */
    @RequestMapping({"/meeting_submit"})
    @ResponseBody
    public String meeting_submit(@RequestBody JSONObject newMt) {
        return meetingService.submitat(newMt);
    }

    /***
     *  nav1显示已签到过的meeting
     * @param session 工号
     * @return meetingList
     */
    @RequestMapping({"/meeting_myAttendance"})
    @ResponseBody
    public List<Meeting> meeting_myAttendance(HttpSession session) {
        return meetingService.myattdce((String) session.getAttribute("mid"));
    }

    /***
     * 显示本人签到详情
     * @param info 签到详情
     * @return 签到时间
     */
    @RequestMapping({"/meeting_mySign"})
    @ResponseBody
    public String meeting_mySign(@RequestBody JSONObject info) {

        return meetingService.mySign(info);
    }

    /***
     *  显示自己创建的meeting
     * @param session 工号
     * @return meetingList
     */
    @RequestMapping({"/meeting_myMeeting"})
    @ResponseBody
    public List<Meeting> meeting_myMeeting(HttpSession session) {
        return meetingService.myMeeting((String) session.getAttribute("mid"));
    }

    /***
     * 查看meeting信息
     * @param id 实际为password
     * @return meeting对象
     */
    @RequestMapping({"/meeting_mtInfo"})
    @ResponseBody
    public Meeting meeting_mtInfo(@RequestParam("info") String id) {
        return meetingService.attendinfo(id);
    }

    /***
     * 查看签到人员
     * @param mtPassword password
     * @return AttenderList
     */
    @RequestMapping({"/meeting_attender"})
    @ResponseBody
    public List<Attender> meeting_attender(@RequestParam("password") String mtPassword) {
        return meetingService.attender(mtPassword);
    }

    /***
     * admin列表
     * @return UserList
     */
    @RequestMapping({"/meeting_adminList"})
    @ResponseBody
    public List<User> meeting_adminList() {
        return meetingService.adminList();
    }

    /***
     * 生成二维码
     * @param code 包含password,time
     * @return 加密过的字符串
     */
    @RequestMapping(value = {"/meeting_gnQr"}, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String meeting_gnQr(@RequestBody JSONObject code) {
        return meetingService.gnmtqrcode(code);
    }

    /***
     * 扫码
     * @param scan 身份信息
     * @return 1不在时间范围内 2成功 3重复
     */
    //produces 需要返回中文时用到
    @RequestMapping(value = {"/meeting_scan"}, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String meeting_scan(@RequestBody JSONObject scan) {
        Attender attender = new Attender();
        attender.setEmpcode(scan.getString("mid"));
        attender.setName(scan.getString("name"));
        attender.setSigndate(scan.getString("time"));
        attender.setMtpassword(scan.getString("mtpassword"));
        return meetingService.scan(attender);
    }

}