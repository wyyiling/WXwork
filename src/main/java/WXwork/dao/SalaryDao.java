
package WXwork.dao;

import WXwork.model.Salary;
import WXwork.util.conn_util;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import WXwork.util.dbpool.druid155;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class SalaryDao {

    public SalaryDao() {
    }

    //1001X1100000000R1HOV
    String sql2 = "select top 12 * from jk_v_saldetail_xt a where a.empcode = ? and a.pk_wa_class = '1001X1100000000WYF8N' and cyear = ? and cperiod= ? order by cyear desc,cperiod desc";

    public JSONArray getsalary(String mid) {
        List<JSONArray> list = new ArrayList<>();
        String sql = "select top 12 * from jk_v_saldetail_xt a where a.empcode = ? and a.pk_wa_class = '1001X1100000000R1HOV' and datediff(mm,[cyear]+'-'+[cperiod]+'-01',getdate())  >0 order by cyear desc, cperiod desc";

        Connection conn = druid155.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        JSONArray ja = new JSONArray();

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("year", rs.getString("cyear"));
                jo.put("month", rs.getString("cperiod"));

                ja.add(jo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return ja;
    }


    public List<Salary> getdtSalary(JSONObject yms) {
        List<Salary> list = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Connection conn = druid155.getConnection();
        Connection conn2 = druid155.getConnection();
        String year = yms.getString("year");
        String month = yms.getString("month");
        String mid = yms.getString("mid");
        String state = "";
        if (yms.getString("state").equals("gz")) {
            state = "1001X1100000000R1HOV";
        } else if (yms.getString("state").equals("jx")) {
            state = "1001X1100000000WYF8N";
        }
        String sql = "select top 12 * from jk_v_saldetail_xt a where a.empcode = ? and a.pk_wa_class = ? and cyear =? and cperiod = ? and datediff(mm,[cyear]+'-'+[cperiod]+'-01',getdate())  >0";

        String sql2 = "select * from jk_v_salitem_xt a where a.cyear=? and a.cperiod=? and a.pk_wa_class=? and itemkey not in ('c_5','c_6','c_7','c_9','c_10','f_4','f_58','f_91','f_100','f_101','f_147','f_150','c_12','f_4','f_58')order by idisplayseq";
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            ps.setString(2, state);
            ps.setString(3, year);
            ps.setString(4, month);
            rs = ps.executeQuery();
            rs.next();
            ps2 = conn2.prepareStatement(sql2);
            ps2.setString(1, year);
            ps2.setString(2, month);
            ps2.setString(3, state);
            rs2 = ps2.executeQuery();
            while (rs2.next()) {
                Salary salary = new Salary();
                salary.setYear(year);
                salary.setMonth(month);
                salary.setType(state);
                String itemkey = rs2.getString("itemkey");
                salary.setItemkey(itemkey);
                salary.setName(rs2.getString("name"));
                if (itemkey.contains("f")) {
                    salary.setMoney(decimalFormat.format(rs.getFloat(itemkey)));
                } else {
                    String money = rs.getString(itemkey);
                    if (money == null) {
                            salary.setMoney("");
                    } else {
                        salary.setMoney(money);
                    }
                }

                list.add(salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
            conn_util.closeall(conn2, rs2, ps2);
        }

        return list;
    }


}