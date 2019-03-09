package ado.edu.pucmm.rancherasystem.db;

        import android.arch.lifecycle.LiveData;
        import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Insert;
        import android.arch.persistence.room.OnConflictStrategy;
        import android.arch.persistence.room.Query;

        import java.util.List;

@Dao
public interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Bill bill);

    @Query("DELETE FROM bill_table")
    void deleteAll();

    @Query("select * from bill_table where id = :id")
    Bill searchBillByID(int id);

    @Query("SELECT * from bill_table ORDER BY id ASC")
    LiveData<List<Bill>> getAllBills();

    @Query("SELECT  * FROM bill_table where client_id like :clientId")
    List<Bill> getBills(String clientId );


}