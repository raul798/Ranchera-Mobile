package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerEntity {

    private Integer idCustomer;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public CustomerEntity(Integer id, String name, String phoneNumber, String email, String address) {
        this.idCustomer = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setIdCustomer(Integer id) {
        this.idCustomer = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("givenName")
    @Expose
    private String givenName;

    @SerializedName("familyName")
    @Expose
    private String familyName;

    @SerializedName("primaryPhone")
    @Expose
    private PrimaryPhone primaryPhone;

    @SerializedName("primaryEmailAddr")
    @Expose
    private PrimaryEmailAddr primaryEmailAddr;

    @SerializedName("shipAddr")
    @Expose
    private ShipAddr shipAddr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public PrimaryPhone getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(PrimaryPhone primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public PrimaryEmailAddr getPrimaryEmailAddr() {
        return primaryEmailAddr;
    }

    public void setPrimaryEmailAddr(PrimaryEmailAddr primaryEmailAddr) {
        this.primaryEmailAddr = primaryEmailAddr;
    }

    public ShipAddr getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(ShipAddr shipAddr) {
        this.shipAddr = shipAddr;
    }
}
