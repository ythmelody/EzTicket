package com.ezticket.web.product.pojo;

import com.ezticket.core.pojo.Core;
import com.ezticket.web.users.pojo.Host;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;


@Entity
public class Product extends Core {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productno;


    private Integer pclassno;
    private String pname;
    private Integer hostno;
    private String pdiscrip;
    private Integer pprice;
    private Integer pspecialprice;
    private Integer pqty;
    private Timestamp psdate;
    private Timestamp pedate;
    private String ptag;
    private Integer pstatus;
    private Integer pratetotal;
    private Integer prateqty;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "productno", referencedColumnName = "productno")
    private List<Pimgt> pimgts;

    @ManyToOne
    @JoinColumn(name = "pclassno", insertable = false, updatable = false)
    private Pclass pclass;

    @ManyToOne
    @JoinColumn(name = "hostno", insertable = false, updatable = false)
    private Host host;


    public Product() {
    }

    public Integer getProductno() {
        return productno;
    }

    public void setProductno(Integer productno) {
        this.productno = productno;
    }

    public Integer getPclassno() {
        return pclassno;
    }

    public void setPclassno(Integer pclassno) {
        this.pclassno = pclassno;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getHostno() {
        return hostno;
    }

    public void setHostno(Integer hostno) {
        this.hostno = hostno;
    }

    public String getPdiscrip() {
        return pdiscrip;
    }

    public void setPdiscrip(String pdiscrip) {
        this.pdiscrip = pdiscrip;
    }

    public Integer getPprice() {
        return pprice;
    }

    public void setPprice(Integer pprice) {
        this.pprice = pprice;
    }

    public Integer getPspecialprice() {
        return pspecialprice;
    }

    public void setPspecialprice(Integer pspecialprice) {
        this.pspecialprice = pspecialprice;
    }

    public Integer getPqty() {
        return pqty;
    }

    public void setPqty(Integer pqty) {
        this.pqty = pqty;
    }

    public Timestamp getPsdate() {
        return psdate;
    }

    public void setPsdate(Timestamp psdate) {
        this.psdate = psdate;
    }

    public Timestamp getPedate() {
        return pedate;
    }

    public void setPedate(Timestamp pedate) {
        this.pedate = pedate;
    }

    public String getPtag() {
        return ptag;
    }

    public void setPtag(String ptag) {
        this.ptag = ptag;
    }

    public int getPstatus() {
        return pstatus;
    }

    public void setPstatus(int pstatus) {
        this.pstatus = pstatus;
    }

    public Integer getPratetotal() {
        return pratetotal;
    }

    public void setPratetotal(Integer pratetotal) {
        this.pratetotal = pratetotal;
    }

    public Integer getPrateqty() {
        return prateqty;
    }

    public void setPrateqty(Integer prateqty) {
        this.prateqty = prateqty;
    }

    public List<Pimgt> getPimgts() {
        return pimgts;
    }

    public void setPimgts(List<Pimgt> pimgts) {
        this.pimgts = pimgts;
    }

    public Pclass getPclass() {
        return pclass;
    }

    public void setPclass(Pclass pclass) {
        this.pclass = pclass;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
