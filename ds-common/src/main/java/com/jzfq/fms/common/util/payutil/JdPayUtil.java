package com.jzfq.fms.common.util.payutil;

import com.jzfq.fms.common.common.jdpay.JdPayBaseResponse;
import com.jzfq.fms.common.common.jdpay.JdPayResponse;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;


public class JdPayUtil {

    private static String VERSION = "version";
    private static String MERCHANT = "merchant";


    /**
     * 解析京东支付响应信息
     *
     * @param rsaPubKey 商户公钥
     * @param strDesKey 商户deskey
     * @param xmlResp   返回xml报文
     * @param cls       转换对象类型
     * @param <T>       转换对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T extends JdPayBaseResponse> T parseResp(String rsaPubKey, String strDesKey, String xmlResp, Class<T> cls) throws IllegalAccessException, InstantiationException {
        JdPayResponse jdPayResponse = JdPayXmlUtil.jdPayXml2Bean(xmlResp, JdPayResponse.class);
        T t = cls.newInstance();
        if (StringUtils.isNotEmpty(jdPayResponse.getEncrypt())) {
            String reqBody = XmlEncryptUtil.decrypt(rsaPubKey, strDesKey, jdPayResponse.getEncrypt());
            t = JdPayXmlUtil.jdPayXml2Bean(reqBody, cls);
        }
        t.setMerchant(jdPayResponse.getMerchant());
        t.setVersion(jdPayResponse.getVersion());
        t.setResult(jdPayResponse.getResult());
        return t;
    }

    /**
     * 对象转map
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map<String, String> objectToMap(Object object) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> map = new HashedMap();
        // 父类属性
        for (Class<?> cls = object.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            // 添加属性key到list
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                map.put(f.getName(), (String)f.get(object));
            }
        }
        return map;
    }

}
