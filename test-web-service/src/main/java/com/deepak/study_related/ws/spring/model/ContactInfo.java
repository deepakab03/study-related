package com.deepak.study_related.ws.spring.model;

public class ContactInfo {

    private String mobileNumber;
    private String houseNumber;
    private String workNumber;
    private String email;

    public ContactInfo() {
        super();
    }

    public ContactInfo(String mobileNumber, String houseNumber, String workNumber, String email) {
        super();
        this.mobileNumber = mobileNumber;
        this.houseNumber = houseNumber;
        this.workNumber = workNumber;
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
