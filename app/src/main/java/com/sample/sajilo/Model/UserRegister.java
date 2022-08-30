package com.sample.sajilo.Model;

public class UserRegister {
    public String id;
    public String username;
    public String fname;
    public String lname;
    public String email;
    public String mobile;
    public String password;
    public String ccode;
    public String status;
    public String rdate;
    public String balance;
    public String refferal_code;
    public String refer_to;

    public UserRegister(String id, String username, String fname, String lname, String email, String mobile, String password, String ccode, String status, String rdate, String balance, String refferal_code, String refer_to) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.ccode = ccode;
        this.status = status;
        this.rdate = rdate;
        this.balance = balance;
        this.refferal_code = refferal_code;
        this.refer_to = refer_to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRefferal_code() {
        return refferal_code;
    }

    public void setRefferal_code(String refferal_code) {
        this.refferal_code = refferal_code;
    }

    public String getRefer_to() {
        return refer_to;
    }

    public void setRefer_to(String refer_to) {
        this.refer_to = refer_to;
    }
}
