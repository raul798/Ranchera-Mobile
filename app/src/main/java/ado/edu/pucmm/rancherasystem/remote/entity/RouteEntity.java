package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteEntity {

    private int routeId;
    private int userId;
    private List<Stop> stops;

    public RouteEntity(int routeId, int userId, List<Stop> stops) {
        this.routeId = routeId;
        this.userId = userId;
        this.stops = stops;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user")
    @Expose
    private int user;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("stops")
    @Expose
    private List<Stop> stopList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public void setStopList(List<Stop> stopList) {
        this.stopList = stopList;
    }
}
