
package TXmanage.response;

import TXmanage.aes.AesException;
import TXmanage.aes.WXBizMsgCrypt;
import TXmanage.util.dbpool.druid191;
import TXmanage.util.conn_util;
import TXmanage.util.data_util;
import TXmanage.util.time_util;
import TXmanage.util.wx_util;
import TXmanage.util.xml_utl.fix_daka_Util;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Controller
public class SprespController {
    public SprespController() {
    }

    /***
     * 审批状态改变的时候通过 审批 应用响应
     * @param request 获取参数
     * @param response 用Writer方法
     */
    @ResponseBody
    @RequestMapping({"/Sp_response"})
    public void Spresponse(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        String msg_signature = request.getParameter("msg_signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        /*
        echostr用于测试不参与实际应用
         */
        if (echostr != null) {
            try {
                PrintWriter out = response.getWriter();
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(data_util.getSp_token(), data_util.getSp_sEncodingAESKey(), data_util.getCorpID());
                String result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
                out.print(result);
                out.close();
            } catch (AesException | IOException e) {
                e.printStackTrace();
            }
        } else {

            Connection conn191 = druid191.getConnection();
            PreparedStatement ps191 = null;

            try {
                InputStream inputStream = request.getInputStream();
                String sPostData = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                inputStream.close();
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(data_util.getSp_token(), data_util.getSp_sEncodingAESKey(), data_util.getCorpID());
                String msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, sPostData);
                JSONObject dataMap = fix_daka_Util.parseXml(msg);
                System.out.println(dataMap);

                if ("打卡补卡".equals(dataMap.get("SpName"))) {
                    if (dataMap.get("SpStatus").equals("2")) {
                        String userid = dataMap.getString("userid");
                        long time = Long.parseLong(dataMap.getString("ApplyTime"));
                        String dt = time_util.tsToDate(time * 1000L);
                        String sql = "INSERT INTO Con_RecordAPP(PersonnelID, RDateTime,PlaceName ) VALUES (?, ?, ?)";
                        ps191 = conn191.prepareStatement(sql);
                        String empcode = wx_util.UIDtoEmp(userid);

                        ps191.setString(1, empcode);
                        ps191.setString(2, dt);
                        ps191.setString(3, "补卡");
                        ps191.executeUpdate();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn_util.closeall(conn191, null, ps191);
            }
        }
    }
}

