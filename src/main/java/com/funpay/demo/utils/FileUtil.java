package com.funpay.demo.utils;

import java.io.*;

/**
 * FileUtil
 * 文件操作工具类
 * Created by Common on 2020/11/11.
 * @author FunPay
 */

public class FileUtil {

    /**
     * 写入指定文件
     *
     * @throws IOException 文件异常
     */
    public static void writeFile(String data, String path) throws IOException {
        writeFile(data, new File(path));
    }

    /**
     * 写入指定文件
     *
     * @throws IOException 文件异常
     */
    public static void writeFile(String data, File file) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.flush();
        } finally {
            close(out);
        }
    }

    /**
     * 读取指定文件
     *
     * @throws IOException 文件异常
     */
    public static String readFile(File file) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
            return out.toString();
        } finally {
            close(in);
            close(out);
        }
    }

    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
