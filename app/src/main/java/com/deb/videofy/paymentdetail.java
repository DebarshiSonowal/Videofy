package com.deb.videofy;

public class paymentdetail {
    private String orderid,paymentid,signatureid;

    public paymentdetail(String orderid, String paymentid, String signatureid) {
        this.orderid = orderid;
        this.paymentid = paymentid;
        this.signatureid = signatureid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getSignatureid() {
        return signatureid;
    }

    public void setSignatureid(String signatureid) {
        this.signatureid = signatureid;
    }
}
