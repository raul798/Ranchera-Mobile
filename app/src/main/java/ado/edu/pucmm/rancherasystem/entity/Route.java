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
    private float latitude;
    private float longitude;

    public Route(int clientId, int priority, float latitude, float longitude) {
        this.clientId = clientId;
        this.priority = priority;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
