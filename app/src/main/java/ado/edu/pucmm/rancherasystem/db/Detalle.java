package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Detalle")
public class Detalle {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private int id_factura;
    @NonNull
    private int id_producto;
    @NonNull
    private int cantidad;

    public Detalle(@NonNull int id_factura, @NonNull int id_producto, @NonNull int cantidad) {
        this.id_factura = id_factura;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(@NonNull int id_factura) {
        this.id_factura = id_factura;
    }

    @NonNull
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(@NonNull int id_producto) {
        this.id_producto = id_producto;
    }

    @NonNull
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(@NonNull int cantidad) {
        this.cantidad = cantidad;
    }
}
