
package WXwork.controller;

import WXwork.model.Salary;
import WXwork.service.SalaryService;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SalaryController {

    public SalaryController() {
    }

    @Resource
    private SalaryService salaryService;

    @RequestMapping({"/salary"})
    public ModelAndView getSalary(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String mid = (String) session.getAttribute("mid");
        if (mid == null) {
            mav.setViewName("views/404");
        } else {
            System.out.println("salary___" + mid);
            JSONArray salary = this.salaryService.getsalary(mid);

            mav.addObject("salary", salary);
            mav.addObject("mid", mid);

            mav.setViewName("views/salary");
        }
        return mav;
    }

    @RequestMapping({"/getgz"})
    @ResponseBody
    public List<Salary> getdtSalary(@RequestBody JSONObject yms){

        return salaryService.getdtSalary(yms);
    }

}
