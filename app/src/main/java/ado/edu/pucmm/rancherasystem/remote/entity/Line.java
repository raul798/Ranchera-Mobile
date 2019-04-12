package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "lineNum": 1,
    "description": "Test",
    "amount": 1998,
    "detailType": "SALES_ITEM_LINE_DETAIL",
    "salesItemLineDetail": {
       ...
    }
}
 */
public class Line {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("lineNum")
    @Expose
    private Integer lineNum;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("amount")
    @Expose
    private float amount;

    @SerializedName("detailType")
    @Expose
    private String detailType;

    @SerializedName("salesItemLineDetail")
    @Expose
    private SalesItemLineDetail salesItemLineDetail;

    public Line(int lineNum, String description, float amount, String detailType, SalesItemLineDetail salesItemLineDetail) {
        this.lineNum = lineNum;
        this.description = description;
        this.amount = amount;
        this.detailType = detailType;
        this.salesItemLineDetail = salesItemLineDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public SalesItemLineDetail getSalesItemLineDetail() {
        return salesItemLineDetail;
    }

    public void setSalesItemLineDetail(SalesItemLineDetail salesItemLineDetail) {
        this.salesItemLineDetail = salesItemLineDetail;
    }
}


