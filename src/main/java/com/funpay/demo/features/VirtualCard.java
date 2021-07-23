package com.funpay.demo.features;

import com.alibaba.fastjson.JSON;
import com.funpay.demo.utils.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author FunPay
 */
public class VirtualCard {

    /**
     * 创建虚拟卡接口地址
     */
    public static final String VIRTUAL_CARD_CREATE = Constant.DOMAIN + "/fun/payment/virtualCard/api/create";
    /**
     * 释放虚拟卡接口地址
     */
    public static final String VIRTUAL_CARD_DESTROY = Constant.DOMAIN + "/fun/payment/virtualCard/api/destroy";

    /**
     * 查询虚拟卡卡号和状态接口地址
     */
    public static final String VIRTUAL_CARD_QUERY_VC = Constant.DOMAIN + "/fun/payment/virtualCard/api/queryVC";

    /**
     * 虚拟卡交易查询接口地址
     */
    public static final String VIRTUAL_CARD_CHECK = Constant.DOMAIN + "/fun/payment/virtualCard/api/check";

    /**
     * 虚拟卡支付记录批量查询接口地址
     */
    public static final String VIRTUAL_CARD_QUERY = Constant.DOMAIN + "/fun/payment/virtualCard/api/query";

    /**
     * 查询虚拟卡卡号和状态
     * /fun/payment/virtualCard/api/queryVC
     */
    public static void virtualCardQueryVc() {

        long timestamp = System.currentTimeMillis();
        String orderId = timestamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("timestamp", timestamp + "");
        paramMap.put("orderNo", "1617272135886HGSsAT3");
        paramMap.put("bankType", "");
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(VIRTUAL_CARD_QUERY_VC, param);
        DemoUtil.doCheckResult(res);
    }

    /**
     * 虚拟卡交易查询
     * 接口地址：/fun/payment/virtualCard/api/check
     */
    public static void check() {

        long currentTimeStamp = System.currentTimeMillis();
        String orderId = currentTimeStamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("timestamp", currentTimeStamp + "");
        paramMap.put("version", "2.0");
        paramMap.put("tradeNo", "003000620220210701170914589236");
        paramMap.put("orderNo", orderId);
        paramMap.put("accountNo", "");
        paramMap.put("bankType", "");

        paramMap.put("pageSize", 10);
        paramMap.put("pageNum", 0);

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(VIRTUAL_CARD_CHECK, param);
        DemoUtil.doCheckResult(res);

    }

    /**
     * 申请使用虚拟卡（VC）
     * /fun/payment/virtualCard/api/create
     */
    public static void virtualCardCreate() {

        long timestamp = System.currentTimeMillis();
        String orderId = timestamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("clientID", Constant.CLIENT_ID);
        paramMap.put("timestamp", timestamp + "");
        paramMap.put("version", "2.0");
        paramMap.put("amount", 50000);
        paramMap.put("currency", Constant.CURRENCY);
        paramMap.put("orderNo", orderId);
        paramMap.put("expireDate", "20220101");
        // 回调地址非常重要 真实环境中不可为空
        paramMap.put("returnUrl", Constant.RETURN_URL);
        paramMap.put("bankType", "VTBfix");
        paramMap.put("accountBase", "123413457");
        paramMap.put("phoneNumber", "1234562678");
        paramMap.put("userName", "test");
        paramMap.put("IDNo", "");

        // 签名
        paramMap.put("sign", EncryptUtil.encrypt(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        try {
            HttpUtil.Response res = HttpUtil.doPost(VIRTUAL_CARD_CREATE, null, JSON.toJSONString(paramMap));

            DemoUtil.doCheckResult(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放销毁虚拟卡（VC）
     * 该接口调用成功后无result字段，无需验签
     * 接口地址：/fun/payment/virtualCard/api/destroy
     */
    public static void virtualCardDestroy() {

        long timestamp = System.currentTimeMillis();
        String orderId = timestamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("accountNo", "902000225482");
        paramMap.put("timestamp", timestamp + "");
        paramMap.put("orderNo", "12345566671");
        paramMap.put("bankType", "");
        paramMap.put("version", "2.0");

        // 对map参数进行排序并签名
        paramMap.put("sign", EncryptUtil.encrypt(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        try {
            HttpUtil.Response res = HttpUtil.doPost(VIRTUAL_CARD_DESTROY, null, JSON.toJSONString(paramMap));
            DemoUtil.doCheckResult(res, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 虚拟卡支付记录批量查询
     * 接口地址：/fun/payment/virtualCard/api/query
     */
    public static void virtualCardQuery() {

        long currentTimeStamp = System.currentTimeMillis();
        String orderId = currentTimeStamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        paramMap.put("tradeNo", "003000620220210701170946252729");
        paramMap.put("orderNo", orderId);
        paramMap.put("accountNo", "2.0");
        paramMap.put("bankType", "2.0");
        paramMap.put("version", "2.0");

        //请使用越南时间
        String endTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        paramMap.put("startTime", "20210501190654");
        paramMap.put("endTime", endTime);
        paramMap.put("pageSize", 10);
        paramMap.put("pageNum", 0);
        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(VIRTUAL_CARD_QUERY, param);
        DemoUtil.doCheckResult(res);

    }
}
