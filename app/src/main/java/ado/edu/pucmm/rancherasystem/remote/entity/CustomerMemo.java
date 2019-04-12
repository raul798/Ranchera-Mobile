package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerMemo {

    private String customerMemoD;

    public CustomerMemo(String customerMemoD){
        this.customerMemoD = customerMemoD;
    }

    public String getCustomerMemoD() {
        return customerMemoD;
    }

    public void setCustomerMemoD(String customerMemoD) {
        this.customerMemoD = customerMemoD;
    }

    @SerializedName("customerMemo")
    @Expose
    private String customerMemo;

    public String getCustomerMemo() {
        return customerMemo;
    }

    public void setCustomerMemo(String customerMemo) {
        this.customerMemo = customerMemo;
    }
}
