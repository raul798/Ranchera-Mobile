package ado.edu.pucmm.rancherasystem.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private float amount;
    @NonNull
    private int bill;
    @NonNull
    private int client;

    @Nullable
    private String externalID;

    private byte[] signature;

    public Payment(float amount, int bill, int client) {
        this.amount = amount;
        this.bill = bill;
        this.client = client;
    }

    @Nullable
    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(@Nullable String externalID) {
        this.externalID = externalID;
    }

    public byte[] getSignature() {
        return signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
