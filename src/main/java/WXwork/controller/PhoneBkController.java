
package WXwork.controller;

import WXwork.model.User;
import WXwork.service.PhoneBkService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PhoneBkController {

    public PhoneBkController() {
    }

    @Resource
    private PhoneBkService phonebookService;

    @RequestMapping({"/phonebook"})
    public ModelAndView getPhonebook(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("phone___" + mid);
            mav.setViewName("views/phonebook");
        }
        return mav;
    }

    @RequestMapping({"/getphuser"})
    @ResponseBody
    public List<User> getPhoneusers(@RequestParam("querystr") String querystr) {
        return this.phonebookService.getPhoneusers(querystr);
    }

    @RequestMapping({"/userindt"})
    @ResponseBody
    public User getUserindt(@RequestParam("querystr") String querystr) {
        return this.phonebookService.getUserindt(querystr);
    }

}
