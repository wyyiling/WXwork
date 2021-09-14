package WXwork.dao;

import WXwork.model.MZsch;
import WXwork.util.conn_util;
import WXwork.util.dbpool.druid9145;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MZschDao {

    public MZschDao() {

    }

    public List<MZsch> getMZsch(String mid, String first, String last) {
        List<MZsch> list = new ArrayList<>();
        Connection conn = druid9145.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select a.scheduleid,to_char(a.see_date,'yyyy-mm-dd') as see_date,a.week,a.noon_code,b.name as noon_name,d.branch_code,a.dept_code,a.dept_name\n" +
                ",case when regexp_like(a.doct_code,'^[0-9]+$') then substr('0000'||a.doct_code,-4,4) else replace(ltrim(replace(a.doct_code,'0',' ')),' ','0') end as doct_code\n" +
                ",a.doct_name,a.reglevl_code,a.reglevl_name\n" +
                "from fin_opr_schema a,com_dictionary b,dawn_org_dept d\n" +
                "where a.schema_type='1'\n" +
                "and a.valid_flag='1'\n" +
                "and a.see_date>=to_date('STT','yyyy-mm-dd') \n".replace("STT", first) +
                "and a.see_date<=to_date('EDT','yyyy-mm-dd')\n".replace("EDT", last) +
                "and a.scheduleid is not null\n" +
                "and a.noon_code=b.code\n" +
                "and b.type='NOON'\n" +
                "and a.dept_code=d.dept_id\n" +
                "and a.doct_code = 'MID' \n".replace("MID", "00"+mid) +
                "order by d.branch_code,a.see_date,d.dept_id,a.noon_code";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MZsch mZsch = new MZsch();
                mZsch.setDate(rs.getString("SEE_DATE"));
                mZsch.setDepname(rs.getString("DEPT_NAME"));
                mZsch.setNoon(rs.getString("NOON_NAME"));
                mZsch.setWeekday(rs.getString("WEEK"));
                list.add(mZsch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return list;

    }


}
