package com.imooc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Orderxml {
    @Id
    @GeneratedValue
    private Integer id;

    public String orderid;
    public String productid;
    public String num;
    public String ordercash;
    public String productname;
    public String sporderid;
    public String mobile;
    public String resultno;
    public String fundbalance;

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrdercash() {
        return this.ordercash;
    }

    public void setOrdercash(String ordercash) {
        this.ordercash = ordercash;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSporderid() {
        return this.sporderid;
    }

    public void setSporderid(String sporderid) {
        this.sporderid = sporderid;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResultno() {
        return this.resultno;
    }

    public void setResultno(String resultno) {
        this.resultno = resultno;
    }

    public String getFundbalance() {
        return this.fundbalance;
    }

    public void setFundbalance(String fundbalance) {
        this.fundbalance = fundbalance;
    }
}
