package WXwork.util;

import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.util.*;

public class js_util {

    private static String sha1(String str) {

        if (str != null && str.length() != 0) {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            try {
                MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
                mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
                byte[] md = mdTemp.digest();
                int j = md.length;
                char[] buf = new char[j * 2];
                int k = 0;

                for (byte bt : md) {
                    buf[k++] = hexDigits[bt >>> 4 & 15];
                    buf[k++] = hexDigits[bt & 15];
                }

                return new String(buf);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static JSONObject getConfig(String url) {

        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 15; ++i) {
            sb.append(data_util.getAll_char().charAt(r.nextInt(data_util.getAll_char().length())));
        }

        String timpstamp = Long.toString((new Date()).getTime() / 1000L);
        String nonceStr = sb.toString();
        String ticket = wx_util.getticket();
        String un = "jsapi_ticket=JSAPITICKET&noncestr=NONCESTR&timestamp=TIMESTAMP&url=URL".replace("JSAPITICKET", ticket).replace("NONCESTR", nonceStr).replace("TIMESTAMP", timpstamp).replace("URL", url);

        String signature = sha1(un);
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timpstamp);
        map.put("ticket", ticket);
        map.put("noncestr", nonceStr);
        map.put("signature", signature);
        return new JSONObject(map);
    }

}
