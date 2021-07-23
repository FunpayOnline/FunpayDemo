package com.funpay.demo.features;

import com.funpay.demo.utils.Constant;
import com.funpay.demo.utils.DemoUtil;
import com.funpay.demo.utils.RsaUtil;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author FunPay
 */
@Log4j2
public class KeyGenerator {

    /**
     * 测试由阿里MiniU生成的密钥对 https://miniu.alipay.com/keytool/create
     *
     * @throws Exception 加密异常
     */
    public static void testKeyFromAlipay() throws Exception {
        RSAPrivateKey privateKey = RsaUtil.loadPrivateKey(Constant.MERCHANT_PRIVATE_KEY, StandardCharsets.UTF_8);

        DemoUtil.segmentation();
        log.info("请将以下一行字符串粘贴到Funpay业务管理的上传公钥输入框中:");
        RSAPublicKey publicKey = RsaUtil.loadPublicKey(Constant.MERCHANT_PUBLIC_KEY, StandardCharsets.UTF_8);
        DemoUtil.segmentation();

        String signStr = RsaUtil.signWithPrivateKey("abc".getBytes(StandardCharsets.UTF_8), privateKey);
        log.info("请将以下一行字符串粘贴到Funpay业务管理的上传签名字符串输入框中:");
        System.out.println(signStr);
        DemoUtil.segmentation();

        boolean res = RsaUtil.verifyWithPublicKey("abc".getBytes(StandardCharsets.UTF_8), signStr, publicKey);
        System.out.println("Verify result: " + res);
    }

    /**
     * 测试本地使用代码本地生成的密钥对
     *
     * @throws Exception 加密异常
     */
    public static void testAutoGen() throws Exception {
        RsaUtil.generateSaveKeyPair(Constant.LOCALE_PUBLIC_KEY, Constant.LOCALE_PRIVATE_KEY);

        DemoUtil.segmentation();
        log.info("以下一行字符串为您的私钥，请妥善保存，并且不要暴露给包括Funpay内的所有人：");
        RSAPrivateKey privateKey = RsaUtil.loadPrivateKey(Constant.LOCALE_PRIVATE_KEY);
        DemoUtil.segmentation();


        log.info("请将以下一行字符串粘贴到Funpay业务管理的上传公钥输入框中：");
        RSAPublicKey publicKey = RsaUtil.loadPublicKey(Constant.LOCALE_PUBLIC_KEY);

        String signStr = RsaUtil.signWithPrivateKey("abc".getBytes(StandardCharsets.UTF_8), privateKey);
        log.info("请将以下一行字符串粘贴到Funpay业务管理的上传签名字符串输入框中:");
        System.out.println(signStr);
        DemoUtil.segmentation();

        boolean res = RsaUtil.verifyWithPublicKey("abc".getBytes(StandardCharsets.UTF_8), signStr, publicKey);
        System.out.println("Verify result: " + res);
    }

    /**
     * 测试本地使用代码本地生成的密钥对
     *
     * @throws Exception 加密异常
     */
    public static void testLocaleFile() throws Exception {
        RSAPrivateKey privateKey = RsaUtil.loadPrivateKey(Constant.LOCALE_PRIVATE_KEY);
        DemoUtil.segmentation();
        log.info("请将以下一行字符串粘贴到Funpay业务管理的上传公钥输入框中：");

        RSAPublicKey publicKey = RsaUtil.loadPublicKey(Constant.LOCALE_PUBLIC_KEY);
        DemoUtil.segmentation();

        String signStr = RsaUtil.signWithPrivateKey("abc".getBytes(StandardCharsets.UTF_8), privateKey);
        log.info("请将以下一行字符串粘贴到Funpay业务管理的上传签名字符串输入框中:");
        System.out.println(signStr);
        DemoUtil.segmentation();

        boolean res = RsaUtil.verifyWithPublicKey("abc".getBytes(StandardCharsets.UTF_8), signStr, publicKey);
        System.out.println("Verify result: " + res);
    }

}
