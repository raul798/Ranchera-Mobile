package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface DetalleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Detalle detalle);

    @Query("DELETE FROM Detalle")
    void deleteAll();

    @Query("SELECT COUNT(*) from Detalle")
    int CountDetalles();
}