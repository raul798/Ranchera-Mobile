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
    private boolean status;
    private int user;

    public Route(int clientId, int priority, boolean status, int user) {
        this.clientId = clientId;
        this.priority = priority;
        this.status = status;
        this.user = user;
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

    public void setStatus(boolean status) { this.status = status; }

    public void setUser(int user) { this.user = user; }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isStatus() { return status; }

    public int getUser() { return user; }
}
