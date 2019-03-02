package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("DELETE FROM Product")
    void deleteAll();

    @Query("select * from Product where id = :id")
    Client searchProductByID(int id);


    @Query("select * from Product where name = :name")
    Client searchProductByName(String name);

    @Query("SELECT * from Product ORDER BY id ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM Product WHERE name like :productName ")
    List<Product> getProducts(String productName);

}
