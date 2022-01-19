package com.funpay.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * HttpUtil
 * HTTP工具类
 * Created by Common on 2020/11/11.
 *
 * @author FunPay
 */
public class HttpUtil {

    private static final String CHARSET = "utf-8";
    private static final String CONTENT_TYPE = "application/json;charset=" + CHARSET;
    private static final String SSL_PROTOCOLS = "TLSv1.3,TLSv1.2,TLSv1.1,SSLv3";
    private static final int READ_TIMEOUT = 180 * 1000;
    private static final int CONNECTION_TIMEOUT = 3 * 1000;

    /**
     * 创建一个HTTPS网络连接
     *
     * @param url    get请求的地址，不包含参数部分
     * @param params get请求的参数，会拼接在url后面构成完成地址
     * @return 创建好的连接
     * @throws IOException              读写流异常
     * @throws NoSuchAlgorithmException 不支持SSL
     * @throws KeyManagementException   密钥对异常
     */
    private static HttpsURLConnection createConnection(String url, Map<String, String> params)
            throws IOException, NoSuchAlgorithmException, KeyManagementException {
        // 开启SSL
        System.setProperty("https.protocols", SSL_PROTOCOLS);
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // 拼接完整URL
        StringBuilder sb = new StringBuilder();
        if (null != params && params.keySet().size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (sb.length() != 0) {
                    sb.append("&");
                } else {
                    sb.append("?");
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        sb.insert(0, url);
        URL requestUrl = new URL(sb.toString());

        // 设置代理
        /* Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        HttpsURLConnection conn = (HttpsURLConnection) requestUrl.openConnection(proxy);*/
        HttpsURLConnection conn = (HttpsURLConnection) requestUrl.openConnection();
        conn.setReadTimeout(READ_TIMEOUT);
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", CONTENT_TYPE);

        return conn;
    }

    /**
     * HTTPS的get方法
     *
     * @param url    get请求的地址，不包含参数部分
     * @param params get请求的参数，会拼接在url后面构成完成地址
     * @return 响应结果
     */
    public static Response doGet(String url, Map<String, String> params) {

        try {
            DemoUtil.apiAddress(url);
            HttpsURLConnection conn = createConnection(url, params);
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                return new DefaultResponse(200, outStream.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTPS的post方法
     *
     * @param url        post请求的地址，不包含参数部分
     * @param params     post请求的参数，会拼接在url后面构成完成地址, 一般为空、
     * @param jsonString json对象字符串
     * @return 响应结果
     */
    public static Response doPost(String url, Map<String, String> params, String jsonString) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        DemoUtil.apiAddress(url);
        // 开启SSL
        HttpsURLConnection conn = createConnection(url, params);
        conn.setRequestMethod("POST");
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(jsonString.getBytes(StandardCharsets.UTF_8));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return new DefaultResponse(200, outStream.toString("UTF-8"));
        }
        return null;
    }

    public interface Response {
        /**
         * 获取状态码
         *
         * @return
         */
        int getStatusCode();

        /**
         * 获取返回的内容
         *
         * @return
         */
        String getContent();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslsession) {
            return true;
        }
    }

    @Data
    @AllArgsConstructor
    public static class DefaultResponse implements Response {
        private final int statusCode;
        private final String content;

        @Override
        public String toString() {
            return "DefaultResponse [statusCode=" + statusCode + ", content=" + content + "]";
        }
    }
}
