package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Support;

@Dao
public interface SupportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Support question);

    @Query("DELETE FROM Support")
    void deleteAll();

    @Query("SELECT * from Support")
    LiveData<List<Support>> getAllRequest();
}
