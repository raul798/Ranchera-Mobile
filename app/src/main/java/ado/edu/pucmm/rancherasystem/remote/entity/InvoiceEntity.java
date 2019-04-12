package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
{
    "line": [
        ...
    ],
    "customerRef": {"value": "24"},
    "customerMemo": {
        "value": "Test"
    }
}
 */

public class InvoiceEntity {
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

    public InvoiceEntity(List<Line> line, CustomerRef customerReference, CustomerMemo customerMemo) {
        this.lineList = line;
        this.customerRef = customerReference;
        this.customerMemo = customerMemo;
    }

    public List<Line> getLines() {
        return lineList;
    }

    public void setLines(List<Line> line) {
        this.lineList = line;
    }

    public CustomerRef getCustomerReference() {
        return customerRef;
    }

    public void setCustomerReference(CustomerRef customerReference) {
        this.customerRef = customerReference;
    }

    public CustomerMemo getCustomerMemo() {
        return customerMemo;
    }

    public void setCustomerMemo(CustomerMemo customerMemo) {
        this.customerMemo = customerMemo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerRef getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(CustomerRef customerRef) {
        this.customerRef = customerRef;
    }

}
