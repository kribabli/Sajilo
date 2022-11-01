package com.sample.sajilo.Model;

public class VendorsStoreData {
    String id,title,store_image,Is_OPEN,address,mobile,star,Total_items;

    public VendorsStoreData(String id, String title, String store_image, String is_OPEN, String address, String mobile, String star, String total_items) {
        this.id = id;
        this.title = title;
        this.store_image = store_image;
        Is_OPEN = is_OPEN;
        this.address = address;
        this.mobile = mobile;
        this.star = star;
        Total_items = total_items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStore_image() {
        return store_image;
    }

    public void setStore_image(String store_image) {
        this.store_image = store_image;
    }

    public String getIs_OPEN() {
        return Is_OPEN;
    }

    public void setIs_OPEN(String is_OPEN) {
        Is_OPEN = is_OPEN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTotal_items() {
        return Total_items;
    }

    public void setTotal_items(String total_items) {
        Total_items = total_items;
    }
}
