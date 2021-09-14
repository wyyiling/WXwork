package gkservice;

import TXmanage.util.json_util;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class gkservice {

    public gkservice() {
    }

    @RequestMapping("/gkservice")
    @ResponseBody
    public void gkpush(HttpServletResponse response) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String news = "{\n" +
                "   \"touser\" :  \"WangYong\" ,\n" +
                "   \"msgtype\" : \"textcard\",\n" +
                "   \"agentid\" : 1000019,\n" +
                " \"textcard\" : {\"title\" : \"感控通知\"," +
                "  \"description\" : \"<div class=\\\"gray\\\">" + sdf.format(date) + "</div> <div class=\"normal\">测试测试</div><div class=\"highlight\">测试测试</div>\"\n" +
                "\"url\" : \" \"," +
                " \"btntxt\":\"更多\" \n}" +
                "}";
        String acc = WXwork.util.wx_util.gettoken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN".replace("ACCESS_TOKEN", acc);
        JSONObject OB = json_util.postResponseBody(url, news);
        System.out.println(news);
        System.out.println(OB);
    }

    public static void main(String[] args) {

    }

}
