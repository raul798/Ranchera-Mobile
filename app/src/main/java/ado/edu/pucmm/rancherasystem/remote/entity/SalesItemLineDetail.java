package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesItemLineDetail {

    @SerializedName("itemRef")
    @Expose
    private ItemRef itemRef;

    @SerializedName("unitPrice")
    @Expose
    private Float unitPrice;

    @SerializedName("qty")
    @Expose
    private Float qty;

    @SerializedName("itemAccountRef")
    @Expose
    private ItemAccountRef itemAccountRef;

    @SerializedName("taxCodeRef")
    @Expose
    private TaxCodeRef taxCodeRef;

    public ItemRef getItemRef() {
        return itemRef;
    }

    public void setItemRef(ItemRef itemRef) {
        this.itemRef = itemRef;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public ItemAccountRef getItemAccountRef() {
        return itemAccountRef;
    }

    public void setItemAccountRef(ItemAccountRef itemAccountRef) {
        this.itemAccountRef = itemAccountRef;
    }

    public TaxCodeRef getTaxCodeRef() {
        return taxCodeRef;
    }

    public void setTaxCodeRef(TaxCodeRef taxCodeRef) {
        this.taxCodeRef = taxCodeRef;
    }
}
