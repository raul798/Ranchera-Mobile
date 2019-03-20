package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Detail;

@Dao
public interface DetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Detail detail);

    @Query("DELETE FROM Detail")
    void deleteAll();

    @Query("SELECT COUNT(*) from Detail")
    int CountDetalles();

    @Query("SELECT product FROM Detail WHERE bill = :id_factura ")
    List<Integer> getBillProducts(int id_factura);

    @Query("SELECT quantity FROM Detail WHERE product = :id_producto ")
    Integer getSelectedProductAmount(int id_producto);

    @Query("SELECT * FROM Detail")
    LiveData<List<Detail>> getAllDetalles();
}