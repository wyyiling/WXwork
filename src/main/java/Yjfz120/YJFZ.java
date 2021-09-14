package Yjfz120;

import WXwork.util.conn_util;
import WXwork.util.*;
import WXwork.util.dbpool.druid9145;
import WXwork.util.dbpool.druid95;
import WXwork.util.commonxml_Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
public class YJFZ {
    @RequestMapping({"/fzpurl"})
    @ResponseBody
    public JSONObject getFZurl(@RequestParam("url") String url) {
        return js_util.getConfig(url);
    }

    @RequestMapping({"/fzgetinfo"})
    @ResponseBody
    public JSONObject fzGetinfo(@RequestParam("cardno") String card) {
        return fzinfo(card);
    }

    @RequestMapping({"/fzsub"})
    @ResponseBody
    public String fzSub(@RequestBody JSONObject fzbody) {
        String status = "fail";
        Connection conn = druid95.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO TRIAGE_PATIENTINFO (PID,HEALTH_CARD_NO,PATIENT_NAME,SEX_CODE,SEX," +
                "DATA_OF_BIRTH,NATIONALITY,COUNTRY,HOME_ADDRESS,MOBILE_PHONE,CARD_NO,MEASURE_DATE," +
                "MEASURE_TIME,DIASTOLIC_PRESSURE,SYSTOLIC_PRESSURE,CREATOR,CREATETIME,SPO2,TEMPER," +
                "TEMPER_TYPE,BREATH,HR,TRIAGE_GRADE_CODE,TRIAGE_GRADE_NAME,PART_STATUS,WARD_CODE," +
                "WARD_NAME,PATIENT_SOURCE_CODE,PATIENT_SOURCE_NAME,GREEN_REASON_CODE,GREEN_REASON_NAME)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, fzbody.getString("pid"));
            ps.setString(2, fzbody.getString("healthcn"));
            ps.setString(3, fzbody.getString("name"));
            ps.setString(4, fzbody.getString("sexcd"));
            ps.setString(5, fzbody.getString("sexnm"));
            String birth = fzbody.getString("birthday");
            ps.setString(6, birth.replace("-", ""));
            ps.setString(7, fzbody.getString("nation"));
            ps.setString(8, fzbody.getString("country"));
            ps.setString(9, fzbody.getString("address"));
            ps.setString(10, fzbody.getString("mobile"));
            ps.setString(11, fzbody.getString("zlc"));
            String msd = fzbody.getString("mstime").split("T")[0];
            String mst = fzbody.getString("mstime").split("T")[1];
            ps.setString(12, msd.replace("-", ""));
            ps.setString(13, mst.replace(":", ""));
            ps.setString(14, fzbody.getString("szy"));
            ps.setString(15, fzbody.getString("ssy"));
            ps.setString(16, fzbody.getString("creator"));
            String now = fzbody.getString("now");
            ps.setString(17, now.replace(":", "").replace("-", "").trim());
            ps.setString(18, fzbody.getString("xy"));
            ps.setString(19, fzbody.getString("twz"));
            ps.setString(20, fzbody.getString("twcd"));
            ps.setString(21, fzbody.getString("hx"));
            ps.setString(22, fzbody.getString("xl"));
            ps.setString(23, fzbody.getString("fzfjcd"));
            ps.setString(24, fzbody.getString("fzfjnm"));
            ps.setString(25, fzbody.getString("fzzt"));
            ps.setString(26, fzbody.getString("bqcd"));
            ps.setString(27, fzbody.getString("bqnm"));
            ps.setString(28, fzbody.getString("ptsourcecd"));
            ps.setString(29, fzbody.getString("ptsourcenm"));
            ps.setString(30, fzbody.getString("lstdcd"));
            ps.setString(31, fzbody.getString("lstdnm"));
            ps.executeUpdate();
            status = "success";
            return status;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, null, ps);
        }
        return status;
    }

    @RequestMapping({"/fznewcard"})
    @ResponseBody
    public JSONObject fzNewCard(@RequestBody JSONObject newcbody) {
        String url = "http://192.164.8.30:9003/ElcHealthCardWebservice.asmx?op=Register";
        String todata = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:neus=\"http://www.neusoft.com/\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "      <neus:Register>\n" +
                "         <!--Optional:-->\n" +
                "         <neus:requestXml>\n" +
                "         \t<![CDATA[\n" +
                " {\n" +
                "                \"apply_type\":\"03\",\n" +
                "                \"id_type\":\"01\",\n" +
                "                \"id_no\":\"cardno\",\n".replace("cardno", newcbody.getString("3idc")) +
                "                \"name\":\"3nm\",\n".replace("3nm", newcbody.getString("3name")) +
                "                \"cellphone\":\"55gg\"\n".replace("55gg", newcbody.getString("3cell")) +
                "                }               \n" +
                "                ]]>\n" +
                "         </neus:requestXml>\n" +
                "      </neus:Register>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>\n";
        String ret = json_util.postxmlResponseBody(url, todata);
        String ec = JSON.parseObject(ret.split("<RegisterResult>")[1].split("</RegisterResult>")[0]).getString("ehealth_card_id");
        return fzinfo(ec);
    }

