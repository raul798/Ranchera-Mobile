package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerMemo {

    @SerializedName("value")
    @Expose
    private String value;

    public CustomerMemo(String customerMemo){
        this.value = customerMemo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String customerMemo) {
        this.value = customerMemo;
    }
}
