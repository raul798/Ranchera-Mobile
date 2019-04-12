package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAccountRef {

      @SerializedName("value")
    @Expose
    private String value;

    public ItemAccountRef(String accountValue){
        this.value = accountValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
