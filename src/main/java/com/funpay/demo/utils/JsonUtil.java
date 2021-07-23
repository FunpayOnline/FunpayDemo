package com.funpay.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.funpay.demo.pojo.Result;


/**
 *  @author FunPay
 */
public class JsonUtil {
    public static <T> Result<T> parseResult(String json) {
        TypeReference<Result<T>> typeReference = new TypeReference<Result<T>>() {};
        return JSON.parseObject(json, typeReference.getType());
    }
}
