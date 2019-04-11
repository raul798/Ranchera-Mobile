package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemEntity {

    private Integer idItem;
    private String itemName;
    private String quantity;
    private Float price;
    private String itemDescription;

    public ItemEntity(Integer idItem, String name, String quantity, float price, String description) {
        this.idItem = idItem;
        this.itemName = name;
        this.quantity = quantity;
        this.price = price;
        this.itemDescription = description;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String description) {
        this.itemDescription = description;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("incomeAccountRef")
    @Expose
    private IncomeAccountRef incomeAccountRef;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionItem) {
        this.description = descriptionItem;
    }

    public IncomeAccountRef getIncomeAccountRef() {
        return incomeAccountRef;
    }

    public void setIncomeAccountRef(IncomeAccountRef incomeAccountRef) {
        this.incomeAccountRef = incomeAccountRef;
    }
}
