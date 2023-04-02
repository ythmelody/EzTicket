package com.ezticket.web.activity.pojo;

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
}
