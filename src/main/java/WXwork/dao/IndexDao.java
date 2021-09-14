
package WXwork.dao;

import WXwork.util.dbpool.druid243;
import WXwork.model.User;
import WXwork.util.conn_util;

import java.sql.*;

import org.springframework.stereotype.Repository;

@Repository
public class IndexDao {

    public IndexDao() {
    }

    public User getUser(String userid) {
        User user = new User();
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String sql = "SELECT * FROM `userinfo` WHERE `user_id` = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userid);
            rs = ps.executeQuery();

            while (rs.next()) {
                user.setUserid(rs.getString("user_id"));
                user.setMid(rs.getString("empcode"));
                user.setDept(rs.getString("department"));
                user.setJob(rs.getString("job"));
                user.setMobile(rs.getString("mobile"));
                user.setName(rs.getString("empname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return user;
    }


}
