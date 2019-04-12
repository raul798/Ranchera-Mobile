package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAccountRef {

    private String accountValue;

    public ItemAccountRef(String accountValue){
        this.accountValue = accountValue;
    }

    public String getAccountValue() {
        return accountValue;
    }

    public void setAccountValue(String accountValue) {
        this.accountValue = accountValue;
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
