package com.funpay.demo.pojo;

import lombok.Data;

/**
 * @author FunPay
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T result;

    @Override
    public String toString() {
        String ret;
        if (null == result){
            ret = "null";
        } else {
            ret = result.toString();
        }
        return "Result:\n{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + ret +
                '}';
    }
}
