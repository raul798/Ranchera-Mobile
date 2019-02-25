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

    @Query("DELETE FROM client_table")
    void deleteAll();

    @Query("select * from client_table where id = :id")
    Client searchClientByID(int id);

    @Query("select * from client_table where name = :name")
    Client searchClientByName(String name);

    @Query("SELECT * from client_table ORDER BY id ASC")
    LiveData<List<Client>> getAllClients();

}
