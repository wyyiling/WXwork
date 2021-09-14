
package TXmanage.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class json_util {

    private static String getHandler(String url) {
        String responseBody = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            closeableHttpResponse = httpClient.execute(httpGet);
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = closeableHttpResponse.getEntity();
                responseBody = EntityUtils.toString(httpEntity, "utf-8");
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclose(httpClient, closeableHttpResponse);
        }
        return responseBody;
    }

    public static JSONObject getResponseBody(String url) {
        return JSON.parseObject(getHandler(url));
    }

    private static String postHandler(String url, String data) {
        String responseBody = "";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            HttpPost httppost = new HttpPost(url);
            StringEntity stringentity = new StringEntity(data, ContentType.create("text/json", "UTF-8"));
            httppost.setEntity(stringentity);
            closeableHttpResponse = httpclient.execute(httppost);
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseBody = EntityUtils.toString(closeableHttpResponse.getEntity());
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclose(httpclient, closeableHttpResponse);
        }
        return responseBody;
    }

    public static JSONObject postResponseBody(String url, String data) {
        return JSON.parseObject(postHandler(url, data));
    }

    private static void httpclose(CloseableHttpClient httpClient, CloseableHttpResponse closeableHttpResponse) {

        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (closeableHttpResponse != null) {
            try {
                closeableHttpResponse.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}