package com.sample.sajilo.Model;

public class CategoryDataResponse {

    String id;
    String cat_name;
    String cat_status;
    String cat_img;

    public CategoryDataResponse(String id, String cat_name, String cat_status, String cat_img) {
        this.id = id;
        this.cat_name = cat_name;
        this.cat_status = cat_status;
        this.cat_img = cat_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_status() {
        return cat_status;
    }

    public void setCat_status(String cat_status) {
        this.cat_status = cat_status;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }
}
