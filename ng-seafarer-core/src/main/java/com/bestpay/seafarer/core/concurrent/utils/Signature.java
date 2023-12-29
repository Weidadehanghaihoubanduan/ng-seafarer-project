package com.bestpay.seafarer.core.concurrent.utils;

import com.bestpay.seafarer.core.concurrent.config.CryptoUtil;
import com.bestpay.seafarer.core.concurrent.config.KeyCertInfo;
import com.bestpay.seafarer.core.concurrent.config.SignatureUtil;

import java.security.PrivateKey;
import java.util.Map;

import static com.bestpay.seafarer.core.concurrent.constant.HttpConstant.*;

/**
 * @Author: dengyancan
 * @Date: 2022/12/15
 */
@SuppressWarnings("unchecked")
public class Signature {

    /**
     * 添加密钥
     * @param clazz 待加密实例
     * @return map put end
     */
    public static Object signaturePut(Object clazz) throws Exception {

        Map<String, Object> target;

        if(clazz instanceof Map) {
            target = (Map<String, Object>) clazz;
            signatureEnd(target);
            return target;
        }

        target = Conversion.objectToMap(clazz);      signatureEnd(target);
        //返回当前class, 故class内必须有以下key(agreeId, signMerchantNo, sign)
        return Conversion.mapToObject(target, clazz.getClass());
    }

    private static void signatureEnd(Map<String, Object> target) throws Exception {

        KeyCertInfo keyCertInfo = CryptoUtil.fileStreamToKeyCertInfo(
                PRIKEY_FILE_PATH, PRIKEY_PWD, PRIKEY_STORE_TYPE, ALIAS);
        String content = SignatureUtil.assembelSignaturingData(target);
        target.put(AGREEID_KEY, AGREEID);
        target.put(SIGN_MERCHANT_NO_KEY, SIGN_MERCHANT_NO);
        target.put(SIGN_KEY,
                SignatureUtil.sign(content, (PrivateKey) keyCertInfo.getPrivateKey()));
    }
}
