package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Factura")
public class Factura {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @NonNull
    private String descripcion;

    public Factura(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }
}