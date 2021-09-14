
package WXwork.dao;

import WXwork.util.dbpool.druid243;
import WXwork.model.User;
import WXwork.util.conn_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PhoneBkDao {

    public PhoneBkDao() {
    }

    public List<User> getPhoneusers(String querystr) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT `empcode`,`empname`,`mobile`,`department`,IFNULL(job,'') AS job,  `department` FROM userinfo WHERE (`empname` LIKE ? OR `Mobile` LIKE ? OR `department` LIKE ? ) ORDER BY `empcode`";
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + querystr + "%");
            ps.setString(2, "%" + querystr + "%");
            ps.setString(3, "%" + querystr + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setMid(rs.getString("empcode"));
                user.setName(rs.getString("empname"));
                user.setMobile(rs.getString("mobile"));
                user.setDept(rs.getString("department"));
                user.setJob(rs.getString("job"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return list;
    }

    public User getUserindt(String querystr) {

        String sql = "SELECT `empcode`,`empname`,`mobile`,`department`,IFNULL(job,'') AS job,`department` FROM `userinfo` WHERE `empcode` = ?";
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = new User();

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, querystr);
            rs = ps.executeQuery();

            while (rs.next()) {
                user.setMid(rs.getString("empcode"));
                user.setName(rs.getString("empname"));
                user.setMobile(rs.getString("mobile"));
                user.setDept(rs.getString("department"));
                user.setJob(rs.getString("job"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return user;

    }
}
