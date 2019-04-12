package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {

    @SerializedName("id")
    @Expose
    private int stopId;

    @SerializedName("priority")
    @Expose
    private int stopPriority;

    @SerializedName("client")
    @Expose
    private int clientId;

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getStopPriority() {
        return stopPriority;
    }

    public void setStopPriority(int stopPriority) {
        this.stopPriority = stopPriority;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
