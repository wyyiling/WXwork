package WXwork.util;

import WXwork.util.dbpool.druid243;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;

public class wx_util {

    public static String gettoken() {
        Connection conn = druid243.getConnection();
        String token = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String sql = "SELECT * FROM `access_token` ORDER BY id DESC limit 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            token = rs.getString("access_token");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return token;
    }

    public static String getticket() {
        String ticket = "";
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM `access_token` ORDER BY `id` DESC limit 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            ticket = rs.getString("ticket");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return ticket;
    }

    public static JSONObject getUserInfo(String access_token, String userid) {
        return json_util.getResponseBody(data_util.getInfo_url().replace("ACCESS_TOKEN", access_token).replace("USERID", userid));
    }

    public static String loginsync(String userid, String access_token) {
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String sql1 = "SELECT * FROM `userinfo` WHERE `mobile` = ?";
            String mobile = getUserInfo(access_token, userid).getString("mobile");
            if (mobile == null || mobile.equals("")) {
                return "";
            } else {
                ps = conn.prepareStatement(sql1);
                ps.setString(1, mobile);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    return "";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return userid;
    }

    public static String EmpToUID(String mid) {
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql1 = "SELECT * FROM  `userinfo` WHERE `empcode` = ?";
        String sql2 = "SELECT * FROM  `userinfo_copy` WHERE `empcode` = ?";

        try {
            ps = conn.prepareStatement(sql1);
            ps.setString(1, mid);
            rs = ps.executeQuery();
            String userid;
            if (!rs.next()) {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, mid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    return "";
                }
            }

            userid = rs.getString("user_id");
            return userid;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return "";
    }

    public static String EmpToName(String mid) {
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql1 = "SELECT * FROM userinfo WHERE empcode = ?";
        String sql2 = "SELECT * FROM userinfo_copy WHERE empcode = ?";

        try {
            ps = conn.prepareStatement(sql1);
            ps.setString(1, mid);
            rs = ps.executeQuery();
            String state;
            if (!rs.next()) {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, mid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    state = "";
                    return state;
                }
            }

            state = rs.getString("empname");
            return state;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return "";
    }

}