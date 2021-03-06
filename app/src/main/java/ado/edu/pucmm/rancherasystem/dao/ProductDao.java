package ado.edu.pucmm.rancherasystem.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Product;

@Dao
public interface ProductDao {

    @Query("SELECT COUNT(*) from Product")
    int Count();

    @Insert()
    void insert(Product product);

    @Query("DELETE FROM Product")
    void deleteAll();

    @Query("select * from Product where id = :id")
    Product searchProductByID(int id);

    @Query("select * from Product where name = :name")
    Product searchProductByName(String name);

    @Query("SELECT * from Product ORDER BY id ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM Product WHERE name like :productName ")
    List<Product> getProducts(String productName);

    @Query("UPDATE Product SET quantity = :quantity WHERE id = :id")
    void updateProductQuantity(int id, float quantity);

}
