package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemRef {

    private String itemValue;

    public ItemRef(String itemValue){
        this.itemValue = itemValue;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue){
        this.itemValue = itemValue;
    }

    @SerializedName("value")
    @Expose
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
