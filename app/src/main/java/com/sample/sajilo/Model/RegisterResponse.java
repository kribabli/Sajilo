package com.sample.sajilo.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("UserLogin")
    private UserRegister UserLogin;

    public RegisterResponse(String responseCode, String result, String responseMsg, UserRegister userLogin) {
        ResponseCode = responseCode;
        Result = result;
        ResponseMsg = responseMsg;
        UserLogin = userLogin;
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

    public UserRegister getUserLogin() {
        return UserLogin;
    }

    public void setUserLogin(UserRegister userLogin) {
        UserLogin = userLogin;
    }
}
