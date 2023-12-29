package com.bestpay.seafarer.core.concurrent.config;

import cn.hutool.core.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @Author: liujianqun
 * @Description:
 * @Date: 2017/8/7
 * @Moidfy by:
 */
@SuppressWarnings("ALL")
public class CryptoUtil {


    private static CertificateFactory factory = initFactory();

    private static CertificateFactory initFactory() {
        try {
            return CertificateFactory.getInstance("X.509");
        } catch (CertificateException var1) {
            throw new RuntimeException(var1);
        }
    }
    /**
     * @desc SHA256 加密公共类
     * @author liujianqun
     * @method getSHA256
     * @return java.lang.String
     */

    public static X509Certificate base64StrToCert(String base64Cert) throws GeneralSecurityException {

        ByteArrayInputStream ex = new ByteArrayInputStream(Base64.decode(base64Cert));
        X509Certificate cert = (X509Certificate)factory.generateCertificate(ex);
        if(cert == null) {
            throw new GeneralSecurityException("将cer从base64转换为对象失败");
        }
        return cert;

    }

    public static KeyCertInfo fileStreamToKeyCertInfo(String keyFile, String pwd, String keyStoreType, String alias){
        KeyCertInfo result = null;
        try {
            InputStream resourceAsStream = CryptoUtil.class.getClassLoader().getResourceAsStream(keyFile);
            KeyStore inputKeyStore = KeyStore.getInstance(keyStoreType);
            
            char[] inPassword = (pwd == null) ? null : pwd.toCharArray();
            inputKeyStore.load(resourceAsStream, inPassword);
            PrivateKey privateKey = (PrivateKey)inputKeyStore.getKey(alias, inPassword);
            X509Certificate x509Certificate = (X509Certificate)inputKeyStore.getCertificate(alias);
            byte[] encoded = x509Certificate.getEncoded();
            String base64Cert = new String(Base64.encode(encoded));
            result = new KeyCertInfo();
            result.setBase64Cert(base64Cert);
            result.setPrivateKey(privateKey);           
            return result;
        } catch (Exception e) {
        	System.out.println("e:" + e.getMessage());
            return result;
        }
    }
}
