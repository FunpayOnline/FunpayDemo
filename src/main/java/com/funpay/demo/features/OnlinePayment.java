package com.funpay.demo.features;

import com.funpay.demo.utils.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        // Y	UNIX系统时间戳，当前时间
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("amount", "3562.01");
        paramMap.put("currency", Constant.CURRENCY);
        // Y	计费点名称，必须与申请计费点的时候输入的一致
        paramMap.put("name", Constant.FEE_NAME);

        // Y	商家自定义订单号，唯一，最长40字符，仅数字字母，不支持特殊字符，同一个商家，同一个订单号只能用一次
        paramMap.put("orderNo", "No" + timestamp + RandomUtil.charDigits(5));
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
        paramMap.put("remark", "hello world");
        // Y	用户所在城市，监管部门要求的必要信息记录
        paramMap.put("email", "abc@123.com");

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
        long timestamp = System.currentTimeMillis();
        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        // businessID number Y 业务ID，Funpay后台分配
        paramMap.put("businessID", Constant.BUSINESS);
        //  timestamp number Y UNIX系统时间戳，当前时间
        paramMap.put("timestamp", timestamp);
        // tradeNo string N 可选值，如设置会与其他条件作并运算来筛选符合条件的交易。代表Funpay返回的交易号。
         paramMap.put("tradeNo", "003000220120211216145658093976");
        // orderNo string N 可选值，如设置会与其他条件作并运算来筛选符合条件的交易。代表Funpay返回的交易号。
         paramMap.put("orderNo", "No1639641414905");
        // startTime string N 查询的起始时间，格式yyyyMMddHHmmss，以河内时间为准，不传或者置空则认为是从第一笔交易开始查询，以用户支付完成实际入账时间为准
        // endTime string N 可选值，如设置会与其他条件作并运算来筛选符合条件的交易。查询的截至时间，格式yyyyMMddHHmmss，以河内时间为准，不传或者置空则认为是截至到符合条件的最后一笔交易，
        // 以用户支付完成实际入账时间为准
        // pageSize number N 可选值，默认值为10，最大支持100 。查询的每页容量。只支持整数。
        paramMap.put("pageSize", 10);
        // pageNum number N 可选值，默认值为0。查询的页码数，以0开始计算。只支持整数。
        paramMap.put("pageNum", 0);
        // version number Y 此处是固定值“2.0”（注意，实际使用时不需要加引号），代表API版本V2 .0
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

        long timestamp = System.currentTimeMillis();

        HashMap<String, Object> paramMap = new HashMap<>(10);

        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", timestamp);
        paramMap.put("version", "2.0");

        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

        HttpUtil.Response res = HttpUtil.doGet(ONLINE_QUERY_BALANCE, param);
        DemoUtil.doCheckResult(res);

    }
}
