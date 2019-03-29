package ado.edu.pucmm.rancherasystem.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Route;

@Dao
public interface RouteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Query("DELETE FROM Route")
    void deleteAll();

    @Query("select * from Route where id = :id")
    Route searchRouteById(int id);

    @Query("select * from Route ORDER BY id ASC")
    List<Route> getAllRoutes();
}
