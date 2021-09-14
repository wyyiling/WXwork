package TXmanage.response;

import TXmanage.aes.WXBizMsgCrypt;
import TXmanage.util.data_util;
import TXmanage.util.wx_util;
import TXmanage.util.xml_utl.commonxml_Util;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Controller
public class TxrespController {
    public TxrespController() {
    }

    /***
     * 管理通讯录，成员信息改变时通过通讯录响应
     * @param request 获取参数
     * @param response 用Writer方法
     */
    @ResponseBody
    @RequestMapping({"/Tx_response"})
    public void txresponse(HttpServletRequest request, HttpServletResponse response) {
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
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(data_util.getTx_token(), data_util.getTx_sEncodingAESKey(), data_util.getCorpID());
                String result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
                out.print(result);
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                InputStream inputStream = request.getInputStream();
                String sPostData = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                inputStream.close();
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(data_util.getTx_token(), data_util.getTx_sEncodingAESKey(), data_util.getCorpID());
                String msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, sPostData);
                JSONObject dataMap = commonxml_Util.parseXml(msg);

                System.out.println(dataMap);
                String userid = dataMap.getString("UserID");
                String txtoken = wx_util.gettxtoken();

                if (dataMap.getString("ChangeType").equals("create_user")) {
                    JSONObject user = wx_util.getUserInfo(txtoken, userid);
                    wx_util.checkuser(user, txtoken);

                } else if (dataMap.getString("ChangeType").equals("update_user")) {
                    JSONObject user = wx_util.getUserInfo(txtoken, userid);
                    wx_util.checkuser(user, txtoken);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
