package ado.edu.pucmm.rancherasystem;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import java.io.Serializable;


@Entity(tableName = "client_table")
public class Client implements Serializable {

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public Client(String name, String phoneNumber, String email, String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Client() {

    }

    //getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }

    //setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
}
