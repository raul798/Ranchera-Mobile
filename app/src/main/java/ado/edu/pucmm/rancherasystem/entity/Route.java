package ado.edu.pucmm.rancherasystem.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Route {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private int clientId;
    private int priority;

    public Route(int clientId, int priority) {
        this.clientId = clientId;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getPriority() {
        return priority;
    }
}
