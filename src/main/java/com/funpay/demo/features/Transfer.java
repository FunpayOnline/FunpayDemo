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
        //请使用菲律宾时间
        String endTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // startTime	string	N	查询的起始时间，格式yyyyMMddHHmmss，以河内时间为准，不传或者置空则认为是从第一笔交易开始查询，以用户支付完成实际入账时间为准
        paramMap.put("startTime", "20210501190654");
        //endTime	string	N	可选值，如设置会与其他条件作并运算来筛选符合条件的交易。查询的截至时间，格式yyyyMMddHHmmss，不传或者置空则认为是截至到符合条件的最后一笔交易，以用户支付完成实际入账时间为准
        paramMap.put("endTime", endTime);
        // pageSize	number	N	可选值，默认值为10，最大支持100 。查询的每页容量。只支持整数。
        paramMap.put("pageSize", 10);
        // pageNum	number	N	可选值，默认值为0。查询的页码数，以0开始计算。只支持整数。
        paramMap.put("pageNum", 0);
        // status	number	N	查询的订单状态筛选，默认值为0，只查成功的交易。
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
        HashMap<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("merchantID", Constant.MERCHANT);
        paramMap.put("businessID", Constant.BUSINESS);
        paramMap.put("timestamp", currentTimeStamp);
        // tradeNo	string	Y	由Funpay创建的交易号，在创建订单或者返回结果的时候都会返回这个交易号，Funpay创建，唯一。可以传空，如果传入数据，将会作为有效值与orderNo一起进行检索。如果orderNo与tradeNo同时为空将会直接返回错误。
        paramMap.put("tradeNo", "010000201220120220111113240780744");
        // orderNo	string	Y	由商家创建的交易号，唯一。可以传空，如果传入数据，将会作为有效值与tradeNo一起进行检索。如果orderNo与tradeNo同时为空将会直接返回错误。
        paramMap.put("orderNo", "fd1641872026014");
        paramMap.put("version", "2.0");
        HashMap<String, String> param = new HashMap<>(1);
        param.put("param", EncryptUtil.encryptWithBase64(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));
        HttpUtil.Response res = HttpUtil.doGet(Constant.TRANSFER_RESULT_CHECK, param);
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
            HashMap<String, Object> paramMap = new HashMap<>(10);
            paramMap.put("merchantID", Constant.MERCHANT);
            // businessID	number	Y	业务ID，Funpay后台分配
            paramMap.put("businessID", Constant.BUSINESS);
            // clientID	string	N	用户ID，商户传入，以便区分用户对账平账，商户可以根据自己需要传入终端用户的ID或者合同号等唯一标记，
            // Funpay对此字段不作区分处理，返回结果中亦不进行返回，仅用于结果报表中的检索。商户可以根据自己需求决定是否传入。
            // 如果不传入请不要使用此字段。如果带了此字段请务必确保其有值。
            paramMap.put("clientID", "111");
            // timestamp	number	Y	UNIX系统时间戳，当前时间
            paramMap.put("timestamp", timestamp);
            // amount	number	Y	打款金额，单位1VND，最小值50,000VND（五万），最大值50,000,000VND（五千万），仅支持整数
            paramMap.put("amount", "300.24");
            // currency	string	Y	货币符号，必须为“VND”
            paramMap.put("currency", Constant.CURRENCY);
            //  orderNo	string	Y	商家自定义订单号，唯一，最长40字符，仅数字字母，不支持特殊字符
            String orderNo = "fd" + timestamp;
            paramMap.put("orderNo", orderNo);
            // returnUrl	url	Y	回调函数地址，银行打款完成后Funpay会调用此接口以通知商家，不需要URL编码，因为不会直接传输链接地址。Funpay回调此接口的具体说明参见“打款结果回调”。此参数必须为“http://” 开头或者“https://” 开头，否则不会触发回调。强烈建议就算您不依赖回调，也填入一个合法的回调地址，可以不做任何逻辑处理，但是方便进一步扩展。
            paramMap.put("returnUrl", Constant.RETURN_URL);
            // accountName	string	Y	用户姓名，即收款的银行卡持有者的姓名。需与银行留存信息完全一致。一般为大写英文字母。Funpay作了兼容处理，如用户输入越南文字母，会自动进行转换。但建议用户自行处理完成后再传入Funpay。商家在使用此接口前，可以先通过核验银行信息接口或者其他方法核验相关信息的正确性，以确保打款成功率。此参数为必传参数，Funpay会检测此信息是否与银行留存一致，如不一致或者为空，会拒绝打款。
            paramMap.put("accountName", "CONG-TY-CP");
            // accountNo	string	Y	用户银行卡账号或者卡号。
            paramMap.put("accountNo", "9704127164820608");
            // accountType	number	Y	保留字段暂无实际意义
            paramMap.put("accountType", 1);
            // bankLocation	string	Y	用户银行卡归属国家，目前仅支持“vn”
            paramMap.put("bankLocation", "ph");
            // bankNo	number	Y	用户银行卡归属银行代号，参见“获取银行列表”。
            paramMap.put("bankNo", 1);
            // remark	string	Y	打款的附录语言，会展示给用户，仅支持英文大小字母和数字。最大长度100个字符，含空格，必填，可以为若干个空格键。
            paramMap.put("remark", "go get your money");
            // version	number	Y	此处是固定值“2.0”（注意，实际使用时不需要加引号），代表API版本V2.0
            paramMap.put("version", "2.0");
            // phoneNumber	string	Y	用户手机号码
            paramMap.put("phoneNumber", "09256847895");
            // IDNo	string	Y	用户身份证号，支持9位、12位身份证号[新增参数]
            paramMap.put("IDType", "SSS");
            // Y 用户真实身份证号，支持9位和12位身份证号[新增参数]
            paramMap.put("IDNo", "421569875124");
            paramMap.put("country", "PH");

            // Y 用户所在省份，监管部门要求的必要信息记录
            paramMap.put("province", "province");
            // Y	用户所在城市，监管部门要求的必要信息记录
            paramMap.put("city", "city");
            // Y	用户所在地址第一行，监管部门要求的必要信息记录
            paramMap.put("line1", "xx oo");
            // Y	用户所在地址第二行，监管部门要求的必要信息记录
            paramMap.put("line2", "xxx ooo");
            paramMap.put("zipCode", "256987");
            // 签名
            paramMap.put("sign", EncryptUtil.encrypt(paramMap, Constant.SECRET_KEY, Constant.LOCALE_PRIVATE_KEY));

            HttpUtil.Response res = HttpUtil.doPost(Constant.TRANSFER_LINK, null, JSON.toJSONString(paramMap));

            DemoUtil.doCheckResult(res);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
