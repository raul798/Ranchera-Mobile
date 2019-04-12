package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
    "itemRef": {"value": "5" },
    "unitPrice": 999,
    "qty": 2,
    "itemAccountRef": {"value": "79"},
    "taxCodeRef": {"value": "TAX"}
 */
public class SalesItemLineDetail {


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

    public SalesItemLineDetail(ItemRef itemRef, float unitPricing, float qty, ItemAccountRef itemAccountRef, TaxCodeRef taxCodeRef) {
        this.itemRef = itemRef;
        this.unitPrice = unitPricing;
        this.qty = qty;
        this.itemAccountRef = itemAccountRef;
        this.taxCodeRef = taxCodeRef;
    }

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
