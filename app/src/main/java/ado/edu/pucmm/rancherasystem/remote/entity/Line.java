package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {

    private int lineNumber;
    private String lineDescription;
    private float lineAmount;
    private String lineDetailType;
    private SalesItemLineDetail lineSalesItemLineDetail;


    public Line(int lineNum, String description, float amount, String detailType, SalesItemLineDetail salesItemLineDetail) {
        this.lineNumber = lineNum;
        this.lineDescription = description;
        this.lineAmount = amount;
        this.lineDetailType = detailType;
        this.lineSalesItemLineDetail = salesItemLineDetail;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public void setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
    }

    public float getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(float lineAmount) {
        this.lineAmount = lineAmount;
    }

    public String getLineDetailType() {
        return lineDetailType;
    }

    public void setLineDetailType(String lineDetailType) {
        this.lineDetailType = lineDetailType;
    }

    public SalesItemLineDetail getLineSalesItemLineDetail() {
        return lineSalesItemLineDetail;
    }

    public void setLineSalesItemLineDetail(SalesItemLineDetail lineSalesItemLineDetail) {
        this.lineSalesItemLineDetail = lineSalesItemLineDetail;
    }

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
    private Float amount;

    @SerializedName("detailType")
    @Expose
    private String detailType;

    @SerializedName("salesItemLineDetail")
    @Expose
    private SalesItemLineDetail salesItemLineDetail;

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


