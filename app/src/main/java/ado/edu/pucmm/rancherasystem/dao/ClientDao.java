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

<<<<<<< HEAD
    @Insert(onConflict = OnConflictStrategy.FAIL)
=======
    @Insert(onConflict = OnConflictStrategy.REPLACE)
>>>>>>> added faq
    void insert(Client client);

    @Query("DELETE FROM Client")
    void deleteAll();

<<<<<<< HEAD
    @Query("SELECT * FROM Client WHERE id = :id")
=======
    @Query("select * from Client where id = :id")
>>>>>>> added faq
    Client searchClientByID(int id);

    @Query("SELECT * FROM Client WHERE name = :name")
    Client searchClientByName(String name);

    @Query("SELECT * from Client ORDER BY id ASC")
    LiveData<List<Client>> getAllClients();

    @Query("SELECT * FROM Client WHERE name like :clientName ")
    List<Client> getClients(String clientName);

}
