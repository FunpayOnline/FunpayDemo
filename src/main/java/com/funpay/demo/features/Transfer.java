package com.funpay.demo.features;

import com.alibaba.fastjson.JSON;
import com.funpay.demo.utils.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author FunPay
 */
public class Transfer {
    /**
     * 打款记录批量查询
     * 接口地址：/fun/transfer/api/query
     */
    public static void bulkQuery() {
        // 毫秒值，当前系统时间戳
        long currentTimeStamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        // 模拟环境仅为示意，tradeNo 是重要的交易凭证，真实环境不可随意设置
        paramMap.put("tradeNo", "003000620220210701170946252729");
        //请使用越南时间
        String endTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        paramMap.put("startTime", "20210501190654");
        paramMap.put("endTime", endTime);
        paramMap.put("pageSize", 10);
        paramMap.put("pageNum", 0);
        paramMap.put("status", 0);
        paramMap.put("version", "2.0");


        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(Constant.BULK_QUERY, param);
        DemoUtil.doCheckResult(res);

    }

    /**
     * 打款结果查询
     * 接口地址：/fun/transfer/api/check
     */
    public static void check() {
        long currentTimeStamp = System.currentTimeMillis();
        String orderId = currentTimeStamp + RandomUtil.charDigits(5);
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        // 模拟环境仅为示意，tradeNo orderNo是重要的交易凭证，真实环境不可随意设置
        paramMap.put("tradeNo", "003000620220210701170914589236");
        paramMap.put("orderNo", orderId);
        paramMap.put("version", "2.0");
        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(Constant.TRANSFER_RESULT_CHECK, param);
        DemoUtil.doCheckResult(res);
    }

    /**
     * 核验银行卡信息
     * 接口地址：/fun/transfer/api/verifyBankInfo
     */
    public static void verifyBankInfo() {

        long currentTimeStamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        // 毫秒值，当前系统时间戳
        paramMap.put("timestamp", currentTimeStamp);
        paramMap.put("bankNo", 28);
        paramMap.put("bankLocation", "vn");
        // 模拟环境仅为示意，此处都是仿真值
        paramMap.put("accountNo", "123456789");
        paramMap.put("accountType", 0);
        paramMap.put("accountName", "LE VAN HOAI");
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(Constant.VERIFY_BANK_INFO_URL, param);
        DemoUtil.doCheckResult(res);

    }

    /**
     * 获取银行列表
     * 接口地址：/fun/transfer/api/getBankList
     */
    public static void getBankList() {
        long currentTimeStamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        // 毫秒值，当前系统时间戳
        paramMap.put("timestamp", currentTimeStamp);
        paramMap.put("bankNo", 0);
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(Constant.GET_BANK_LIST_URL, param);
        assert res != null;
        DemoUtil.doCheckResult(res, false);

    }

    /**
     * 打款
     * 接口地址: /fun/transfer/api/transferMoney
     */
    public static void transferMoney() {

        try {
            long timestamp = System.currentTimeMillis();
            String orderId = timestamp + RandomUtil.charDigits(5);
            HashMap<String, Object> paramMap = new HashMap<>(10);
            paramMap.put("merchantID", Constant.MERCHANT);
            paramMap.put("businessID", Constant.BUSINESS);
            // 毫秒值，当前系统时间戳
            paramMap.put("timestamp", timestamp + "");
            // 打款金额
            paramMap.put("amount", 50000);
            paramMap.put("currency", "VND");
            paramMap.put("version", "2.0");
            // 用户区分交易的orderNo
            paramMap.put("orderNo", orderId);
            // 回调地址非常重要 真实环境中不可为空
            paramMap.put("returnUrl", "");
            // 模拟环境仅为示意，此处都是仿真值
            paramMap.put("accountName", "LE VAN HOAI");
            paramMap.put("accountNo", "123456789");
            paramMap.put("bankNo", 28);
            paramMap.put("accountType", 0);
            paramMap.put("bankLocation", "vn");
            paramMap.put("remark", "");
            // 打款时间 可忽略
            paramMap.put("transferTime", "");
            paramMap.put("clientID", "");
            paramMap.put("isAsync", 0);
            // 签名
            paramMap.put("sign", EncryptUtil.encrypt(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

            HttpUtil.Response res = HttpUtil.doPost(Constant.TRANSFER_LINK, null, JSON.toJSONString(paramMap));

            DemoUtil.doCheckResult(res);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
