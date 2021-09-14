package TXmanage.util.xml_utl;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class commonxml_Util {

    @SuppressWarnings("unchecked")
    public static JSONObject parseXml(String msg) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<>();
        // 从request中取得输入流
        InputStream inputStream = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();

        // 得到根元素的所有子节点

        for (Iterator<Element> i = root.elementIterator(); i.hasNext(); ) {
            Element e = i.next();
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();

        return new JSONObject(map);
    }
}
