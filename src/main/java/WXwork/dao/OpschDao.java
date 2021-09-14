
package WXwork.dao;

import WXwork.util.dbpool.druid133;
import WXwork.model.Opsch;
import WXwork.util.conn_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class OpschDao {

    public OpschDao() {
    }

    public List<Opsch> getOpschedule(String name) {
        List<Opsch> list = new ArrayList<>();
        String sql = "SELECT a.SCHEDULED_DATE_TIME,a.BED_NO,a.PATIENT_ID,a.FIRST_OPERATION_NURSE,a.SURGEON,a.FIRST_ASSISTANT,a.FIRST_SUPPLY_NURSE,b.name,c.DEPT_NAME,d.DEPT_NAME AS OPERATION_DEPT,a.OPERATION_NAME, a.OPERATING_ROOM_NO,a.SEQUENCE FROM MED_OPERATION_MASTER a left JOIN MED_PAT_MASTER_INDEX b on a.PATIENT_ID=b.PATIENT_ID left join MED_DEPT_DICT c on a.DEPT_STAYED=c.DEPT_CODE left join MED_DEPT_DICT d on a.OPERATING_ROOM=d.DEPT_CODE WHERE a.SCHEDULED_DATE_TIME >= trunc(sysdate - 0) AND (a.SURGEON = ? or a.FIRST_ASSISTANT = ?)ORDER BY a.SCHEDULED_DATE_TIME desc";
        Connection conn = druid133.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, name);
            rs = ps.executeQuery();

            while (rs.next()) {
                Opsch opsch = new Opsch();
                opsch.setSch_dtime(rs.getString("SCHEDULED_DATE_TIME"));
                opsch.setBedno(rs.getString("BED_NO"));
                opsch.setPatient_id(rs.getString("PATIENT_ID"));
                opsch.setPatient_name(rs.getString("name"));
                opsch.setDept(rs.getString("DEPT_NAME"));
                opsch.setSurgeon(rs.getString("SURGEON"));
                opsch.setOpname(rs.getString("OPERATION_NAME"));
                opsch.setSupnurse(rs.getString("FIRST_SUPPLY_NURSE"));
                opsch.setOpnurse(rs.getString("FIRST_OPERATION_NURSE"));
                opsch.setAssistant(rs.getString("FIRST_ASSISTANT"));
                opsch.setSequence(rs.getString("SEQUENCE"));
                opsch.setOpdept(rs.getString("OPERATION_DEPT"));
                opsch.setRoom(rs.getString("OPERATING_ROOM_NO"));
                list.add(opsch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }

        return list;
    }

}
