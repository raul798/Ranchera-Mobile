package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "client_table")
public class Client {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    private String name;

    private String phoneNumber;

    private String address;

    private String email;

    public Client(Integer id, @NonNull String name, @NonNull String phoneNumber, @NonNull String address, @NonNull String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
