package com.sample.sajilo.Model;

public class Datum {




    public String id;
    public String img;
    public String status;
    public String is_main;

    public Datum(String id, String img, String status, String is_main) {
        this.id = id;
        this.img = img;
        this.status = status;
        this.is_main = is_main;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_main() {
        return is_main;
    }

    public void setIs_main(String is_main) {
        this.is_main = is_main;
    }
}
