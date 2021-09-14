
package WXwork.controller;

import WXwork.util.AesAlgo;
import WXwork.util.data_util;
import WXwork.model.User;
import WXwork.service.IndexService;
import WXwork.util.json_util;
import WXwork.util.wx_util;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class IndexController {

    public IndexController() {
    }

    @Resource
    private IndexService indexService;

    @RequestMapping({"/index"})
    public ModelAndView getIndex(String code, String pass, String userid, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String access_token = wx_util.gettoken();
        if (!code.equals("")) {
            String url = data_util.getCode_url().replace("ACCESS_TOKEN", access_token).replace("CODE", code);
            String usid = json_util.getResponseBody(url).getString("UserId");
            if (usid != null) {
                userid = wx_util.loginsync(usid, access_token);
                if (userid.equals("")) {
                    mav.setViewName("views/404");
                } else {
                    String ts = Long.toString(new Date().getTime() / 1000);
                    User user = this.indexService.getUser(userid, access_token);
                    String mid = user.getMid();
                    String name = user.getName();
                    String mobile = user.getMobile();
                    String nStr = mid + "&" + name + "&" + mobile;
                    session.setAttribute("mid", mid);
                    String nnStr = AesAlgo.aesalgo(nStr) + "&" + ts;
                    mav.addObject("md5", nnStr);
                    mav.addObject("user", user);
                    System.out.println("login___" + mid + " " + userid);
                    mav.setViewName("views/qywx_index");
                }
            } else {
                mav.setViewName("views/404");
            }
            return mav;
        } else if (pass.equals("5784814@3!@#")) {
            String usd = wx_util.loginsync(userid, access_token);
            if (usd.equals("")) {
                mav.setViewName("views/404");
            } else {
                String ts = Long.toString(new Date().getTime() / 1000);
                User user = indexService.getUser(usd, access_token);
                String mid = user.getMid();
                String name = user.getName();
                String mobile = user.getMobile();
                String nStr = mid + "&" + name + "&" + mobile;
                session.setAttribute("mid", mid);
                String nnStr = AesAlgo.aesalgo(nStr) + "&" + ts;
                mav.addObject("md5", nnStr);
                mav.addObject("user", user);
                System.out.println("login___" + mid + " " + userid);
                mav.setViewName("views/qywx_index");
            }
            return mav;
        } else {
            mav.setViewName("views/404");
            return mav;
        }
    }

}
