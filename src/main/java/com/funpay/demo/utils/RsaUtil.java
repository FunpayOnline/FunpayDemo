package com.funpay.demo.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author FunPay
 */
@Log4j2
public class RsaUtil {

    private static final String ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final int KEY_SIZE = 2048;
    private static final int KEY_LINE_SIZE = 64;

    /**
     * BASE64Encoder 编码
     *
     * @param data 要编码的数据
     * @return 编码后的字符串
     */
    private static String encodeWithBase64(byte[] data) {
        return new String(Base64.encodeBase64(data));
    }

    /**
     * BASE64Encoder 解码
     *
     * @param data 要解码的字符串
     * @return 解码后的数据源
     */
    public static byte[] decodeWithBase64(String data) {
        return Base64.decodeBase64(data);
    }

    /**
     * 生成密钥对并保存在指定的位置
     *
     * @param publicPath  公钥保存位置
     * @param privatePath 私钥保存位置
     * @throws Exception 生成中的异常
     */
    public static void generateSaveKeyPair(String publicPath, String privatePath) throws Exception {
        // 获取指定算法的密钥对生成器
        KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);

        // 初始化密钥对生成器（指定密钥长度, 使用默认的安全随机数源）
        gen.initialize(KEY_SIZE);

        // 随机生成一对密钥（包含公钥和私钥）
        KeyPair keyPair = gen.generateKeyPair();

