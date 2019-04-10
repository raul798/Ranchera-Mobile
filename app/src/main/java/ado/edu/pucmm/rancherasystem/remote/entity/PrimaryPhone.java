package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrimaryPhone {

    @SerializedName("freeFormNumber")
    @Expose
    private String freeFormNumber;

    public String getFreeFormNumber() {
        return freeFormNumber;
    }

    public void setFreeFormNumber(String freeFormNumber) {
        this.freeFormNumber = freeFormNumber;
    }
}
