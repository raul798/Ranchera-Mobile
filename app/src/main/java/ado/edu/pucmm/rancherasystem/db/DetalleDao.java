package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
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

    @Query("SELECT * FROM Detalle WHERE id_factura = 1")
    LiveData<List<Detalle>> getAllDetalles();


}
