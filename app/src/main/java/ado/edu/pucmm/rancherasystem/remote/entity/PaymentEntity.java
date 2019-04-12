package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentEntity {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("currencyRef")
    @Expose
    private CurrencyRef currencyRef;

    @SerializedName("line")
    @Expose
    private List<LinePayment> line;

    @SerializedName("customerRef")
    @Expose
    private CustomerRef customerRef;

    @SerializedName("totalAmt")
    @Expose
    private float totalAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CurrencyRef getCurrencyRef() {
        return currencyRef;
    }

    public void setCurrencyRef(CurrencyRef currencyRef) {
        this.currencyRef = currencyRef;
    }

    public List<LinePayment> getLine() {
        return line;
    }

    public void setLine(List<LinePayment> line) {
        this.line = line;
    }

    public CustomerRef getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(CustomerRef customerRef) {
        this.customerRef = customerRef;
    }

    public float getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(float totalAmt) {
        this.totalAmt = totalAmt;
    }
}
