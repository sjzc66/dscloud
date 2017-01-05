package com.jzfq.fms.common.util.payutil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class XmlEncryptUtil {
    private static final Logger logger = Logger.getLogger(XmlEncryptUtil.class);

    private static String XML_JDPAY_END = "</jdpay>";
    private static String XML_SIGN_START = "<sign>";
    private static String XML_SIGN_END = "</sign>";
    private static String SIGN = "sign";
    private static String RESULT = "result";

    /**
     * xml格式请求数据签名与加密
     *
     * @param rsaPrivateKey 商户私钥
     * @param strDesKey     商户DES加密key
     * @param genSignStr    请求参数
     * @return
     */
    public static String encrypt(String rsaPrivateKey, String strDesKey, String genSignStr) {

        String encrypt = null;
        if (StringUtils.isNotEmpty(rsaPrivateKey) && StringUtils.isNotEmpty(strDesKey) && StringUtils.isNotEmpty(genSignStr)) {
            try {
                // xml头信息
                genSignStr = JdPayXmlUtil.addXmlHeadAndElJdPay(genSignStr);
                // 格式化
                genSignStr = JdPayXmlUtil.fomatXmlStr(genSignStr);
                // 删除<sing>...</sign>
                genSignStr = JdPayXmlUtil.delXmlElm(genSignStr, SIGN);
                // 签名
                String sign = VerifySignatureUtl.encryptMerchant(genSignStr, rsaPrivateKey);
                // 封装
                String data = genSignStr.substring(0, genSignStr.length() - XML_JDPAY_END.length()) + XML_SIGN_START + sign + XML_SIGN_END + XML_JDPAY_END;
                // 3des加密
                encrypt = Base64.encodeBase64String(ThreeDesUtil.encrypt2HexStr(RsaUtil.decryptBASE64(strDesKey), data).getBytes("UTF-8"));
            } catch (Exception e) {
                logger.error("signature failed");
                throw new RuntimeException("signature failed");
            }
        }
        return encrypt;
    }

    /**
     * xml格式请求数据签名与加密
     *
     * @param rsaPrivateKey 商户私钥
     * @param strDesKey     商户DES加密key
     * @param param         请求对象，必须是pojo对象
     * @return 请求数据encrypt
     */
    public static String encrypt(String rsaPrivateKey, String strDesKey, Object param) {
        return encrypt(rsaPrivateKey, strDesKey, JdPayXmlUtil.pojo2JdPayXml(param));
    }


    /**
     * 解密xml格式返回参数
     *
     * @param rsaPubKey 商户公钥
     * @param strDesKey 商户DES加密key
     * @param encrypt   服务返回xml数据encrypt元素的字符串
     * @return 原始报文
     */
    public static String decrypt(String rsaPubKey, String strDesKey, String encrypt) {
        String reqBody = "";
        try {
            // des解密encrypt
            reqBody = ThreeDesUtil.decrypt4HexStr(RsaUtil.decryptBASE64(strDesKey), new String(Base64.decodeBase64(encrypt), "UTF-8"));
            // 获取签名
            String inputSign = JdPayXmlUtil.getXmlElm(reqBody, SIGN);
            // 添加xml头信息
            reqBody = JdPayXmlUtil.addXmlHead(reqBody);
            // 格式化
            reqBody = JdPayXmlUtil.fomatXmlStr(reqBody);
            // 删除<sign>...</sign>元素
            String genSignStr = JdPayXmlUtil.delXmlElm(reqBody, SIGN);
            // 验签
            boolean verifyResult = VerifySignatureUtl.decryptMerchant(genSignStr, inputSign, rsaPubKey);
            if (!verifyResult) {
                logger.error("verify signature failed");
                throw new RuntimeException("verify signature failed");
            }
        } catch (Exception e) {
            logger.error("data decrypt failed");
            throw new RuntimeException("data decrypt failed");
        }
        return reqBody;
    }

    /**
     * 解密xml格式返回参数
     *
     * @param rsaPubKey 商户公钥
     * @param reqBody   请求报文（解密后）
     * @return 原始报文
     */
    public static String decrypt(String rsaPubKey, String reqBody) {
        String req = "";
        try {
            // 获取签名
            String inputSign = JdPayXmlUtil.getXmlElm(reqBody, SIGN);
            // 添加xml头信息
            req = JdPayXmlUtil.addXmlHead(reqBody);
            // 格式化
            req = JdPayXmlUtil.fomatXmlStr(req);
            // 删除<sign>...</sign>元素
            String genSignStr = JdPayXmlUtil.delXmlElm(req, SIGN);
            // 验签
            boolean verifyResult = VerifySignatureUtl.decryptMerchant(genSignStr, inputSign, rsaPubKey);
            if (!verifyResult) {
                logger.error("verify signature failed");
                throw new RuntimeException("verify signature failed");
            }
        } catch (Exception e) {
            logger.error("data decrypt failed");
            throw new RuntimeException("data decrypt failed");
        }
        return req;
    }

}
