package com.funpay.demo.pojo;

import lombok.Data;

/**
 * @author FunPay
 */
@Data
public class TransferResult {
    private int merchantID;
    private int businessID;
    private String sign;
    private int amount;
    private String currency;
    private String orderNo;
    private String tradeNo;
    private String accountName;
    private String accountNo;
    private int accountType;
    private String bankLocation;
    private int bankNo;
    private int bankBranchNo;
    private String sendTime;
    private int status;

    @Override
    public String toString() {
        return "TransferResult{" +
                "merchantID=" + merchantID +
                ", businessID=" + businessID +
                ", sign='" + sign + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", accountType=" + accountType +
                ", bankLocation='" + bankLocation + '\'' +
                ", bankNo=" + bankNo +
                ", bankBranchNo=" + bankBranchNo +
                ", sendTime='" + sendTime + '\'' +
                ", status=" + status +
                '}';
    }
}
