
package WXwork.controller;

import WXwork.model.Worksch;
import WXwork.service.WorkschService;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WorkschController {

    public WorkschController() {
    }

    @Resource
    private WorkschService workschService;

    @RequestMapping({"worksch"})
    public ModelAndView getWorksch(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("worksch___" + mid);
            List<Worksch> worksch = this.workschService.getWorksch(mid);
            mav.addObject("worksch", worksch);
            mav.setViewName("views/worksch");
        }
        return mav;
    }
}
