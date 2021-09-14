
package WXwork.controller;

import WXwork.model.Opsch;
import WXwork.service.OpschService;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OpschController {

    public OpschController() {
    }

    @Resource
    OpschService opschService;

    @RequestMapping({"/opsch"})
    public ModelAndView getOpschedule(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("opt___" + mid);
            List<Opsch> opschedule = this.opschService.getOpsch(mid);
            mav.addObject("opschedule", opschedule);
            mav.setViewName("views/opschedule");
        }
        return mav;
    }
}
