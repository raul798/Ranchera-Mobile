package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FacturaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Factura factura);

    @Query("DELETE FROM Factura")
    void deleteAll();

    @Query("SELECT COUNT(*) from Factura")
    int CountFacturas();

    @Query("SELECT * FROM Factura WHERE id = :factura_id")
    Factura searchFacturaByID(int factura_id);
}