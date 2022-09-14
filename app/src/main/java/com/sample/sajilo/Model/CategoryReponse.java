package com.sample.sajilo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryReponse {
    @SerializedName("data")
    private ArrayList<CategoryDataResponse> data;

    @SerializedName("ResponseCode")
    String ResponseCode;

    @SerializedName("Result")
    String Result;

    @SerializedName("ResponseMsg")
    String ResponseMsg;

    public ArrayList<CategoryDataResponse> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryDataResponse> data) {
        this.data = data;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
    }
}
