package TXmanage.util;

import com.alibaba.fastjson.JSONObject;

public class data_util {

    public static String getCorpID() {
        return "wwd63d766f2bf188b9";
    }

    public static String getSp_token() {
        return "konodioda";
    }

    public static String getTx_token() {
        return "niconiconi";
    }

    public static String getTx_sEncodingAESKey() {
        return "5yykq8TqwSY2MaAFCbfxs2gQDLu35OmajGb6TjDY8Pp";
    }

    public static String getSp_sEncodingAESKey() {
        return "YYbgVouojgtwyssGTPHg2YzV6YRybQ9yO0FjdMhg8Jx";
    }

    public static String getTx_sec() {
        return "tGbP3vekVKgUmqizPSqgENzwtj0AEmGJngF1oefTFsI";
    }

    public static String getZg_sec() {
        return "QC2F_4l3PRJdB7njYI-e0iDzhn25tlnQwq5Oimtf6Kk";
    }

    public static String getDaka_sec() {
        return "pFavbnHkkPO46zn_Ocue7PoMOvr1TWrmoa-hAf9x5fs";
    }

    public static String getInfo_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
    }

    public static String getAll_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
    }

    public static String getUpdate_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
    }

    public static String getDaka_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=ACCESS_TOKEN";
    }

    public static String getTicket_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";
    }

    public static String getTag_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/tag/get?access_token=ACCESS_TOKEN&tagid=TAGID";
    }

    public static String getDeplist_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
    }

    public static String getAdddep_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
    }

    public static String getUpdep_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
    }

    public static String getDeldep_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
    }

    private static String getToken_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    }

    public static String getAccess_token(String secret) {
        JSONObject js = json_util.getResponseBody(getToken_url().replace("ID", getCorpID()).replace("SECRET", secret));
        if (js.getString("errmsg").equals("ok")) {
            return js.getString("access_token");
        } else {
            return "";
        }
    }

}
