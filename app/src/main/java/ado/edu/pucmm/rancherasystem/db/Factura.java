package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

@Entity(tableName = "Factura")
public class Factura {
    @PrimaryKey (autoGenerate = true)
    private int id;

    @NonNull
    private int id_client;

    @NonNull
    private String descripcion;

    private float total;


    private byte[] signature;

    public Factura(String descripcion, Integer id_client) {
        this.descripcion = descripcion;
        this.id_client = id_client;
        this.total = 0;
        this.signature = null;
    }


    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getSignature() {
        return signature;
    }


}



