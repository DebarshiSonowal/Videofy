package com.deb.videofy;

import org.json.JSONObject;

public class customerdata extends JSONObject {
    private String name,description,image,order_id,currency;
    private Integer amount;

    public customerdata(String name, String description, String image, String order_id, String currency, Integer amount) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.order_id = order_id;
        this.currency = currency;
        this.amount = amount;
    }

    public customerdata() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
