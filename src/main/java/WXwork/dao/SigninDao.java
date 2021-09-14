
package WXwork.dao;

import WXwork.util.dbpool.druid191;
import WXwork.model.Signin;
import WXwork.util.conn_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class SigninDao {

    public SigninDao() {
    }

    public List<Signin> getSignin(String mid) {

        List<Signin> list = new ArrayList<>();
        String sql = "SELECT *  FROM v_signrecord WHERE PersonnelID= ? AND RDateTime >= DATEADD(m,-2,getdate()) ORDER BY RDateTime desc";
        Connection conn = druid191.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            rs = ps.executeQuery();
            while (rs.next()) {
                Signin signin = new Signin();
                signin.setSign_time(rs.getString("RDateTime").substring(0, rs.getString("RDateTime").length() - 2));
                signin.setAddress(rs.getString("PlaceName"));
                list.add(signin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return list;
    }
}
