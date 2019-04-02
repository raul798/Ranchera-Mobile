package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.HelpRequest;

@Dao
public interface HelpRequestDao {

    @Insert()
    void insert(HelpRequest request);

    @Query("DELETE FROM HelpRequest")
    void deleteAll();

    @Query("SELECT * from HelpRequest")
    LiveData<List<HelpRequest>> getAllRequest();

    @Query("SELECT COUNT(*) from HelpRequest")
    int CountRequests();
}
