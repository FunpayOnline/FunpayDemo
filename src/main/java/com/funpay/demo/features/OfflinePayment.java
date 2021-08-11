package com.funpay.demo.features;

import com.alibaba.fastjson.JSON;
import com.funpay.demo.utils.*;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Alen
 */
public class OfflinePayment {
    public static final String OFFLINE_PAY = Constant.DOMAIN + "/fun/payment/api/offlinePay";

    /**
     * 线下支付结果查询
     */
    public static final String OFFLINE_CHECK = Constant.DOMAIN + "/fun/payment/api/check";

    /**
     * 更新线下支付码接口地址
     */
    public static final String UPDATE_VTP_CODE = Constant.DOMAIN + "/fun/payment/api/updateVTPCode";

    /**
     * 当前余额查询api地址
     */
    public static final String QUERY_BALANCE = Constant.DOMAIN + "/fun/payment/api/queryBalance";

    /**
     * 线下支付记录批量查询接口地址
     */
    public static final String GET_OFFLINE_PAY = Constant.DOMAIN + "/fun/payment/api/query";

    /**
     * 线下支付接口
     * /fun/payment/api/offlinePay
     */
    public static void offlinePay() {

        long timestamp = System.currentTimeMillis();
        String orderId = timestamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("clientID", Constant.CLIENT_ID);
        paramMap.put("timestamp", timestamp + "");
        paramMap.put("amount", 50000);
        paramMap.put("currency", "VND");
        paramMap.put("name", "");
        // 用户区分交易的orderNo
        // 模拟环境仅为示意，orderNo 是重要的交易凭证，真实环境不可随意设置
        paramMap.put("orderNo", orderId);
        paramMap.put("expireDate", "");
        // 回调地址非常重要 真实环境中不可为空
        paramMap.put("returnUrl", "");
        paramMap.put("version", "2.0");
        paramMap.put("purchaseType", "2");
        paramMap.put("phoneNumber", "123456789");
        paramMap.put("userName", "test");
        paramMap.put("IDNo", "");

        // 签名
        paramMap.put("sign", EncryptUtil.encrypt(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = null;
        try {
            res = HttpUtil.doPost(OFFLINE_PAY, null, JSON.toJSONString(paramMap));
        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        DemoUtil.doCheckResult(res);

    }

    /**
     * 更新线下支付码
     * /fun/payment/api/updateVTPCode
     */
    public static void updateVtpCode() {

        long timestamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("timestamp", timestamp + "");
        // 模拟环境仅为示意，tradeNo 是重要的交易凭证，真实环境不可随意设置
        paramMap.put("tradeNo", "003000620220210701170914589236");
        paramMap.put("amount", 50000);
        paramMap.put("expireDate", "");
        // 回调地址非常重要 真实环境中不可为空
        paramMap.put("returnUrl", Constant.RETURN_URL);
        paramMap.put("phoneNumber", "");
        paramMap.put("userName", "");
        paramMap.put("status", 1);
        paramMap.put("version", "2.0");
        paramMap.put("IDNo", "");

        // 签名
        paramMap.put("sign", EncryptUtil.encrypt(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = null;
        try {
            res = HttpUtil.doPost(UPDATE_VTP_CODE, null, JSON.toJSONString(paramMap));
        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        DemoUtil.doCheckResult(res);

    }


    /**
     * 线下支付记录批量查询
     * 接口地址：/fun/payment/api/query
     */
    public static void query() {

        long currentTimeStamp = System.currentTimeMillis();
        String orderId = currentTimeStamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        // 模拟环境仅为示意，orderNo tradeNo 是重要的交易凭证，真实环境不可随意设置
        paramMap.put("tradeNo", "003000620220210701170946252729");
        paramMap.put("orderNo", orderId);
        //请使用越南时间
        String endTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        paramMap.put("startTime", "20210501190654");
        paramMap.put("endTime", endTime);
        paramMap.put("pageSize", 10);
        paramMap.put("pageNum", 0);
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(GET_OFFLINE_PAY, param);
        DemoUtil.doCheckResult(res);

    }


    /**
     * 当前余额查询 ok
     * 接口地址：/fun/payment/api/queryBalance
     */
    public static void queryBalance() {

        long currentTimeStamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(QUERY_BALANCE, param);
        DemoUtil.doCheckResult(res);

    }


    /**
     * 线下支付结果查询
     * 接口地址：/fun/payment/api/check
     */
    public static void offlineCheck() {

        long currentTimeStamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        String orderId = currentTimeStamp + RandomUtil.charDigits(5);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        // 模拟环境仅为示意，orderNo tradeNo 是重要的交易凭证，真实环境不可随意设置
        paramMap.put("tradeNo", "003000220120210706084257250076");
        paramMap.put("orderNo", "");
        paramMap.put("phoneNumber", "123456789");
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);

        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(OFFLINE_CHECK, param);
        DemoUtil.doCheckResult(res);
    }
}
