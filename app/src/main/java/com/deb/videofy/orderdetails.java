package com.deb.videofy;

public class orderdetails {
    private String amount,currency,description;
    private Boolean payment_capture;

    public orderdetails(String amount, String currency, String description, Boolean payment_capture) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.payment_capture = payment_capture;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPayment_capture() {
        return payment_capture;
    }

    public void setPayment_capture(Boolean payment_capture) {
        this.payment_capture = payment_capture;
    }
}
