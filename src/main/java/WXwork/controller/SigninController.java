
package WXwork.controller;

import WXwork.model.Signin;
import WXwork.service.SigninService;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SigninController {

    public SigninController() {
    }

    @Resource
    private SigninService signinService;

    @RequestMapping({"/signining"})
    public ModelAndView getSignin(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("sign___" + mid);
            List<Signin> signin = this.signinService.getsignin(mid);
            mav.addObject("signin", signin);
            mav.setViewName("views/signin");
        }
        return mav;
    }
}
