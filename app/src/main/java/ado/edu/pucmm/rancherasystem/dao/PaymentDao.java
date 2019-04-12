package ado.edu.pucmm.rancherasystem.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Payment;

@Dao
public interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Payment payment);

    @Query("DELETE FROM Payment")
    void deleteAll();

    @Query("SELECT Sum(amount) FROM Payment WHERE bill = :bill")
    float getBillAmounts(int bill);

    @Query("UPDATE Payment SET signature = :signature WHERE id = :id")
    void updatePaymentSignature(int id, byte[] signature);

    @Query("UPDATE Payment SET externalID = :externalId WHERE id = :id")
    void updateExternalID(int id, String externalId);

    @Query("SELECT * FROM Payment where externalID IS NULL")
    List<Payment> getAllUnSynchronized();


}
