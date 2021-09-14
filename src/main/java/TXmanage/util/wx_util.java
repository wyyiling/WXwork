package TXmanage.util;

import TXmanage.util.dbpool.druid175;
import TXmanage.util.dbpool.druid243;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class wx_util {

    public static String gettxtoken() {
        Connection conn = druid243.getConnection();
        String token = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String sql = "SELECT txtoken FROM `access_token` ORDER BY id DESC limit 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            token = rs.getString("txtoken");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return token;


    }

    public static JSONObject getUserInfo(String access_token, String userid) {
        return json_util.getResponseBody(data_util.getInfo_url().replace("ACCESS_TOKEN", access_token).replace("USERID", userid));
    }

    public static JSONObject getAllUser(String access_token) {
        String url = data_util.getAll_url().replace("ACCESS_TOKEN", access_token).replace("DEPARTMENT_ID", "1").replace("FETCH_CHILD", "1");
        return json_util.getResponseBody(url);
    }

    public static String UIDtoEmp(String userid) {
        Connection conn = druid243.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql1 = "SELECT * FROM `userinfo` WHERE `user_id` = ?";
        String sql2 = "SELECT * FROM `userinfo_copy` WHERE `user_id` = ?";

        String state;
        try {
            ps = conn.prepareStatement(sql1);
            ps.setString(1, userid);
            rs = ps.executeQuery();
            if (!rs.next()) {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, userid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    return "";
                }
            }

            state = rs.getString("empcode");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return state;
    }

    public static void syncuser(Connection conn, String access_token, JSONObject user) {

        String mobile = user.getString("mobile");
        String userid = user.getString("userid");
        String alias = user.getString("alias");
        String name = user.getString("name");
        String dept = user.getString("department");
        String job = user.getString("position");
        String update_url = data_util.getUpdate_url().replace("ACCESS_TOKEN", access_token);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (mobile != null && !mobile.equals("")) {
                String sql = "SELECT * FROM v_baseInfo WHERE Mobile = ?";
                String jso;
                JSONObject res;

                ps = conn.prepareStatement(sql);
                ps.setString(1, mobile);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    if (dept.equals("[10010]") && !alias.equals("信息中心第三方人员")) {
                        user.put("alias", "信息中心第三方人员");
                        jso = JSONObject.toJSONString(user);
                        json_util.postResponseBody(update_url, jso);

                    } else if (dept.equals("[10012]") && !alias.equals("东院体检中心")) {
                        user.put("alias", "东院体检中心");
                        jso = JSONObject.toJSONString(user);
                        json_util.postResponseBody(update_url, jso);

                    } else if (!dept.equals("[10012]") && !dept.equals("[10010]") && !alias.equals("限制分组")) {
                        jso = "{\"userid\":\"" + userid + "\",\"alias\":\"限制分组\",\"department\":[10011]}";
                        json_util.postResponseBody(update_url, jso);

                    }

                } else if (!alias.equals(rs.getString("EmpCode")) ||
                        !dept.contains("[" + rs.getString("OrganizeID") + "]") ||
                        !job.equals(rs.getString("jobname")) ||
                        !name.equals(rs.getString("Empname"))) {

                    System.out.println(("科室ID:  " + rs.getString("OrganizeID")));
                    jso = "{\"userid\":\"" + userid + "\",\"name\":\"" + rs.getString("EmpName") + "\",\"alias\":\"" + rs.getString("EmpCode") + "\",\"department\":[" + rs.getString("OrganizeID") + "],\"position\":\"" + rs.getString("JobName") + "\"}";
                    res = json_util.postResponseBody(update_url, jso);
                    if (!res.getString("errcode").equals("0")) {
                        System.out.println(res.getString("errmsg"));
                        jso = "{\"userid\":\"" + userid + "\",\"alias\":\"" + rs.getString("EmpCode") + "\",\"department\":[10011]}";
                        json_util.postResponseBody(update_url, jso);
                    }
                }
            } else {
                alias = "{\"userid\":\"" + userid + "\",\"alias\":\"限制分组\",\"department\":[10011]}";
                json_util.postResponseBody(update_url, alias);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(null, rs, ps);
        }

    }

    public static void checkuser(JSONObject user, String txtoken) {

        Connection conn = druid243.getConnection();
        Connection conn2 = druid175.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String sql1 = "SELECT * FROM userinfo WHERE empcode = ?";
        String sql3 = "SELECT * FROM v_baseinfo where mobile = ?";
        String userid = user.getString("userid");
        String mobile = user.getString("mobile");

        if (!(mobile == null)) {

            if (!mobile.equals("")) {
                try {
                    ps = conn2.prepareStatement(sql3);
                    ps.setString(1, mobile);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        String emp = rs.getString("EmpCode");
                        String department = rs.getString("OrganizeName");
                        String depid = rs.getString("OrganizeID");
                        String name = rs.getString("EmpName");
                        System.out.println(emp);
                        ps = conn.prepareStatement(sql1);
                        ps.setString(1, emp);
                        rs2 = ps.executeQuery();
                        if (rs2.next()) {
                            String sql2 = "UPDATE userinfo SET user_id = ?,mobile =?,department=?,depid=?,empname= ? WHERE empcode = ?";

                            ps = conn.prepareStatement(sql2);
                            ps.setString(1, userid);
                            ps.setString(2, mobile);
                            ps.setString(3, department);
                            ps.setString(4, depid);
                            ps.setString(5, name);
                            ps.setString(6, emp);

                        } else {
                            String sql4 = "INSERT INTO `userinfo` (`empcode`,`empname`,`department`,`mobile`,`job`,`depid`,`user_id`) VALUES(?,?,?,?,?,?,?)";

                            ps = conn.prepareStatement(sql4);
                            ps.setString(1, rs.getString("EmpCode"));
                            ps.setString(2, rs.getString("EmpName"));
                            ps.setString(3, rs.getString("OrganizeName"));
                            ps.setString(4, rs.getString("Mobile"));
                            ps.setString(5, rs.getString("JobName"));
                            ps.setInt(6, rs.getInt("OrganizeID"));
                            ps.setString(7, userid);
                        }
                        ps.executeUpdate();
                    } else {
                        System.out.println("无此号码 " + userid);
                    }

                    syncuser(conn2, txtoken, user);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    conn_util.closeall(conn, rs, ps);
                    conn_util.closeall(conn2, rs2, null);
                }
            } else {
                System.out.println("号码为空！ " + userid);
            }
        } else {
            System.out.println("获取出错！ " + userid);
        }

    }

    public static void syncdaka(Connection conn191, String access_token, Long per, String list) {

        Date date = new Date();
        long ts = date.getTime() / 100000L * 100L;
        String stt = String.valueOf(ts - per);
        String edt = String.valueOf(ts);
        PreparedStatement ps = null;
        String sql2 = "INSERT INTO Con_RecordAPP(PersonnelID, CardSerial, RDateTime,PlaceName) VALUES (?, ?, ?, ?)";

        String data = "{\"opencheckindatatype\": 1,\"starttime\": STT,\"endtime\": EDT,\"useridlist\": [LIST]}".replace("STT", stt).replace("EDT", edt).replace("LIST", list);
        JSONArray checkindata = JSON.parseArray(json_util.postResponseBody(data_util.getDaka_url().replace("ACCESS_TOKEN", access_token), data).getString("checkindata"));
        try {
            for (int i = 0; i < checkindata.size(); ++i) {
                String checkin_time = checkindata.getJSONObject(i).getString("checkin_time");
                String dt = time_util.tsToDate(Long.parseLong(checkin_time) * 1000L);
                String location = checkindata.getJSONObject(i).getString("location_detail");
                String userid = checkindata.getJSONObject(i).getString("userid");

                String empcode = UIDtoEmp(userid);
                if (checkindata.getJSONObject(i).getString("exception_type").equals("") &&
                        !checkin_time.equals("") && !empcode.equals("")) {

                    ps = conn191.prepareStatement(sql2);
                    ps.setString(1, empcode);
                    ps.setString(2, "");
                    ps.setString(3, dt);
                    ps.setString(4, location);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(null, null, ps);
        }
    }

//    private static void syncallusid() {
//    String txtoken = wx_util.gettxtoken();
//        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD".replace("ACCESS_TOKEN", ac).replace("DEPARTMENT_ID", "1").replace("FETCH_CHILD", "1");
//JSONArray jsonArray = JSONObject.parseArray(wx_util.getAllUser(txtoken).getString("userlist"));
//        Connection conn = druid243wxwork.getConnection();
//        PreparedStatement ps = null;
//        String sql = "update userinfo set user_id = ? where mobile =?";
//        try {
//            for (int i = 0; i < JA.size(); i++) {
//
//                String mob = JA.getJSONObject(i).getString("mobile");
//                String us = JA.getJSONObject(i).getString("userid");
//                ps = conn.prepareStatement(sql);
//                ps.setString(1, us);
//                ps.setString(2, mob);
//                ps.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            conn_util.closeall(conn, null, ps);
//        }
//    }
}
