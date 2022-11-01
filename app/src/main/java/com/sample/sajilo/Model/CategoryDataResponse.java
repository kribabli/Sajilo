package com.sample.sajilo.Model;

public class CategoryDataResponse {
    String id;
    String name;
    String status;
    String image;
    String pinCode;


    public CategoryDataResponse(String id, String name, String status, String image, String pinCode) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
        this.pinCode = pinCode;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
