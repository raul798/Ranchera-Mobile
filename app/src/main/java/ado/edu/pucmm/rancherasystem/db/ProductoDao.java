package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Producto producto);

    @Query("DELETE FROM Producto")
    void deleteAll();

    @Query("SELECT COUNT(*) from Producto")
    int CountProductos();
}
