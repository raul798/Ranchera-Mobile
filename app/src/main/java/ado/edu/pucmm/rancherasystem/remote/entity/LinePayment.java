package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
{
    "amount": 999,
    "linkedTxn": [
        ...
    ]
}
 */
public class LinePayment {

    @SerializedName("amount")
    @Expose
    private float amount;

    @SerializedName("linkedTxn")
    @Expose
    private List<LinkedTxn> linkedTxn;

    public float getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<LinkedTxn> getLinkedTxn() {
        return linkedTxn;
    }

    public void setLinkedTxn(List<LinkedTxn> linkedTxn) {
        this.linkedTxn = linkedTxn;
    }

    public LinePayment() {
    }

    public LinePayment(float amount, List<LinkedTxn> linkedTxn) {
        this.amount = amount;
        this.linkedTxn = linkedTxn;
    }
}
