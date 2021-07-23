package com.funpay.demo.features;

import com.funpay.demo.utils.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Alen
 */
public class OnlinePayment {
    /**
     * 网银支付记录批量查询
     */
    public static final String ONLINE_PAY_QUERY = Constant.DOMAIN + "/fun/payment/api/query";

    /**
     * 网银当前余额查询
     */
    public static final String ONLINE_QUERY_BALANCE = Constant.DOMAIN + "/fun/payment/api/queryBalance";

    /**
     * 网银支付接口
     */
    public static final String ONLINE_PAY = Constant.DOMAIN + "/fun/payment/api/onlinePay";


    /**
     * 网银支付接口
     * 此接口会返回一个支付页面
     * 接口地址：/fun/payment/api/onlinePay
     */
    public static void onlinePay() {

        long currentTimeStamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        String orderNo = currentTimeStamp + RandomUtil.charDigits(5);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        paramMap.put("feeID", Constant.FEE_ID);
        paramMap.put("version", "2.0");
        paramMap.put("clientID", Constant.CLIENT_ID);
        paramMap.put("amount", 50000);
        paramMap.put("currency", Constant.CURRENCY);
        paramMap.put("name", "testMerchant");
        paramMap.put("orderNo", orderNo);
        paramMap.put("returnUrl", Constant.RETURN_URL);
        paramMap.put("purchaseType", "2");
        paramMap.put("phoneNumber", "21678467812");
        paramMap.put("userName", "alenTest");
        paramMap.put("IDNo", "");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(ONLINE_PAY, param);

        DemoUtil.doCheckResult(res);

    }


    /**
     * 网银支付记录批量查询
     * 接口地址：/fun/payment/api/query
     */
    public static void query() {

        long currentTimeStamp = System.currentTimeMillis();
        String orderId = currentTimeStamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
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

        HttpUtil.Response res = HttpUtil.doGet(ONLINE_PAY_QUERY, param);
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

        HttpUtil.Response res = HttpUtil.doGet(ONLINE_QUERY_BALANCE, param);
        DemoUtil.doCheckResult(res);

    }


}
