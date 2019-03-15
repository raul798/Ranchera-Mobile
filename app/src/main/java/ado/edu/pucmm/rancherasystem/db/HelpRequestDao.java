package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface HelpRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HelpRequest request);

    @Query("DELETE FROM HelpRequest")
    void deleteAll();

    @Query("SELECT * from HelpRequest")
    LiveData<List<HelpRequest>> getAllRequest();

    @Query("SELECT COUNT(*) from HelpRequest")
    int CountRequests();
}

//    @Query("select * from Client where id = :id")
//    Client searchClientByID(int id);
//
//    @Query("select * from Client where name = :name")
//    Client searchClientByName(String name);
//@Query("SELECT * FROM Client WHERE name like :clientName ")
//List<Client> getClients(String clientName);
