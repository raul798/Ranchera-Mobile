package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxCodeRef {

    @SerializedName("value")
    @Expose
    private String value;

    public TaxCodeRef(String taxValue){
        this.value = taxValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
