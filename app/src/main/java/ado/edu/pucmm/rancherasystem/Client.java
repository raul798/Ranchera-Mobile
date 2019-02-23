package ado.edu.pucmm.rancherasystem;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;


@Entity(tableName = "client_table")
public class Client {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_client")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "phoneNumber")
    private int phoneNumber;

    @NonNull
    @ColumnInfo(name = "email")

    private String email;

    @NonNull
    @ColumnInfo(name = "address")
    private String address;

    public Client(@NonNull int id, @NonNull String name, @NonNull int phoneNumber, @NonNull String email, @NonNull String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.id = id;
    }

    //getters
    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }
}
