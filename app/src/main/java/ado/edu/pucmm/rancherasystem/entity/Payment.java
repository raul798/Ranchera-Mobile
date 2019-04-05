package ado.edu.pucmm.rancherasystem.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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

    public Payment(float amount, int bill, int client) {
        this.amount = amount;
        this.bill = bill;
        this.client = client;
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
}
