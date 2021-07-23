package com.funpay.demo.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.funpay.demo.utils.DemoUtil.segmentation;

/**
 * EncryptUtil
 * Funpay统一签名加密工具类
 * Created by Common on 2020/11/11
 *
 * @author FunPay
 */
@Log4j2
public class EncryptUtil {

    /**
     * Funpay V1.0 - 1.3 签名方法，适用于POST取得sign参数
     *
     * @param map 以map格式输入所有待签名参数
     * @param key secretKey
     * @return 签名字符串
     */
    @Deprecated
    public static String encrypt(HashMap<String, Object> map, String key) {
        //对map 按key 进行排序
        ArrayList<String> list = new ArrayList<>(map.keySet());
        list.sort(Comparator.naturalOrder());

        //拼接为字符串
        StringBuilder sb = new StringBuilder();
        for (String name : list) {
            Object value = map.get(name);
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(name).append("=").append(value);
        }

        //拼接上secretKey
        sb.append(key);
        System.out.println("String before md5 " + sb);

        //取 md5
        String result = md5(sb.toString()).toUpperCase();
        System.out.println("String after md5 " + result);
        return result;
    }

    /**
     * Funpay V1.0 - 1.3 签名方法，适用于GET取得param参数
     *
     * @param map 以map格式输入所有待签名参数
     * @param key secretKey
     * @return 用于拼接url的param参数
     */
    @Deprecated
    public static String encryptWithBase64(HashMap<String, Object> map, String key) {
        // 获取sign
        map.put("sign", encrypt(map, key));

        // 取Base64字符串的时候不需要拼接
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        //Url Safe Base64
        return Base64.getUrlEncoder().encodeToString(sb.toString().getBytes());
    }

    private static String md5(String plainText) {
        String reMd5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            reMd5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reMd5;
    }

    /**
     * Funpay V2.0 签名方法，适用于POST取得sign参数
     *
     * @param map            以map格式输入所有待签名参数
     * @param key            secretKey
     * @param privateKeyPath 私钥存储路径
     * @return 签名字符串
     */
    public static String encrypt(HashMap<String, Object> map, String key, String privateKeyPath) {
        //对map 按key 进行排序
        ArrayList<String> list = new ArrayList<>(map.keySet());
        list.sort(Comparator.naturalOrder());

        //拼接为字符串
        StringBuilder sb = new StringBuilder();
        for (String name : list) {
            Object value = map.get(name);
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(name).append("=").append(value);
        }

        //拼接上secretKey
        sb.append(key);
        segmentation();
        log.info("为方便您进行debug，控制台输出信息持久化至当前项目根路径下的logs文件夹");
        log.info("加密前的字符串(String before sign) : " + sb);

        String result = "";
        try {
            //使用私钥进行加密签名
            result = RsaUtil.signWithPrivateKey(sb.toString(), privateKeyPath, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("加密后的字符串(String after sign) : " + result);
        return result;
    }

    /**
     * Funpay V2.0 签名方法，适用于GET取得param参数
     *
     * @param map            以map格式输入所有待签名参数
     * @param key            secretKey
     * @param privateKeyPath 私钥存储路径
     * @return 用于拼接url的param参数
     */
    public static String encryptWithBase64(HashMap<String, Object> map, String key, String privateKeyPath) {
        // 获取sign
        map.put("sign", encrypt(map, key, privateKeyPath));

        // 取Base64字符串的时候不需要排序
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        //Url Safe Base64

        return Base64.getUrlEncoder().encodeToString(sb.toString().getBytes());
    }

    /**
     * 验签工具类
     *
     * @param resultJson 传入的接口返回的res json对象，提取result字段并对其进行排序
     * @return 验签结果
     */
    public static boolean verifySign(JSONObject resultJson) throws Exception {
        //拿到result结果json对象
        JSONObject jsonObject = resultJson.getJSONObject("result");

        if (jsonObject == null) {
            log.info("不存在result字段，暂不进行验签,请检查接口返回的数据是否正确");
            segmentation();
            return false;
        }
        //从result中获取sign
        String sign = jsonObject.getString("sign");
        assert sign != null;
        log.info("接口result字段中的sign为: " + sign);

        HashMap<String, String> resultMap = new HashMap<>(16);

        /*
         * 将result结果的json对象转为hashmap
         */
        for (Object map : jsonObject.entrySet()) {
            String key = ((Map.Entry) map).getKey().toString();
            String value = ((Map.Entry) map).getValue().toString();
            resultMap.put(key, value);
        }

        /*
         * 移除接口验签不需要计算的字段
         * 请根据实际的接口文档对不需要参与的字段进行移除
         */
        resultMap.remove("sign");
        resultMap.remove("transactions");

        /*
         * 1. 调用 sortParam(map) 排序方法，对hashmap进行排序，并且处理为url风格的string串 格式为：key1=value1&key2=value2
         * 2. 在尾部拼接 SECRET_KEY
         */
        String param = sortParam(resultMap) + Constant.SECRET_KEY;

        /*
          调用验签工具类，注意此方法可以重载，也可传入PublicKey的路径。
         */
        boolean verifyResult = RsaUtil.verifyWithPublicKey(param.getBytes(StandardCharsets.UTF_8), sign, Constant.FUNPAY_PUBLIC_KEY);

        if (verifyResult) {
            log.info("验签成功");
        } else {
            log.info("验签失败");
        }
        segmentation();
        //返回验签结果
        return verifyResult;

    }

    /**
     * 对参数map进行排序
     *
     * @param param 参数map
     * @return 排序后转为url参数风格的string
     */
    public static String sortParam(Map<String, String> param) {
        List<Map.Entry<String, String>> arrayList = new ArrayList<>(param.entrySet());
        //对参数进行排序，按照ASCII码序列，a->b
        arrayList.sort(Map.Entry.comparingByKey());
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : arrayList) {
            // 移除为空的参数
            if (item.getKey() != null) {
                String key = item.getKey();
                String value = item.getValue();
                value = null == value ? "" : value;
                //转为url风格参数，key1=value1&key2=value2
                sb.append(key).append("=").append(value).append("&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
