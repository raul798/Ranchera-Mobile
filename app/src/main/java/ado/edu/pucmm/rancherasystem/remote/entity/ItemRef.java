package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemRef {

    @SerializedName("value")
    @Expose
    private String value;

    public ItemRef(String itemValue){
        this.value = itemValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
