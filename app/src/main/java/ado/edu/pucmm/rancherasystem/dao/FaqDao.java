package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Faq;
import ado.edu.pucmm.rancherasystem.entity.Support;

@Dao
public interface FaqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Faq faq);

    @Query("DELETE FROM Faq")
    void deleteAll();

    @Query("SELECT * from Faq")
    LiveData<List<Faq>> getAllFaqs();
}
