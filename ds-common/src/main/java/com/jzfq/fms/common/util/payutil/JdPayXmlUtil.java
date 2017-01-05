package com.jzfq.fms.common.util.payutil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class JdPayXmlUtil {
    private static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static String XML_JDPAY_START = "<jdpay>";
    private static String XML_JDPAY_END = "</jdpay>";
    private static String JDPAY = "jdpay";

    /**
     * 格式化xml字符串:逐行取值，去除元素之前的换行与空格
     *
     * @param xml
     * @return
     */
    public static String fomatXmlStr(String xml) {
        String formatStr = "";
        Pattern p = Pattern.compile("\t|\r|\n");
        Scanner scanner = new Scanner(xml);
        scanner.useDelimiter(p);
        while (scanner.hasNext()) {
            formatStr += scanner.next().trim();
        }
        return formatStr;
    }

    /**
     * 对xml格式化，添加xml头信息，添加元素<jdpay>...</jdpay>
     */
    public static String addXmlHead(String xml) {
        if (xml != null && !xml.equals("")) {
            if (!xml.trim().startsWith("<?xml")) {
                xml = XML_HEAD + xml;
            }
        }
        return xml;
    }

    /**
     * 对xml格式化，添加xml头信息，添加元素<jdpay>...</jdpay>
     */
    public static String addXmlHeadAndElJdPay(String xml) {
        if (xml != null && !xml.equals("")) {
            if (!xml.contains(XML_JDPAY_START)) {
                xml = XML_JDPAY_START + xml;
            }
            if (!xml.contains(XML_JDPAY_END)) {
                xml = xml + XML_JDPAY_END;
            }
            if (!xml.trim().startsWith("<?xml")) {
                xml = XML_HEAD + xml;
            }
        }
        return xml;
    }

    /**
     * 获取xml元素值
     *
     * @param xml
     * @param elName
     * @return
     */
    public static String getXmlElm(String xml, String elName) {
        String result = "";
        String elStart = "<" + elName + ">";
        String elEnd = "</" + elName + ">";
        if (xml.contains(elStart) && xml.contains(elEnd)) {
            int from = xml.indexOf(elStart) + elStart.length();
            int to = xml.lastIndexOf(elEnd);
            result = xml.substring(from, to);
        }
        return result;
    }

    /**
     * 删除xml元素值
     *
     * @param xml
     * @param elmName
     * @return
     */
    public static String delXmlElm(String xml, String elmName) {
        String elStart = "<" + elmName + ">";
        String elEnd = "</" + elmName + ">";
        if (xml.contains(elStart) && xml.contains(elEnd)) {
            int i1 = xml.indexOf(elStart);
            int i2 = xml.lastIndexOf(elEnd);
            String start = xml.substring(0, i1);
            int length = elEnd.length();
            String end = xml.substring(i2 + length, xml.length());
            xml = start + end;
        }
        return xml;
    }

    /**
     * obj 转 xml
     *
     * @param obj
     * @return
     */
    public static String obj2Xml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
        return xstream.toXML(obj);
    }

    /**
     * xml 转 bean
     *
     * @param xmlStr
     * @param cls    转成对象的类型
     * @param <T>
     * @return
     */
    public static <T> T xml2Bean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }

    /**
     * pojo对象转xml字符串, 声明别名jdpay
     *
     * @param obj
     * @return
     */
    public static String pojo2JdPayXml(Object obj) {
        String xmlStr = "";
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map || obj instanceof Collection || obj instanceof Dictionary) {
            throw new RuntimeException("Object type must be pojo");
        } else if (obj instanceof String) {
            xmlStr = (String) obj;
        } else {
            XStream xstream = new XStream(new DomDriver("utf8"));
            xstream.processAnnotations(obj.getClass());
            xstream.alias(JDPAY, obj.getClass());
            xmlStr = xstream.toXML(obj);
        }
        return xmlStr;
    }

    public static <T> T jdPayXml2Bean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.alias(JDPAY, cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }

}
