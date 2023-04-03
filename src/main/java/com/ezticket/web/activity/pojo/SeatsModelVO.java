package com.ezticket.web.activity.pojo;

public class SeatsModelVO {
    private Integer seatModelno;
    private Integer blockno;
    private Integer x;
    private Integer y;
    private String realx;
    private String realy;
    private Integer seatStatus;

    public Integer getSeatModelno() {
        return seatModelno;
    }
    public void setSeatModelno(Integer seatModelno) {
        this.seatModelno = seatModelno;
    }
    public Integer getBlockno() {
        return blockno;
    }
    public void setBlockno(Integer blockno) {
        this.blockno = blockno;
    }
    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public String getRealx() {
        return realx;
    }
    public void setRealx(String realx) {
        this.realx = realx;
    }
    public String getRealy() {
        return realy;
    }
    public void setRealy(String realy) {
        this.realy = realy;
    }
    public Integer getSeatStatus() {
        return seatStatus;
    }
    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
    }
}