        // 保存之
        saveKey(keyPair.getPublic(), publicPath, false);
        saveKey(keyPair.getPrivate(), privatePath, true);
    }

    /**
     * 将 公钥/私钥 编码后以 Base64 的格式保存到指定文件
     *
     * @param key  密钥
     * @param path 保存路径
     * @throws IOException 生成中的异常
     */
    private static void saveKey(Key key, String path, boolean isPrivate) throws IOException {
        // 获取密钥编码后的格式
        byte[] encBytes = key.getEncoded();

        // 转换为 Base64 文本
        String encodedStr = encodeWithBase64(encBytes);

        //格式化
        StringBuilder sb = new StringBuilder();
        if (isPrivate) {
            sb.append("-----BEGIN PRIVATE KEY-----\n");
        } else {
            sb.append("-----BEGIN PUBLIC KEY-----\n");
        }

        int len = encodedStr.length();

        int pos = 0;
        while (0 != len) {
            if (KEY_LINE_SIZE <= len) {
                sb.append(encodedStr, pos, pos + KEY_LINE_SIZE).append("\n");
                pos += KEY_LINE_SIZE;
                len -= KEY_LINE_SIZE;
            } else {
                sb.append(encodedStr, pos, pos + len).append("\n");
                len = 0;
            }

        }
        if (isPrivate) {
            sb.append("-----END PRIVATE KEY-----");
        } else {
            sb.append("-----END PUBLIC KEY-----");
        }

        // 保存到文件
        FileUtil.writeFile(sb.toString(), path);
    }

    /**
     * 从字符串加载私钥
     * 支持任意格式字符串
     *
     * @param key     密钥字符串
     * @param charset 字符串编码格式
     * @throws IOException 加载数据流异常
     */
    private static byte[] loadPem(final String key, final Charset charset) throws IOException {
        InputStream in = new ByteArrayInputStream(key.getBytes(charset));
        InputStreamReader isr = new InputStreamReader(in, charset);

        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        StringBuilder res = new StringBuilder();
        while (null != s) {
            if (!s.startsWith("-----")) {
                res.append(s);
            }
            s = br.readLine();
        }
        log.debug("加载的密钥为(请根据输出位置自行判断是公钥私钥，输出信息仅供调试使用): \n{}", res);
        return decodeWithBase64(res.toString());
    }

    /**
     * 从文件加载私钥
     *
     * @param path 路径
     * @throws Exception 加载异常
     */
    public static RSAPrivateKey loadPrivateKey(String path) throws Exception {
        String key = FileUtil.readFile(new File(path));
        java.security.Security.addProvider(new BouncyCastleProvider());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(loadPem(key, StandardCharsets.UTF_8));
        return (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(spec);
    }

    /**
     * 从文件加载公钥
     *
     * @param path 路径
     * @throws Exception 加载异常
     */
    public static RSAPublicKey loadPublicKey(String path) throws Exception {
        String key = FileUtil.readFile(new File(path));
        // 把 公钥的Base64文本 转换为已编码的 公钥bytes  创建 已编码的公钥规格
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(loadPem(key, StandardCharsets.UTF_8));
        // 获取指定算法的密钥工厂, 根据已编码的公钥规格, 生成公钥对象
        return (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(encPubKeySpec);
    }

    /**
     * 从字符串加载私钥
     * 支持任意格式字符串
     *
     * @param key     私钥字符串
     * @param charset 字符串编码
     * @throws Exception 加载异常
     */
    public static RSAPrivateKey loadPrivateKey(String key, Charset charset) throws Exception {
        java.security.Security.addProvider(new BouncyCastleProvider());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(loadPem(key, charset));
        return (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(spec);
    }

    /**
     * 从字符串加载公钥
     * 支持任意格式字符串
     *
     * @param key     公钥字符串
     * @param charset 字符串编码
     * @throws Exception 加载异常
     */
    public static RSAPublicKey loadPublicKey(String key, Charset charset) throws Exception {
        // 把 公钥的Base64文本 转换为已编码的 公钥bytes  创建 已编码的公钥规格
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(loadPem(key, charset));
        // 获取指定算法的密钥工厂, 根据已编码的公钥规格, 生成公钥对象
        return (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(encPubKeySpec);
    }

    /**
     * 用私钥对信息进行数字签名
     *
     * @param str     签名字符串
     * @param path    私钥存放路径
     * @param charset str的编码格式
     * @return 生成的签名
     * @throws Exception 加密异常
     */
    public static String signWithPrivateKey(String str, String path, Charset charset) throws Exception {

        return signWithPrivateKey(str.getBytes(charset), path);

    }

    /**
     * 用私钥对信息进行数字签名
     *
     * @param data 签名数据
     * @param path 私钥存放路径
     * @return 生成的签名
     * @throws Exception 加密异常
     */
    public static String signWithPrivateKey(byte[] data, String path) throws Exception {

        // 加载私钥
        RSAPrivateKey privateKey = loadPrivateKey(path);
        // 用私钥对信息进行数字签名
        return signWithPrivateKey(data, privateKey);

    }

    /**
     * 用私钥对信息进行数字签名
     *
     * @param data       签名数据
     * @param privateKey 私钥
     * @return 生成的签名
     * @throws Exception 加密异常
     */
    public static String signWithPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
        // 用私钥对信息进行数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return encodeWithBase64(signature.sign());

    }

    /**
     * 用公钥对信息进行验签
     *
     * @param data 待校验数据
     * @param sign 旧的签名
     * @param path 公钥存放路径
     * @return 生成的签名
     * @throws Exception 加密异常
     */
    public static boolean verifyWithPublicKey(byte[] data, String sign, String path) throws Exception {
        // 加载公钥钥
        RSAPublicKey publicKey = loadPublicKey(path);
        // 验签
        return verifyWithPublicKey(data, sign, publicKey);
    }

    /**
     * 用公钥对信息进行验签
     *
     * @param data      待校验数据
     * @param sign      旧的签名
     * @param publicKey 公钥
     * @return 生成的签名
     * @throws Exception 加密异常
     */
    public static boolean verifyWithPublicKey(byte[] data, String sign, RSAPublicKey publicKey) throws Exception {
        // 验签
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        // 验证签名
        return signature.verify(decodeWithBase64(sign));
    }

    /**
     * RSA公钥加密
     *
     * @param str     待加密字符串
     * @param charset 字符串编码
     * @param path    公钥路径
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptWithPublicKey(String str, Charset charset, String path) throws Exception {
        // 加载公钥钥
        RSAPublicKey publicKey = loadPublicKey(path);

        return encryptWithPublicKey(str, charset, publicKey);
    }

    /**
     * RSA公钥加密
     *
     * @param str       待加密字符串
     * @param charset   字符串编码
     * @param publicKey 公钥路径
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptWithPublicKey(String str, Charset charset, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] data = str.getBytes(charset);
        return encodeWithBase64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data, publicKey.getModulus().bitLength()));
    }

    /**
     * RSA私钥解密
     *
     * @param encryptStr 已加密字符串
     * @param charset    编码格式
     * @param path       公钥路径
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptWithPrivateKey(String encryptStr, Charset charset, String path) throws Exception {
        // 加载私钥
        RSAPrivateKey privateKey = loadPrivateKey(path);

        // RSA解密
        return decryptWithPrivateKey(encryptStr, charset, privateKey);
    }

    /**
     * RSA私钥解密
     *
     * @param encryptStr 已加密字符串
     * @param charset    编码格式
     * @param privateKey 公钥路径
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptWithPrivateKey(String encryptStr, Charset charset, RSAPrivateKey privateKey) throws Exception {
        // RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 64位解码加密后的字符串
        byte[] data = decodeWithBase64(encryptStr);
        data = rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, data, privateKey.getModulus().bitLength());
        return new String(data, charset);
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int mode, byte[] data, int keySize) throws IOException {
        int maxBlock;
        if (mode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (data.length > offSet) {
                if (data.length - offSet > maxBlock) {
                    buff = cipher.doFinal(data, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(data, offSet, data.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密区间为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultData = out.toByteArray();
        out.close();
        return resultData;
    }
}
