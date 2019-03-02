package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Client client);

    @Query("DELETE FROM Client")
    void deleteAll();

    @Query("select * from Client where id = :id")
    Client searchClientByID(int id);


    @Query("select * from Client where name = :name")
    Client searchClientByName(String name);

    @Query("SELECT * from Client ORDER BY id ASC")
    LiveData<List<Client>> getAllClients();

    @Query("SELECT * FROM Client WHERE name like :clientName ")
    List<Client> getClients(String clientName);

}
