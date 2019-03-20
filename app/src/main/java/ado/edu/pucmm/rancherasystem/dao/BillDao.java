package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Bill;

@Dao
public interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Bill bill);

    @Query("DELETE FROM Bill")
    void deleteAll();

    @Query("select * from Bill where id = :id")
    Bill searchBillByID(int id);

    @Query("SELECT * from Bill ORDER BY id ASC")
    LiveData<List<Bill>> getAllBills();

    @Query("SELECT  * FROM Bill where client like :clientId")
    List<Bill> getBills(Integer clientId );

    @Query("SELECT COUNT(*) from Bill")
    int CountFacturas();

    @Query("SELECT * FROM Bill WHERE id = :factura_id")
    Bill searchFacturaByID(int factura_id);

    @Query("UPDATE Bill SET total = :total WHERE id = :id")
    void updateBillTotal(int id, float total);

    @Query("UPDATE Bill SET description = :description WHERE id = :id")
    void updateBillDescription(int id, String description);

    @Query("UPDATE Bill SET signature = :signature WHERE id = :id")
    void updateBillSignature(int id, byte[] signature);

    @Query("SELECT * FROM Bill")
    LiveData<List<Bill>> getAllFacturas();


}