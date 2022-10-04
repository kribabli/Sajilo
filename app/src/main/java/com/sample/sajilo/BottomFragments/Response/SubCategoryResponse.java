package com.sample.sajilo.BottomFragments.Response;

public class SubCategoryResponse {


    String id;
    String category;
    String name;
    String cat_img;

    public SubCategoryResponse(String id, String category, String name, String cat_img) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cat_img = cat_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }



}
