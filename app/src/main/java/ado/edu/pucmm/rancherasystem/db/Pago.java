package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Pago")
public class Pago {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private double monto;
    @NonNull
    private int id_factura;
    @NonNull
    private int id_cliente;

    public Pago(@NonNull double monto, @NonNull int id_factura, @NonNull int id_cliente) {
        this.monto = monto;
        this.id_factura = id_factura;
        this.id_cliente = id_cliente;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public double getMonto() {
        return monto;
    }

    public void setMonto(@NonNull double monto) {
        this.monto = monto;
    }

    @NonNull
    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(@NonNull int id_factura) {
        this.id_factura = id_factura;
    }

    @NonNull
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(@NonNull int id_cliente) {
        this.id_cliente = id_cliente;
    }
}
