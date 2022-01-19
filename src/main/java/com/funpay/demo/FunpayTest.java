package com.funpay.demo;

import com.funpay.demo.features.*;


/**
 * @author FunPay
 * 注释掉的方法是缺乏查询数据，无法验证
 * 未注释的方法工作正常，验签正常
 * 鼠标悬停方法名即可查看方法接口地址
 */
public class FunpayTest {

    @org.junit.Test
    public void keyGeneratorTest() throws Exception {
         /*
          如果你希望使用Alipay MiniU平台的key 请释放本方法，并将密钥对填入本文件顶端的相应变量中
         */
        // KeyGenerator.testKeyFromAlipay();

        // 如果你希望使用本工具自动生成密钥对，请释放本方法，并将密钥对存储路径填入本文件顶端的相应变量中
        // KeyGenerator.testAutoGen();

        // 如果你希望使用OpenSSL生成的密钥对，请释放本方法，并将密钥对存储路径填入本文件顶端的相应变量中
        // KeyGenerator.testLocaleFile();
    }

    @org.junit.Test
    public void offlinePaymentTest() {
        OfflinePayment.queryBalance();
        OfflinePayment.query();
        OfflinePayment.updateOfflineCode();
        OfflinePayment.offlinePay();
        OfflinePayment.offlineCheck();
    }

    @org.junit.Test
    public void transferTest() {
        Transfer.transferMoney();
        Transfer.getBankList();
        Transfer.verifyBankInfo();
        Transfer.check();
        Transfer.bulkQuery();
    }

    @org.junit.Test
    public void onlinePaymentTest() {
        OnlinePayment.queryBalance();
        OnlinePayment.query();
        OnlinePayment.onlinePay();
    }

    @org.junit.Test
    public void virtualCardTest() {
        VirtualCard.virtualCardQuery();
        VirtualCard.virtualCardQueryVc();
        VirtualCard.virtualCardDestroy();
        VirtualCard.virtualCardCreate();
        VirtualCard.check();
    }

}
