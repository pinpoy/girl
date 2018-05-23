package com.imooc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class OrderPay {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "price字段必传")
    private String price;
    @NotBlank(message = "mobile字段必传")
    private String mobile;
    @NotBlank(message = "sporderid字段必传")
    private String sporderid;
    @NotBlank(message = "sign字段必传")
    private String sign;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSporderid() {
        return this.sporderid;
    }

    public void setSporderid(String sporderid) {
        this.sporderid = sporderid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "OrderPay{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sporderid='" + sporderid + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
