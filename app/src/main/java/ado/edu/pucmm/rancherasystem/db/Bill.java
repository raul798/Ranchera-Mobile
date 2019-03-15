package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "bill_table",
        foreignKeys = @ForeignKey(entity = Client.class,
        parentColumns = "id",
        childColumns = "client_id",
        onDelete = ForeignKey.CASCADE))
public class Bill {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private float debt;
    private int client_id;

    public Bill(int id, float debt, String description, int client_id) {
        this.id = id;
        this.debt = debt;
        this.description = description;
        this.client_id = client_id;
    }

    private String description;

    public int getId() {
        return id;
    }

    public float getDebt() {
        return debt;
    }

    public String getDescription() {
        return description;
    }

    public int getClient_id() {
        return client_id;
    }
}
