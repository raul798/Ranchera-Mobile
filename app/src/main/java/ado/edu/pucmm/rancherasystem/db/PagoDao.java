package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface PagoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pago pago);

    @Query("DELETE FROM Pago")
    void deleteAll();

}
