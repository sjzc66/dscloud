package com.jzfq.fms.common.util.payutil;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class VerifySignatureUtl {
	
	private static final Logger logger = Logger.getLogger(VerifySignatureUtl.class);
	
	/**
	 * 签名
	 * @param sourceSignString
	 * @param rsaPriKey
	 * @return
	 */
	public static String encryptMerchant(String sourceSignString, String rsaPriKey) {
		logger.debug("encrypt merchant: sourceSignString=[" + sourceSignString + "]");
		String result = "";
        try {
            //摘要
            String sha256SourceSignString = SHAUtil.Encrypt(sourceSignString, null);
            //私钥对摘要进行加密
            byte[] newsks = RSACoder.encryptByPrivateKey(sha256SourceSignString.getBytes("UTF-8"), rsaPriKey);
            result = Base64.encodeBase64String(newsks);
        } catch (Exception e) {
            logger.error("encrypt merchant error.", e);
            throw new RuntimeException("verify signature failed.", e);
        }
        return result;
	}
	
	/**
	 * 收单验签
	 * @param strSourceData
	 * @param signData
	 * @param rsaPubKey
	 */
	public static boolean decryptMerchant(String strSourceData, String signData, String rsaPubKey) {
		boolean flag = false;
		logger.debug("verify signature: strSourceData=[" + strSourceData + "]");
        //1、校验参数是否为空，为空则抛异常
		if (signData == null || signData.isEmpty())
            throw new IllegalArgumentException("Argument 'signData' is null or empty");

        if (rsaPubKey == null || rsaPubKey.isEmpty())
            throw new IllegalArgumentException("Argument 'key' is null or empty");
        
        try {
        	//2、对拼接后的字符串进行SHA-256加密(摘要)
            String sha256SourceSignString = SHAUtil.Encrypt(strSourceData, null); 
            logger.debug("verify signature: sha256SourceSignString=[" + sha256SourceSignString + "]");
            
            //1、将商户传来的签名进行base64解密
            byte[] signByte = Base64.decodeBase64(signData);
            logger.debug("verify signature: pubKey=[" + rsaPubKey + "]");
            //2、base64解密后的值用公钥解密
            byte[] decryptArr = RSACoder.decryptByPublicKey(signByte, rsaPubKey); 
            //3、公钥解密后的数据转成字符串
            String decryptStr = RSACoder.bytesToString(decryptArr); 
            logger.debug("verify signature: decryptStr=[" + decryptStr + "]");
            if (sha256SourceSignString.equals(decryptStr)){
            	flag = true;
            	logger.debug("verify signature: result=[" + "verify successfully." + "]");
            }else {
            	logger.debug("verify signature: result=[" + "verify failed" + "]");
                throw new RuntimeException("Signature verification failed.");
            }
        }catch (UnsupportedEncodingException e) {
            logger.error("验证商户签名失败" + e.getMessage());
            throw new RuntimeException("verify signature failed.", e);
        }catch (RuntimeException e){
            logger.error("验证商户签名失败" + e.getMessage());
            throw e;
        }catch(Exception e){
            logger.error("验证商户签名失败" + e.getMessage());
            throw new RuntimeException("verify signature failed.", e);
        }
        return flag;
	}

}
