package com.funpay.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.httpclient.HttpStatus;

import java.util.HashMap;

/**
 * @author Alen
 */
@Log4j2
public class DemoUtil {
    /**
     * 重载方法，默认验签，如果不需要验签，请调用doCheckResult(HttpUtil.Response res, boolean isVerifySign)
     *
     * @param res
     * @throws Exception
     */
    public static void doCheckResult(HttpUtil.Response res) {
        try {
            doCheckResult(res, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 对结果进行验签，注意，该方法接收的参数为返回结果中的result（json格式）
     */
    public static void doCheckResult(HttpUtil.Response res, boolean isVerifySign) {
        assert res != null;
        if (res.getStatusCode() == HttpStatus.SC_OK) {
            JSONObject resultContent = JSON.parseObject(res.getContent());

            log.info("接口返回数据，字段详情请参考具体接口的文档: \n                    {}", resultContent);

            /**
             * 对结果进行验签，注意，该方法接收的参数为返回结果中的result（json格式）
             */
            if (isVerifySign) {
                try {
                    EncryptUtil.verifySign(resultContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                log.info("该接口无需验签");
                segmentation();
            }

        } else {
            log.warn("错误状态码为 {} 请参考文档中的错误状态码", res.getStatusCode());
            segmentation();
        }
    }

    /**
     * 打印当前请求接口的api地址
     */

    public static void apiAddress(String url) {
        log.info("当前请求的api接口为: " + url.replace(Constant.DOMAIN, ""));
    }


    /**
     * 打印分割线
     */

    public static void segmentation() {
        log.info("-------------------------------");
    }
}
