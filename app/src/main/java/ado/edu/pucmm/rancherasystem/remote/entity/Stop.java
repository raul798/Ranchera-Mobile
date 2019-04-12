package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {
    private int stop;
    private int priority;
    private String name;
    private String address;

    public Stop(int id, int priority, String name, String address) {
        this.stop = id;
        this.priority = priority;
        this.name = name;
        this.address = address;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @SerializedName("id")
    @Expose
    private int stopId;

    @SerializedName("priority")
    @Expose
    private int stopPriority;

    @SerializedName("client")
    @Expose
    private int clientId;

    @SerializedName("name")
    @Expose
    private String clientName;

    @SerializedName("address")
    @Expose
    private String stopAddress;

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRouteAddress() {
        return stopAddress;
    }

    public void setRouteAddress(String stopAddress) {
        this.stopAddress = stopAddress;
    }
}
