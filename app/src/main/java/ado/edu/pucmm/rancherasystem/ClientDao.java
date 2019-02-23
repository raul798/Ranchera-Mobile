package ado.edu.pucmm.rancherasystem;

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

    @Query("SELECT * FROM client_table ORDER BY id_client ASC")
    LiveData<List<Client>> getAllClients();

}