//    @RequestMapping({"/yjfz120"})
//    public ModelAndView yjfz(String code) {
//        ModelAndView mav = new ModelAndView();
//        String access_token = data_util.getAccess_token(data_util.getYjfz_sec());
//        String url = data_util.getCode_url().replace("ACCESS_TOKEN", access_token).replace("CODE", code);
//        String usid = json_util.getResponseBody(url).getString("UserId");
//        mav.setViewName("views/yjfz/yjmain");
//        String empcode = wx_util.UIDtoEmp(usid);
//        mav.addObject("empcode", empcode);
//        return mav;
//    }

    private static JSONObject fzinfo(String card) {
        JSONObject responsebody = JSON.parseObject("{\"minis\":\"fail\"}");
        String scard = card.split(":")[0];
        String url = "http://192.164.8.28:8090/HT_PDA_Webservice.asmx?op=QueryPatientinfoByElc";
        String todata = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:neus=\"http://www.neusoft.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <neus:QueryPatientinfoByElc>\n" +
                "         <!--Optional:-->\n" +
                "         <neus:requestXml>" +
                "<![CDATA[" +
                "<HtRequest>\n" +
                "<cardNo>CARD_NO</cardNo>\n".replace("CARD_NO", scard) +
                "<cardType>0</cardType>\n" +
                "</HtRequest>\n" +
                "]]>" +
                "</neus:requestXml>\n" +
                "      </neus:QueryPatientinfoByElc>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String ret = json_util.postxmlResponseBody(url, todata);
        Connection conn = druid9145.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String fistring = ret.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&")
                    .split("<HtResponse>")[1].split("</HtResponse>")[0];
            responsebody = commonxml_Util.parseXml(fistring);
            responsebody.put("zlcard", "");
            responsebody.put("minis", "");
            responsebody.put("mobile", "");
            responsebody.put("home", "");
            String birth = responsebody.getString("DateOfBirth");
            String nbirth = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6, 8);
            responsebody.put("DateOfBirth", nbirth);
            String blno = responsebody.getString("PID");
            String sql = "SELECT nvl(HOME,'0') as \"HOME\",nvl(HOME_TEL,'0') AS \"HOME_TEL\" FROM com_patientinfo WHERE CARD_NO =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, blno);
            rs = ps.executeQuery();
            if (rs.next()) {
                responsebody.put("home", rs.getString("HOME"));
                responsebody.put("mobile", rs.getString("HOME_TEL"));
            }
            sql = "SELECT * FROM FIN_OPB_ACCOUNTCARD where CARD_NO=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, blno);
            rs = ps.executeQuery();
            if (rs.next()) {
                responsebody.put("zlcard", rs.getString("MARKNO"));
            }
            responsebody.put("minis", "success");
            responsebody.put("elcard", scard);
            return responsebody;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn_util.closeall(conn, rs, ps);
        }
        return responsebody;
    }
}
