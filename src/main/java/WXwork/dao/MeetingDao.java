
package WXwork.dao;

import WXwork.model.Attender;
import WXwork.util.dbpool.druid243;
import WXwork.model.Meeting;
import WXwork.model.User;
import WXwork.util.conn_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class MeetingDao {

    public MeetingDao() {
    }

    public Integer checkAdmin(String mid) {
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "SELECT * FROM `admin` WHERE `empcode` = ?";

        int level = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            rs = ps.executeQuery();
            if (rs.next()) {
                level = 1;
                return level;
            }

        } catch (SQLException e) {
            return 0;
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return level;
    }

    public Integer checkatd(String password) {

        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int state = 0;
        String sql = "select * from attendance where password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                state = 1;
            }
            return state;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return state;
    }

    public  String submitat(JSONObject newMt) {
        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String success = "";
        String sql1 = "INSERT INTO `attendance` (`password`,`creemp`,`addr`,`starttime`,`endtime`,`title`) VALUES(?,?,?,?,?,?)";
        String sql2 = "SELECT * FROM `attendance` WHERE password = ?";

        try {
            ps = conn.prepareStatement(sql1);
            ps.setString(1, newMt.getString("password"));
            ps.setString(2, newMt.getString("creemp"));
            ps.setString(3, newMt.getString("addr"));
            ps.setString(4, newMt.getString("starttime"));
            ps.setString(5, newMt.getString("endtime"));
            ps.setString(6, newMt.getString("title"));
            ps.executeUpdate();
            ps = conn.prepareStatement(sql2);
            ps.setString(1, newMt.getString("password"));
            rs = ps.executeQuery();

            if (rs.next()) {
                success = rs.getString("password");
                return success;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return success;
    }

    public List<Meeting> myattdce(String mid) {
        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Meeting> list = new ArrayList<>();
        String sql = "SELECT a.title,a.addr,a.password,a.endtime,b.* FROM atdetail b JOIN attendance a ON a.password = b.mtpassword AND b.empcode = ? order by a.endtime desc";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            rs = ps.executeQuery();
            while (rs.next()) {
                Meeting meeting = new Meeting();
                meeting.setPassword(rs.getString("password"));
                meeting.setTitle(rs.getString("title"));
                meeting.setAddr(rs.getString("addr"));
                list.add(meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return list;
    }

    public String mySign(JSONObject info) {
        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rtime = "";
        String sql = "SELECT * FROM atdetail where mtpassword = ? and empcode = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, info.getString("signid"));
            ps.setString(2, info.getString("mid"));
            rs = ps.executeQuery();
            while (rs.next()) {
                rtime = rs.getString("signtime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return rtime;
    }

    public List<Meeting> myMeeting(String mid) {
        List<Meeting> list = new ArrayList<>();
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "SELECT * FROM `attendance` WHERE `creemp` = ? order by endtime desc";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mid);
            rs = ps.executeQuery();

            while (rs.next()) {
                Meeting attd = new Meeting();
                attd.setPassword(rs.getString("password"));
                attd.setCreemp(mid);
                attd.setStarttime(rs.getString("starttime"));
                attd.setEndtime(rs.getString("endtime"));
                attd.setTitle(rs.getString("title"));
                attd.setAddr(rs.getString("addr"));
                list.add(attd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return list;
    }

    public Meeting attendinfo(String password) {
        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Meeting attd = new Meeting();

        String sql = "SELECT * FROM attendance WHERE password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                attd.setPassword(rs.getString("password"));
                attd.setTitle(rs.getString("title"));
                attd.setStarttime(rs.getString("starttime"));
                attd.setEndtime(rs.getString("endtime"));
                attd.setCreemp(rs.getString("creemp"));
                attd.setAddr(rs.getString("addr"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return attd;
    }

    public List<Attender> attender(String mtpassword) {
        List<Attender> list = new ArrayList<>();
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "SELECT * FROM `atdetail` WHERE `mtpassword` = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mtpassword);
            rs = ps.executeQuery();
            while (rs.next()) {
                Attender attder = new Attender();
                attder.setMtpassword(mtpassword);
                attder.setSigndate(rs.getString("signtime"));
                attder.setName(rs.getString("empname"));
                attder.setEmpcode(rs.getString("empcode"));
                list.add(attder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return list;
    }

    public List<User> adminList() {
        List<User> list = new ArrayList<>();
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "SELECT * FROM `admin`";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setMid(rs.getString("empcode"));
                user.setName(rs.getString("empname"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return list;
    }

    public Integer gnmtqrcode(String mtpass) {
        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int state = 0;
        String sql = "SELECT * FROM attendance WHERE password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mtpass);
            rs = ps.executeQuery();
            if (rs.next()) {
                state = 1;
                return state;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return state;
    }

    public Integer scan(Attender attender) {
        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int state = 0;
        String time = attender.getSigndate();
        String mid = attender.getEmpcode();
        String name = attender.getName();
        String mtpassword = attender.getMtpassword();

        String sql1 = "SELECT * FROM `attendance` WHERE `password` =?";
        String sql2 = "INSERT INTO `atdetail` (`empcode`,`signtime`,`mtpassword`,`empname`) values(?,?,?,?)";
        String sql3 = "SELECT * FROM `atdetail` WHERE `empcode` =? AND `mtpassword` = ?";

        try {
            ps = conn.prepareStatement(sql3);
            ps.setString(1, mid);
            ps.setString(2, attender.getMtpassword());
            rs = ps.executeQuery();
            if (rs.next()) {
                state = 3;
                //重复
                return state;
            }
            ps = conn.prepareStatement(sql1);
            ps.setString(1, mtpassword);
            rs = ps.executeQuery();
            if (rs.next()) {
                String st = rs.getString("starttime");
                String et = rs.getString("endtime");
                if (time.compareTo(st) <= 0 || time.compareTo(et) >= 0) {
                    state = 1;
                    //不再时间范围内
                    return state;
                }

                ps = conn.prepareStatement(sql2);
                ps.setString(1, mid);
                ps.setString(2, time);
                ps.setString(3, mtpassword);
                ps.setString(4, name);
                ps.executeUpdate();
                //成功
                state = 2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return state;
    }

}