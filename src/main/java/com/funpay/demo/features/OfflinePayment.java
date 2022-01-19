package com.funpay.demo.features;

import com.alibaba.fastjson.JSON;
import com.funpay.demo.utils.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public static final String UPDATE_VTP_CODE = Constant.DOMAIN + "/fun/payment/api/updateOfflineCode";

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
        HashMap<String, Object> paramMap = new HashMap<>(10);
        // Y	商家ID，Funpay后台分配
        paramMap.put("merchantID", Constant.MERCHANT);
        // Y	业务ID，Funpay后台分配
        paramMap.put("businessID", Constant.BUSINESS);
        // Y	计费点ID，Funpay后台分配
        paramMap.put("feeID", Constant.FEE_ID);
        // N	用户ID，商户传入，以便区分用户对账平账，商户可以根据自己需要传入终端用户的ID或者合同号等唯一标记，
        // Funpay对此字段不作区分处理，返回结果中亦不进行返回，仅用于结果报表中的检索。商户可以根据自己需求决定是否传入。
        // 如果不传入请不要使用此字段。如果带了此字段请务必确保其有值。最大40个字符。
        paramMap.put("clientID", "123450");
        // Y	UNIX系统时间戳，当前时间
        paramMap.put("timestamp", String.valueOf(timestamp));
        // 支付金额，单位1VND，最小额10,000 VND（一万），最大额80,000,000VND（八千万），只支持整数
        paramMap.put("amount", "500.25");
        paramMap.put("currency", Constant.CURRENCY);
        // Y	计费点名称，必须与申请计费点的时候输入的一致
        paramMap.put("name", Constant.FEE_NAME);

        // Y	商家自定义订单号，唯一，最长40字符，仅数字字母，不支持特殊字符，同一个商家，同一个订单号只能用一次
        paramMap.put("orderNo", "No" + timestamp);
        // Y	商家自行指定的过期时间，格式yyyyMMddHHmmss。超过指定时间后用户将不能再对此订单进行支付。
        paramMap.put("expireDate", LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // Y	回调函数地址，立即回调用，不需要URL编码，因为不会直接传输链接地址。 此参数必须为“http://” 开头或者“https://” 开头，
        // 否则不会触发回调。强烈建议就算您不依赖回调，也填入一个合法的回调地址，可以不做任何逻辑处理，但是方便进一步扩展。
        paramMap.put("returnUrl", Constant.RETURN_URL);
        // Y	此处是固定值“2.0”（注意，实际使用时不需要加引号），代表API版本V2.0
        paramMap.put("version", "2.0");
        // Y	用户手机号码，用于线下便利店跟用户确认核验订单，避免误操作
        paramMap.put("phoneNumber", "09589451257");
        // y ,用户真实姓名，用于收单方跟用户进行订单核验，避免误操作，请使用"First name-Middle Name-Last Name"这种格式进行姓名的传输，
        // First name、Middle Name、Last Name都必须具备并且以中划线"-"进行分隔
        paramMap.put("accountName", "ya-ma-ha");
        // y,用户身份证件类型 "UMID"--统一身份证，监管部门要求的必要信息记录，其他可用值参考“可用证件列表”
        paramMap.put("IDType", "SSS");
        // Y	用户真实身份证号，支持9位和12位身份证号[新增参数]
        paramMap.put("IDNo", "421569875124");

        // Y	用户所在省份，监管部门要求的必要信息记录
        paramMap.put("province", "province");
        // Y	用户所在城市，监管部门要求的必要信息记录
        paramMap.put("city", "city");
        // Y	用户所在地址第一行，监管部门要求的必要信息记录
        paramMap.put("line1", "xx oo");
        // Y	用户所在地址第二行，监管部门要求的必要信息记录
        paramMap.put("line2", "xxx ooo");
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
     * /fun/payment/api/updateOfflineCode
     */
    public static void updateOfflineCode() {

        long timestamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);
        // Y	商家ID，Funpay后台分配
        paramMap.put("merchantID", Constant.MERCHANT);
        // Y	业务ID，Funpay后台分配
        paramMap.put("businessID", Constant.BUSINESS);
        // Y	计费点ID，Funpay后台分配
        paramMap.put("feeID", Constant.FEE_ID);
        // Y	UNIX系统时间戳，当前时间
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("orderNo", "No1641809711362");
        // 支付金额，单位1VND，最小额10,000 VND（一万），最大额80,000,000VND（八千万），只支持整数
        paramMap.put("amount", "300.25");

        // Y	商家自行指定的过期时间，格式yyyyMMddHHmmss。超过指定时间后用户将不能再对此订单进行支付。
        paramMap.put("expireDate", LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // Y	回调函数地址，立即回调用，不需要URL编码，因为不会直接传输链接地址。 此参数必须为“http://” 开头或者“https://” 开头，
        // 否则不会触发回调。强烈建议就算您不依赖回调，也填入一个合法的回调地址，可以不做任何逻辑处理，但是方便进一步扩展。
        paramMap.put("returnUrl", Constant.RETURN_URL);
        // Y	用户手机号码，用于线下便利店跟用户确认核验订单，避免误操作
        paramMap.put("phoneNumber", "09589451257");
        // y ,用户真实姓名，用于收单方跟用户进行订单核验，避免误操作，请使用"First name-Middle Name-Last Name"这种格式进行姓名的传输，
        // First name、Middle Name、Last Name都必须具备并且以中划线"-"进行分隔
        paramMap.put("accountName", "ya-ma-ha");

        paramMap.put("version", "2.0");
        // y,用户身份证件类型 "UMID"--统一身份证，监管部门要求的必要信息记录，其他可用值参考“可用证件列表”
        paramMap.put("IDType", "SSS");
        // Y	用户真实身份证号，支持9位和12位身份证号[新增参数]
        paramMap.put("IDNo", "421569875124");

        // Y	用户所在省份，监管部门要求的必要信息记录
        paramMap.put("province", "province");

        // Y	用户所在城市，监管部门要求的必要信息记录
        paramMap.put("city", "city");

        // Y	用户所在地址第一行，监管部门要求的必要信息记录
        paramMap.put("line1", "xx oo");

        // Y	用户所在地址第二行，监管部门要求的必要信息记录
        paramMap.put("line2", "xxx ooo");

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
        HashMap<String, Object> paramMap = new HashMap<>(10);

        // merchantID number Y 商家ID，Funpay后台分配
        paramMap.put("merchantID", Constant.MERCHANT);
        // businessID number Y 业务ID，Funpay后台分配
        paramMap.put("businessID", Constant.BUSINESS);
        // timestamp number Y UNIX系统时间戳，当前时间
        paramMap.put("timestamp", currentTimeStamp);
        // tradeNo string N 可选值，如设置会与其他条件作并运算来筛选符合条件的交易。代表Funpay返回的交易号。
         paramMap.put("tradeNo", "003000220120211216145658093976");
        // orderNo string N 可选值，如设置会与其他条件作并运算来筛选符合条件的交易。代表Funpay返回的交易号。
         paramMap.put("orderNo", "No1639641414905");
        // startTime string N 查询的起始时间，格式yyyyMMddHHmmss，不传或者置空则认为是从第一笔交易开始查询，以用户支付完成实际入账时间为准
        paramMap.put("startTime", "2021091022121210");
        // endTime string N 可选值，如设置会与其他条件作并运算来筛选符合条件的交易。查询的截至时间，格式 yyyyMMddHHmmss，以河内时间为准，
        // 不传或者置空则认为是截至到符合条件的最后一笔交易，
        paramMap.put("endTime", "");
        // 以用户支付完成实际入账时间为准
        // pageSize number N 可选值，默认值为10，最大支持100 。查询的每页容量。只支持整数。
        paramMap.put("pageSize", 10);
        // pageNum number N 可选值，默认值为0。查询的页码数，以0开始计算。只支持整数。
        paramMap.put("pageNum", 0);
        // version number Y 此处是固定值“2.0”（注意，实际使用时不需要加引号），代表API版本V2 .0
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
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", System.currentTimeMillis());
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
        paramMap.put("merchantID", Constant.MERCHANT);
        // businessID	number	Y	业务ID，Funpay后台分配
        paramMap.put("businessID", Constant.BUSINESS);
        // timestamp	number	Y	UNIX系统时间戳，当前时间
        paramMap.put("timestamp", currentTimeStamp);
        // tradeNo	string	Y	由Funpay创建的交易号，在创建订单或者返回结果的时候都会返回这个交易号，Funpay创建，唯一。
        // 可以传空，如果传入数据，将会作为有效值与orderNo一起进行检索。如果orderNo与tradeNo同时为空将会直接返回错误。
         paramMap.put("tradeNo", "010000201520120220111111010928905");
        // orderNo	string	Y	由商家创建的交易号，唯一。可以传空，如果传入数据，将会作为有效值与tradeNo一起进行检索。
        // 如果orderNo与tradeNo同时为空将会直接返回错误。
        paramMap.put("orderNo", "No1641979867255");
        // version	number	Y	此处是固定值“2.0”（注意，实际使用时不需要加引号），代表API版本V2.0
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);

        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(OFFLINE_CHECK, param);
        DemoUtil.doCheckResult(res);
    }
}
