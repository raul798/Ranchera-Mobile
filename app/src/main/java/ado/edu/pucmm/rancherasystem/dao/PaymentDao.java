package ado.edu.pucmm.rancherasystem.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import ado.edu.pucmm.rancherasystem.entity.Payment;

@Dao
public interface PaymentDao {
    @Insert()
    void insert(Payment payment);

    @Query("DELETE FROM Payment")
    void deleteAll();

}
