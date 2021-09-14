package WXwork.controller;

import WXwork.model.MZsch;
import WXwork.model.Worksch;
import WXwork.service.MZschService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MZschController {

    public MZschController() {

    }

    @Resource
    private MZschService mZschService;

    @RequestMapping({"/mzsch"})
    public ModelAndView getmzsch(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("mzsch___" + mid);
            List<MZsch> mzsches = this.mZschService.getMZsch(mid);
            mav.addObject("mzsches", mzsches);
            mav.setViewName("views/mzsch");
        }
        return mav;
    }


}
