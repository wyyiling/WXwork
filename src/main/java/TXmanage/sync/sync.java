
package TXmanage.sync;

import TXmanage.util.dbpool.druid175;
import TXmanage.util.dbpool.druid191;
import TXmanage.util.dbpool.druid243;
import TXmanage.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class sync {

    static void syncToken() {

        Connection conn = druid243.getConnection();
        PreparedStatement ps = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String access_token = data_util.getAccess_token(data_util.getZg_sec());
        String txtoken = data_util.getAccess_token(data_util.getTx_sec());
        String ticket_url = data_util.getTicket_url().replace("ACCESS_TOKEN", access_token);
        String ticket = json_util.getResponseBody(ticket_url).getString("ticket");
        String date = sdf.format(new Date());
        String sql = "INSERT INTO `access_token`(`access_token`,`txtoken`,`time`,`ticket`) values(?,?,?,?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, access_token);
            ps.setString(2, txtoken);
            ps.setString(3, date);
            ps.setString(4, ticket);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, null, ps);
        }

    }

    static void syncGroup() {

        Connection conn = druid243.getConnection();
        Connection conn2 = druid175.getConnection();
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        String sql1 = "SELECT * FROM v_baseInfo where empcode =?";
        String sql2 = "SELECT empcode,mobile FROM userinfo";
        String sql3 = "DELETE FROM userinfo WHERE empcode = ?";
        String sql4 = "UPDATE userinfo SET mobile = ? ,job = ? , department = ? ,depid = ? WHERE empcode = ?";
        try {
            ps = conn.prepareStatement(sql2);
            rs = ps.executeQuery();
            ps = conn2.prepareStatement(sql1);
            while (rs.next()) {
                String emp = rs.getString("empcode");
                ps.setString(1, emp);
                rs2 = ps.executeQuery();
                if (!rs2.next()) {
                    ps2 = conn.prepareStatement(sql3);
                    ps2.setString(1, emp);
                } else {
                    ps2 = conn.prepareStatement(sql4);
                    ps2.setString(1, rs2.getString("mobile"));
                    ps2.setString(2, rs2.getString("jobname"));
                    ps2.setString(3, rs2.getString("organizename"));
                    ps2.setString(4, rs2.getString("organizeID"));
                    ps2.setString(5, emp);
                }
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
            conn_util.closeall(conn2, rs2, ps2);
        }

    }

    static void syncAlias() {

        String txtoken = wx_util.gettxtoken();
        JSONArray jsonArray = JSONObject.parseArray(wx_util.getAllUser(txtoken).getString("userlist"));
        Connection conn = druid175.getConnection();
        for (Object o : jsonArray) {
            wx_util.syncuser(conn, txtoken, (JSONObject) o);
        }
        conn_util.closeall(conn, null, null);
        System.out.println("________done________");
    }

    static void syncDaka() {

        Date date = new Date();
        long per = 600L;
        long ts = date.getTime() / 100000L * 100L;

        System.out.println("开始时间____" + time_util.tsToDate((ts - per) * 1000L));
        System.out.println("结束时间____" + time_util.tsToDate(ts * 1000L));

        String access_token = data_util.getAccess_token(data_util.getDaka_sec());
        JSONArray jsonArray = JSONObject.parseArray(json_util.getResponseBody(data_util.getTag_url().replace("ACCESS_TOKEN", access_token).replace("TAGID", "1")).getString("userlist"));
        StringBuilder qsb = new StringBuilder();
        Connection conn191 = null;

        for (int i = 0; i < jsonArray.size(); i += 100) {
            StringBuilder sb = new StringBuilder();

            if (conn191 == null) {
                conn191 = druid191.getConnection();
            }
            for (int j = i; j < i + 100; ++j) {
                if (j < jsonArray.size()) {
                    sb.append("\"").append(jsonArray.getJSONObject(j).getString("userid")).append("\",");
                }
                qsb.append(sb);
                sb.delete(0, sb.length());
            }

            String sbstr = qsb.toString();
            String list = sbstr.substring(0, sbstr.length() - 1);

            wx_util.syncdaka(conn191, access_token, per, list);
            qsb.delete(0, qsb.length());
        }
        conn_util.closeall(conn191, null, null);
        System.out.println("___完成___");
    }

    static void syncdep() {

        Connection conn = druid175.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from PriOrganizes where PARENTID IS NOT NULL";
        String access_token = wx_util.gettxtoken();
        String url = data_util.getDeplist_url().replace("ACCESS_TOKEN", access_token);
        JSONArray ja = json_util.getResponseBody(url).getJSONArray("department");
        ArrayList<String> li = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            li.add(ja.getJSONObject(i).getString("id"));
        }
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                String parentid = rs.getString("ParentID");
                String update = "{\n" +
                        "   \"id\": " + id + ",\n" +
                        "   \"name\": \"" + name + "\",\n" +
                        "   \"parentid\": " + parentid + "\n" +
                        "}";
                if (li.contains(rs.getString("id")) && rs.getInt("status") == 1) {
                    JSONObject jl = json_util.postResponseBody(data_util.getUpdep_url().replace("ACCESS_TOKEN", access_token), update);
                    System.out.println(id + "  " + jl.getString("errcode"));
                } else if (li.contains(rs.getString("id")) && rs.getInt("status") == 0) {
                    JSONObject jl = json_util.getResponseBody(data_util.getDeldep_url().replace("ACCESS_TOKEN", access_token));
                    if (jl.getString("errcode").equals("0")) {
                        System.out.println(id + "  " + name + "部门已删除");
                    }

                } else if (!li.contains(rs.getString("id")) && rs.getInt("status") == 1) {
                    JSONObject jl = json_util.postResponseBody(data_util.getAdddep_url().replace("ACCESS_TOKEN", access_token), update);
                    if (jl.getString("errcode").equals("0")) {
                        System.out.println(id + "  " + name + "部门已添加");
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

    }

}
