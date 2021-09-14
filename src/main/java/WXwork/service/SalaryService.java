
package WXwork.service;

import WXwork.dao.SalaryDao;

import javax.annotation.Resource;

import WXwork.model.Salary;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SalaryService")
public class SalaryService {
    @Resource
    private SalaryDao salaryDao;

    public SalaryService() {
    }

    public JSONArray getsalary(String mid) {
        return this.salaryDao.getsalary(mid);
    }

    public List<Salary> getdtSalary(JSONObject yms){
        return this.salaryDao.getdtSalary(yms);
    }


}
