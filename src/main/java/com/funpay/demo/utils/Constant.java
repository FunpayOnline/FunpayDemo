package com.funpay.demo.utils;

/**
 * @author FunPay
 */
public class Constant {
    /**
     * 阿里MiniU生成的私钥填写在这
     */
    public static final String MERCHANT_PRIVATE_KEY =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDst4wm2NVI1bSY/Z8kOtCLyxWg8umulh+Qs9wtSbyBecA7F/" +
            "NNsfwVd+LXXt2hyyxgXaDYoXM16abVVvBekG9RqftVhgWDrkcML/5H45nOwshPphmKSqWXd+/9ZJV5JR6kvwfKvnvbw60ktZt8" +
            "dzr+7zw2Nc/bH2vHBWL/SsZe4dUKUuTgYm8gUtefZw6kkgD8pRCA0XHsb9s2iS1sqzese5pIG03irt1TPkHqLkJx5ADNwi8QOJ" +
            "S0ULegZJ8VwhKCCBDvaKKt8v3N655GA2Oo3R8vgVpOe0oR+FOEDq5fKqZuXzNR3fOfOLg8oDj90c1MVqkpavmLQ30kL/JFs0Jt" +
            "AgMBAAECggEAd8Pt7wGp963YL8eyKtxvAyt/B+2e+awO0GGPtoImE8QL1ForyVWR2zLK3His3d0z2JXGeZRF/DL5K9tOximBiO" +
            "ndtcmXJKaaX7owhyLKgOff/Rxms6dM3LPcAnJPhfmX5XaZdKSneesowPf5cSHKAY39Ath7D7x4BTveeJfWgc37prhyqu3CAUtD" +
            "bM0DaO1cwcLkxP+u+Yrk85QCgKa+p43ClBvLtHGTPI34q4q4jL55WFR5P1Gi8MNQMq8wLQYgtUVg++bS3dr3c9l/oknmwQiuHY" +
            "jvCspOLk0oAwDBL76EKmlP/3UKdO/QAyYiMefXniSw8DEkocOgkbjZDNadQQKBgQD33ieTayCG+nSpgHeIpC0P5WV9SfQIDa7G" +
            "bdWzRzJ9ss31jL7iKEaqZU9NqgChY4No/0uUpvTrLZzSXYHEI3HPaVUsv8iUVr+PHiAm1cL5SEoLX+6FB6qgKy9JVocrALt0Cj" +
            "/38smE7Jomi6mrt+HvKfMRSjnjMezhUBqCDYexmQKBgQD0e7yvwyuqDPeDfq7qboPfeqlfy77i726HUsTg9uA0FQl0ueQmjLTh" +
            "7HzxLoCzwnXa2JgZM14ca9VBsUPF7e0GAA3OqqGn7eCsOqZ9I0/FgQ1BQLAW+yxwVPqefVLbbSL+v52Bzu2RSUb+nySOamDDHM" +
            "Anrz8CSKjoaA+8Db2D9QKBgD3I0nScr2713z6hFyqoQdNEixe/52RahMZReoNFrABJIa2jd7FH+NVgi3QAfEe+VnNFr8FOHJ7C" +
            "r+SACmiprSTWTLTq2USgmv9BsyH7Bq68pjhPoVTnpFBoc1EEkIzXkeJK68cSbckEcShKh/CV1WuYOU6GAgfv2ewP24uvc7OBAo" +
            "GAPhncMO0rfzNx/7XDKotN9gtLUKkJYM2ezsLofZhYFQKu9zZszJn7LFQM3JqKWJ7xnthbsjMJGcEOV3R2maU5Csgiubc//s7+" +
            "veDb/3djwPBp9IRA9mVwwUvkqTN+Q3xyYjE8CggM8lWhsfWsrHw6/fSknq/2GldoiDg+nu8Cj60CgYEAwnCFeYQOprhTVYJaIg" +
            "biuKBUyLPUesThqTIqQBKiOV+Cp8kbI0CuBsoxACKHClvvioJo2hfJ13AhbFZFm4PaguKLPuOBfxHmb7MNE/NtzT/P29h0mwr0" +
            "8hoR36Vgwg60EFgwmiPqYtIwewFlW5vRA44iJ/7SBiwCrUkzviJDtTs=";
    /**
     * 阿里MiniU生成的公钥填写在这
     */
    public static final String MERCHANT_PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7LeMJtjVSNW0mP2fJDrQi8sVoPLprpYfkLPcLUm8gXnAOxfzTbH8FX" +
            "fi117docssYF2g2KFzNemm1VbwXpBvUan7VYYFg65HDC/+R+OZzsLIT6YZikqll3fv/WSVeSUepL8Hyr5728OtJLWbfHc6/u88" +
            "NjXP2x9rxwVi/0rGXuHVClLk4GJvIFLXn2cOpJIA/KUQgNFx7G/bNoktbKs3rHuaSBtN4q7dUz5B6i5CceQAzcIvEDiUtFC3oG" +
            "SfFcISgggQ72iirfL9zeueRgNjqN0fL4FaTntKEfhThA6uXyqmbl8zUd3znzi4PKA4/dHNTFapKWr5i0N9JC/yRbNCbQIDAQAB";


    /**
     * 如果希望测试本地密钥对，则公钥路径填写在这
     * 如果希望本工具自动生成密钥对，则生成的公钥保存路径也写在这
     */
    public static final String LOCALE_PUBLIC_KEY = "./publicKey.pem";

    /**
     * 如果希望测试本地密钥对，则私钥路径填写在这
     * 如果希望本工具自动生成密钥对，则生成的私钥保存路径也写在这
     */
    public static final String LOCALE_PRIVATE_KEY = "./privateKey.pem";


    /**
     * Funpay返回公钥存储在这
     */
    public static final String FUNPAY_PUBLIC_KEY = "./FunpayKey.pem";


    /**
     * Api域名
     */
    public static final String DOMAIN = "https://sandbox.funpay.asia";

    /**
     * 打款接口地址
     */
    public static final String TRANSFER_LINK = DOMAIN + "/fun/transfer/api/transferMoney";

    /**
     * 获取银行列表接口地址
     */
    public static final String GET_BANK_LIST_URL = DOMAIN + "/fun/transfer/api/getBankList";

    /**
     * 核验银行卡信息接口地址
     */
    public static final String VERIFY_BANK_INFO_URL = DOMAIN + "/fun/transfer/api/verifyBankInfo";

    /**
     * 打款结果查询接口地址
     */
    public static final String TRANSFER_RESULT_CHECK = DOMAIN + "/fun/transfer/api/check";

    /**
     * 打款批量查询接口地址
     */
    public static final String BULK_QUERY = DOMAIN + "/fun/transfer/api/query";


    /**
     * 回调地址，请以实际为准
     */
    public static final String RETURN_URL = "www.bing.com";

    public static final String SECRET_KEY = "5xlNy8xxfvcs73ADJmmHMUHwahRUAAzpXrcHOBwC";
    public static final int MERCHANT = 10086;
    public static final int BUSINESS = 10000;
    public static final int FEE_ID = 10010;

    public static final String CURRENCY = "VND";
    public static final String CLIENT_ID = "FUNPAY-TEST";


}
