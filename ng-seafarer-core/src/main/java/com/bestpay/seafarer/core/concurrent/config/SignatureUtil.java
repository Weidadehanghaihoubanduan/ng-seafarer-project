package com.bestpay.seafarer.core.concurrent.config;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.*;

@SuppressWarnings("ALL")
public class SignatureUtil {

    /***
     * 编码
     */
    private static final String charcterCode = "UTF-8";

    /**
     * 算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /***
     * SHA256 加密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(charcterCode));
        byte[] signBytes = signature.sign();
        return new String(Base64.encode(signBytes));
    }


    /***
     * 验签
     * @param jsonS
     * @param certName
     * @return
     * @throws Exception
     */
    public static Boolean verifySign(String jsonS, String certName) throws Exception {

        Map mapTypes = JSON.parseObject(jsonS);
        Map data = translateResultData(mapTypes);
        String checksign = String.valueOf(mapTypes.get("sign"));
        String checkContent = assembelSignaturingData1(data);
        InputStream pubStream = SignatureUtil.class.getClassLoader().getResourceAsStream(certName);
        byte pubByte[] = new byte[1024];
        pubStream.read(pubByte);
        pubStream.close();
        X509Certificate x509Certificate = CryptoUtil.base64StrToCert(Base64Encrypt.getBASE64ForByte(pubByte));
        Signature signatureCheck = Signature.getInstance("SHA1withRSA");
        boolean isOk = SignatureUtil.verify(signatureCheck, checkContent, checksign, x509Certificate.getPublicKey());
        System.out.println("验签明文:" + checkContent);

        return isOk;

    }

    // 顺序组装请求参数，用于签名校验
    public static String assembelSignaturingData(Map data) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> treeMap = new TreeMap(data);
        for (Map.Entry<String, Object> ent : treeMap.entrySet()) {
            String name = ent.getKey();
                sb.append(name).append('=').append(ent.getValue()).append('&');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }


    /***
     *    顺序组装请求参数，用于签名
     * @param data
     * @return
     */
    public static String assembelSignaturingData1(Map data) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> treeMap = new TreeMap(data);
        for (Map.Entry<String, Object> ent : treeMap.entrySet()) {
            String name = ent.getKey();
            if (!"sign".equals(name)) {
                sb.append(name).append('=').append(ent.getValue()).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 接口返回结果复杂集合类转换签名
     *
     * @param res 接口返回结果
     * @return
     */
    public static Map<String, Object> translateResultData(Map<String, Object> res) {
        Map<String, Object> resultDataMap = new HashMap<String, Object>();
        Set set = res.entrySet();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (!(entry.getValue() instanceof String)) {
                if (entry.getValue() instanceof String[]
                        || entry.getValue() instanceof Map
                        || entry.getValue() instanceof List) {
                    // 将数据转换成json字符串参与签名
                    resultDataMap.put(key, JSON.toJSONString(value, SerializerFeature.MapSortField, SerializerFeature.WriteMapNullValue, SerializerFeature.UseISO8601DateFormat));
                } else {
                    resultDataMap.put(key, value);
                }
            } else {
                resultDataMap.put(key, value);
            }
        }
        return resultDataMap;
    }

    public static boolean verify(Signature signature, String data, String sign, PublicKey publicKey) throws UnsupportedEncodingException {
        if (data == null || sign == null || publicKey == null) {
            return false;
        }
        byte[] signBytes = Base64.decode(sign.getBytes(charcterCode));
        try {
            signature.initVerify(publicKey);
            signature.update(data.getBytes(charcterCode));
            return signature.verify(signBytes);
        } catch (Exception e) {
            return false;
        }
    }
}
