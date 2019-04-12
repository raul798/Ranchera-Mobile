package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "txnId": "152",
    "txnType": "Invoice"
}
*/
public class LinkedTxn {


    @SerializedName("txnId")
    @Expose
    private String txnId;


    @SerializedName("txnType")
    @Expose
    private String txnType;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public LinkedTxn(String txnId, String txnType) {
        this.txnId = txnId;
        this.txnType = txnType;
    }
}
