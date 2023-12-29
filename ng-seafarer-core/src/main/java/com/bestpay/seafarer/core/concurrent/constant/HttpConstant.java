package com.bestpay.seafarer.core.concurrent.constant;

@SuppressWarnings("ALL")
public class HttpConstant {

    public static final Integer DEFAULT = 1;
    public static final String RULES = "#\\{([^}]*)}";
    public static final String REQUEST_METHOD = "POST";
    public static final Integer CONNECTION_TIME_OUT = 5000;

    //best pay
    public static final String SIGN_KEY = "sign";
    public static final String ALIAS = "conname";
    public static final String AGREEID_KEY = "agreeId";
    public static final String PRIKEY_PWD = "14170207";
    public static final String PRIKEY_STORE_TYPE = "PKCS12";
    public static final String SIGN_MERCHANT_NO_KEY = "signMerchantNo";
    public static final String SIGN_MERCHANT_NO = "3178002072759722";
    public static final String PRIKEY_FILE_PATH = "cert/test/test.p12";
    public static final String AGREEID = "20210223030100103612212639170599";
    public static final String PREFIX = "https://mapi.test.bestpay.net/gapi";
}
