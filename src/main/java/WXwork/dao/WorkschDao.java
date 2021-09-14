
package WXwork.dao;

import WXwork.util.dbpool.druid155;
import WXwork.model.Worksch;
import WXwork.util.conn_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class WorkschDao {

    public WorkschDao() {
    }

    public List<Worksch> getWorksch(String mid) {
        List<Worksch> list = new ArrayList<>();
        Connection conn = druid155.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "SELECT * FROM v_zqshfit WHERE empno = ? and convert(datetime,calendar) >= getdate() ORDER BY calendar";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            rs = ps.executeQuery();

            while (rs.next()) {
                Worksch worksch = new Worksch();
                worksch.setWkcalendar(rs.getString("calendar"));
                worksch.setShift(rs.getString("shfit"));
                list.add(worksch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return list;
    }
}
