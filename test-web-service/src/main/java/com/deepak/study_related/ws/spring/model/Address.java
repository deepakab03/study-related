package com.deepak.study_related.ws.spring.model;

public class Address {

    private String country;
    private String city;
    private String localityName1;
    private String localityName2;
    private String streetName;
    private String houseName;
    private String houseNumber;

    private ContactInfo contactInfo;
    private int id;
    
    public Address() {
    }
    
    public Address(String houseName) {
        this.houseName = houseName;
    }
    
    public static Address sampleAddress(String houseName) {
        Address address = new Address(houseName);
        address.city = "Blore";
        address.country = "India";
        if (null == houseName) {
            houseName = "Xanadu";
        }
        address.localityName1 = "Blah location1";
        address.localityName2 = "zzzz";
        address.streetName = "RR";
        address.houseNumber = "327";
        address.contactInfo = new ContactInfo("+91-97766", "9723972", "2972973", "ab@abcccc.com");
        return address;
    }
    
    public static Address sampleAddress(String houseName, int id) {
        Address address = sampleAddress(houseName);
        address.id = id;
        address.houseNumber += "_" + id;
        return address;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalityName1() {
        return localityName1;
    }

    public void setLocalityName1(String localityName1) {
        this.localityName1 = localityName1;
    }

    public String getLocalityName2() {
        return localityName2;
    }

    public void setLocalityName2(String localityName2) {
        this.localityName2 = localityName2;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int counter) {
        this.id = counter;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

}
