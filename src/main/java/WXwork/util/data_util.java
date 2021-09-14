package WXwork.util;

public class data_util {

    public static String getAll_char() {
        return "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    public static String getJS_code() {
        return "!-deepdarkfantasies-!";
    }

    public static String getCode_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

    }

    public static String getInfo_url() {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
    }
}
