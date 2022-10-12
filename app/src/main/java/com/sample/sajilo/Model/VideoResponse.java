package com.sample.sajilo.Model;

public class VideoResponse {

    String id;
    String vedio;
    String status;
    String date;


    public VideoResponse(String id, String vedio, String status, String date) {
        this.id = id;
        this.vedio = vedio;
        this.status = status;
        this.date = date;
    }


    VideoResponse(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
