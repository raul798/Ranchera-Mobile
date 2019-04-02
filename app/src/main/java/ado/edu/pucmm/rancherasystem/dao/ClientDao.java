package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Client;

@Dao
public interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Client client);

    @Query("DELETE FROM Client")
    void deleteAll();

    @Query("SELECT * FROM Client WHERE id = :id")
    Client searchClientByID(int id);

    @Query("SELECT * FROM Client WHERE name = :name")
    Client searchClientByName(String name);

    @Query("SELECT * from Client ORDER BY id ASC")
    LiveData<List<Client>> getAllClients();

    @Query("SELECT * FROM Client WHERE name like :clientName ")
    List<Client> getClients(String clientName);

}
