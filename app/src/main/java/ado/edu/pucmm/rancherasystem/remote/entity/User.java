package ado.edu.pucmm.rancherasystem.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("syncToken")
    @Expose
    private String syncToken;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    @SerializedName("primaryEmailAddr")
    @Expose
    private PrimaryEmailAddr primaryEmailAddr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSyncToken() {
        return syncToken;
    }

    public void setSyncToken(String syncToken) {
        this.syncToken = syncToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public PrimaryEmailAddr getPrimaryEmailAddr() {
        return primaryEmailAddr;
    }

    public void setPrimaryEmailAddr(PrimaryEmailAddr primaryEmailAddr) {
        this.primaryEmailAddr = primaryEmailAddr;
    }
}


