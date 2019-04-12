package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceEntity {

    public InvoiceEntity(String id, List<Line> lineList, CustomerRef customerRef, CustomerMemo customerMemo) {
        this.id = id;
        this.lineList = lineList;
        this.customerRef = customerRef;
        this.customerMemo = customerMemo;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("line")
    @Expose
    private List<Line> lineList;

    @SerializedName("customerRef")
    @Expose
    private CustomerRef customerRef;

    @SerializedName("customerMemo")
    @Expose
    private CustomerMemo customerMemo;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Line> getLineList() {
        return lineList;
    }

    public void setLineList(List<Line> lineList) {
        this.lineList = lineList;
    }

    public CustomerRef getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(CustomerRef customerRef) {
        this.customerRef = customerRef;
    }

    public CustomerMemo getCustomerMemo() {
        return customerMemo;
    }

    public void setCustomerMemo(CustomerMemo customerMemo) {
        this.customerMemo = customerMemo;
    }
}
