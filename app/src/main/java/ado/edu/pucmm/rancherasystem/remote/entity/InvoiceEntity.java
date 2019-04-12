package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceEntity {

    private List<Line> lines;
    private CustomerRef customerReference;
    private CustomerMemo customerMem;

    public InvoiceEntity(List<Line> line, CustomerRef customerReference, CustomerMemo customerMem) {
        this.lines = line;
        this.customerReference = customerReference;
        this.customerMem = customerMem;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> line) {
        this.lines = line;
    }

    public CustomerRef getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(CustomerRef customerReference) {
        this.customerReference = customerReference;
    }

    public CustomerMemo getCustomerMem() {
        return customerMem;
    }

    public void setCustomerMem(CustomerMemo customerMem) {
        this.customerMem = customerMem;
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
