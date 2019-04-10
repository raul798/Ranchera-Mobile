package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipAddr {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("line1")
    @Expose
    private String line1;

    @SerializedName("city")
    @Expose
    private String city;

    public String getLine1() {
        return line1;
    }

    public String getCity() {
        return city;
    }

    public int getId(){ return id; }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
