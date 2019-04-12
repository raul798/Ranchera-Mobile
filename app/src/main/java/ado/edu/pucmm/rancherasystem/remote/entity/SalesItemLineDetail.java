package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesItemLineDetail {

    private ItemRef itemReference;
    private float quantity;
    private ItemAccountRef itemAccountReference;
    private TaxCodeRef taxCodeReference;

    public SalesItemLineDetail(ItemRef itemRef, float qty, ItemAccountRef itemAccountRef, TaxCodeRef taxCodeRef) {
        this.itemReference = itemRef;
        this.quantity = qty;
        this.itemAccountReference = itemAccountRef;
        this.taxCodeReference = taxCodeRef;
    }

    public ItemRef getItemReference() {
        return itemReference;
    }

    public void setItemReference(ItemRef itemReference) {
        this.itemReference = itemReference;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public ItemAccountRef getItemAccountReference() {
        return itemAccountReference;
    }

    public void setItemAccountReference(ItemAccountRef itemAccountReference) {
        this.itemAccountReference = itemAccountReference;
    }

    public TaxCodeRef getTaxCodeReference() {
        return taxCodeReference;
    }

    public void setTaxCodeReference(TaxCodeRef taxCodeReference) {
        this.taxCodeReference = taxCodeReference;
    }

    @SerializedName("itemRef")
    @Expose
    private ItemRef itemRef;

    @SerializedName("unitPrice")
    @Expose
    private float unitPrice;

    @SerializedName("qty")
    @Expose
    private float qty;

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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
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
