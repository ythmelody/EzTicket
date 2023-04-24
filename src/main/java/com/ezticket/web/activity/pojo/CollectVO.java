package com.ezticket.web.activity.pojo;

import org.apache.commons.lang3.RandomStringUtils;

public class CollectVO {
    private Integer collectno;
    private Integer memberno;
    private Integer tDetailsno;
    private Integer tStatus;
    private byte[] qrcode;

    public Integer getCollectno() {
        return collectno;
    }

    public void setCollectno(Integer collectno) {
        this.collectno = collectno;
    }

    public Integer getMemberno() {
        return memberno;
    }

    public void setMemberno(Integer memberno) {
        this.memberno = memberno;
    }

    public Integer gettDetailsno() {
        return tDetailsno;
    }

    public void settDetailsno(Integer tDetailsno) {
        this.tDetailsno = tDetailsno;
    }

    public Integer gettStatus() {
        return tStatus;
    }

    public void settStatus(Integer tStatus) {
        this.tStatus = tStatus;
    }

    public byte[] getQrcode() {
        return qrcode;
    }

    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
    }

//    測試 Apache Commons.lang3 工具，亂數產生字串
//    public static void main(String[] args){
//        String str = RandomStringUtils.randomAlphanumeric(10, 50);
//        System.out.println(str);
//    }
}
