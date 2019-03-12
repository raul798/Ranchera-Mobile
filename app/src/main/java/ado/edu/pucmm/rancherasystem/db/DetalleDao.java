package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DetalleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Detalle detalle);

    @Query("DELETE FROM Detalle")
    void deleteAll();

    @Query("SELECT COUNT(*) from Detalle")
    int CountDetalles();

    @Query("SELECT id_producto FROM Detalle WHERE id_factura = :id_factura ")
    List<Integer> getBillProducts(int id_factura);

    @Query("SELECT cantidad FROM Detalle WHERE id_producto = :id_producto ")
    Integer getSelectedProductAmount(int id_producto);
}