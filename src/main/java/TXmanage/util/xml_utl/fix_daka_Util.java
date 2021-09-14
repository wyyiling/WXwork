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

public class fix_daka_Util {

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
            if (e.getName().equals("ApprovalInfo")) {
                for (Iterator<Element> j = e.elementIterator(); j.hasNext(); ) {
                    Element f = j.next();
                    if (f.getName().equals("Applyer")) {
                        for (Iterator<Element> k = f.elementIterator(); k.hasNext(); ) {
                            Element g = k.next();
                            map.put(g.getName(), g.getText());
                        }
                    }
                    map.put(f.getName(), f.getText());
                }
            }
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();

        return new JSONObject(map);
    }
}